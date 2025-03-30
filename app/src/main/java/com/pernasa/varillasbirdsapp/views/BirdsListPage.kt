package com.pernasa.varillasbirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pernasa.varillasbirdsapp.R
import com.pernasa.varillasbirdsapp.models.Bird
import com.pernasa.varillasbirdsapp.models.GlobalCounterBirdsObserved
import com.pernasa.varillasbirdsapp.models.room.RoomBird
import com.pernasa.varillasbirdsapp.ui.theme.SkyBlueTertiary
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE
import com.pernasa.varillasbirdsapp.viewModels.BirdsListViewModel
import java.text.Normalizer

@Composable
fun BirdsListPage(
    birdsListViewModel: BirdsListViewModel,
    onBirdClick: (Bird) -> Unit
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var selectedHeightRanges by rememberSaveable {
        mutableStateOf(listOf<Int>())
    }
    var selectedFrequency by rememberSaveable {
        mutableStateOf(listOf(4,3,2,1))
    }

    var observationFilterState by rememberSaveable { mutableStateOf(ToggleableState.Off) }

    val observedBirds by birdsListViewModel.listObservedBirds.collectAsState()

    val normalizedSearchQuery = Normalizer.normalize(searchQuery, Normalizer.Form.NFD)
        .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

    val filteredBirdsList by remember(searchQuery, selectedHeightRanges, selectedFrequency, observationFilterState, observedBirds) {
        derivedStateOf {
            birdsListViewModel.dataBirdsList.filter { bird ->
                val normalizedBirdName = Normalizer.normalize(bird.name, Normalizer.Form.NFD)
                    .replace(Regex("\\p{InCombiningDiacriticalMarks}+"), "")

                val matchesObservation = when (observationFilterState) {
                    ToggleableState.On -> observedBirds.any { it.id == bird.id && it.wasObserved }
                    ToggleableState.Indeterminate -> observedBirds.none { it.id == bird.id && it.wasObserved }
                    ToggleableState.Off -> true
                }

                val heightMatches = selectedHeightRanges.isEmpty() || selectedHeightRanges.any { selectedHeight ->
                    when (selectedHeight) {
                        15 -> bird.height in 0..15
                        30 -> bird.height in 15..30
                        50 -> bird.height in 30..50
                        1000 -> bird.height > 50
                        else -> false
                    }
                }

                normalizedBirdName.contains(normalizedSearchQuery, ignoreCase = true) &&
                        heightMatches &&
                        (selectedFrequency.isEmpty() || bird.frequency in selectedFrequency) &&
                        matchesObservation
            }
        }
    }
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SearchRow(
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    searchQuery = query
                },
                observationFilterState = observationFilterState,
                onObservationFilterStateChange = { newState ->
                    observationFilterState = newState
                }
            )
        }

        item {
            FiltersRow(
                onHeightSelected = { selectedHeightRanges = it },
                onFrequencySelected = { selectedFrequency = it }
            )
        }

        items(filteredBirdsList.chunked(2), key = { it.first().id }) { rowBirds ->
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
            ) {
                items(rowBirds, key = { it.id }) { bird ->
                    BirdCard(
                        bird,
                        onBirdClick,
                        birdsListViewModel,
                        Modifier.fillParentMaxWidth(0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun BirdCard(
    bird: Bird,
    onBirdClick: (Bird) -> Unit,
    birdsListViewModel: BirdsListViewModel,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        border = BorderStroke(2.dp, SkyBlueTertiary),
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 6.dp)
            .clickable(onClick = { onBirdClick(bird) })
    ) {
        Column (
            modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, start = 2.dp, end = 2.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "${bird.id}. ${bird.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_TEXT_SIZE,
                    lineHeight = TITLE_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                maxLines = 2
            )
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 10.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(BorderStroke(0.8.dp, SkyBlueTertiary), RoundedCornerShape(8.dp)),
                //painter = painterResource(id = bird.imageResId),
                contentDescription = stringResource(R.string.image_bird_description),
                contentScale = ContentScale.Crop,
                model = bird.imageResId
            )
            CardRowCheckBox(
                birdsListViewModel,
                bird.id,
                Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CardRowCheckBox(
    birdsListViewModel: BirdsListViewModel,
    birdId: Int,
    modifier: Modifier
    ) {

    val isObservedFlow = remember { birdsListViewModel.getBirdObservationState(birdId) }
    val isObserved by isObservedFlow.collectAsState(initial = false)

    Row(
        modifier = modifier
            .padding(start = 15.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = stringResource(R.string.bird_observed),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = SUBTITLE_TEXT_SIZE,
                lineHeight = SUBTITLE_TEXT_SIZE,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            checked = isObserved,
            onCheckedChange = { isChecked ->
                birdsListViewModel.editBirdWasObserved(RoomBird(birdId, isChecked))
                GlobalCounterBirdsObserved.modifyCounter(isChecked)
            },
        )
    }
}
