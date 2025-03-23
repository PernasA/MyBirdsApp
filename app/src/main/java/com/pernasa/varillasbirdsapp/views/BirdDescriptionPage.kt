package com.pernasa.varillasbirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.pernasa.varillasbirdsapp.R
import com.pernasa.varillasbirdsapp.models.Bird
import com.pernasa.varillasbirdsapp.models.GlobalCounterBirdsObserved
import com.pernasa.varillasbirdsapp.models.room.RoomBird
import com.pernasa.varillasbirdsapp.ui.theme.SkyBluePrimary
import com.pernasa.varillasbirdsapp.ui.theme.SkyBluePrimaryContainer
import com.pernasa.varillasbirdsapp.ui.theme.SkyBlueTertiaryLight
import com.pernasa.varillasbirdsapp.ui.theme.GreenLime
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.BIG_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE
import com.pernasa.varillasbirdsapp.viewModels.BirdsListViewModel

sealed class WidgetItem {
    data class RowNames(val bird: Bird) : WidgetItem()
    data class RowCounterObserved(val bird: Bird, val birdsListViewModel: BirdsListViewModel) : WidgetItem()
    data class RowBirdAttributes(val bird: Bird): WidgetItem()
    data class BirdFullDescription(val bird: Bird): WidgetItem()
}

@Composable
fun BirdDescriptionPage(bird: Bird, birdsListViewModel: BirdsListViewModel) {
    val widgetItems = listOf(
        WidgetItem.RowNames(bird),
        WidgetItem.RowCounterObserved(bird, birdsListViewModel),
        WidgetItem.RowBirdAttributes(bird),
        WidgetItem.BirdFullDescription(bird)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(widgetItems) { item ->
            when (item) {
                is WidgetItem.RowNames ->
                    RowNames(item.bird)
                is WidgetItem.RowCounterObserved ->
                    RowCounterObserved(item.bird, item.birdsListViewModel)
                is WidgetItem.RowBirdAttributes ->
                    RowBirdAttributes(item.bird)
                is WidgetItem.BirdFullDescription ->
                    BirdFullDescription(item.bird)
            }
        }
    }
}

@Composable
fun RowNames(bird: Bird) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top)
                .padding(end = 10.dp),
            text = "${bird.id}. ${bird.name}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = BIG_TEXT_SIZE,
                lineHeight = BIG_TEXT_SIZE,
                shadow = Shadow(GreenLime, blurRadius = 1.0f),
                textAlign = TextAlign.Left,
                color = Color.White,
                lineBreak = LineBreak.Heading
            )
        )
        Text(
            text = bird.scientificName,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = TITLE_TEXT_SIZE,
                lineHeight = TITLE_TEXT_SIZE,
                shadow = Shadow(GreenLime, blurRadius = 1.0f),
                textAlign = TextAlign.Right,
                color = Color.White,
                lineBreak = LineBreak.Heading,
                fontStyle = FontStyle.Italic
            ),
            modifier = Modifier.weight(1F)
                .align(Alignment.Top)
                .padding(start = 10.dp),
        )
    }

    ZoomableImage(
        bird.imageResId,
        Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 5.dp, end = 5.dp)
            .border(BorderStroke(0.8.dp, GreenLime), RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)),
        ContentScale.FillWidth
    )
}

@Composable
fun RowCounterObserved(bird: Bird, birdsListViewModel: BirdsListViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .padding(top = 35.dp)
    ) {
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)) {
            Text(
                text = stringResource(R.string.have_you_observed),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            SwitchWasObserved(birdsListViewModel, bird.id)
        }
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)) {
            Text(
                text = stringResource(R.string.birds_observed),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = SkyBluePrimaryContainer,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            val counterState by GlobalCounterBirdsObserved.counter.observeAsState(0)
            Text(
                text = counterState.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = BIG_TEXT_SIZE,
                    lineHeight = BIG_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = SkyBluePrimaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp)
            )
        }
    }
}

@Composable
fun SwitchWasObserved(birdsListViewModel: BirdsListViewModel, birdId: Int) {
    val isChecked by birdsListViewModel.getBirdObservationState(birdId).collectAsState(initial = false)

    Switch(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        checked = isChecked,
        colors = SwitchDefaults.colors(checkedThumbColor = Color.White),
        onCheckedChange = { checked ->
            birdsListViewModel.editBirdWasObserved(RoomBird(birdId, checked))
            GlobalCounterBirdsObserved.modifyCounter(checked)
        },
        thumbContent = if (isChecked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = SkyBluePrimary,
                )
            }
        } else { null }
    )
}

@Composable
fun RowBirdAttributes(bird: Bird){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 35.dp, start = 5.dp, end = 5.dp)
    ) {
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)
        ) {
            Text(
                text = stringResource(R.string.bird_height).replace(" ", "\n", true),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            )
            Text(
                text = bird.height.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp)
            )
            Image(
                painter = painterResource(R.drawable.icon_ruler),
                contentDescription = stringResource(R.string.image_ruler_description),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 14.dp),
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.tint(SkyBluePrimary)
            )
        }
        VerticalDivider (
            color = GreenLime,
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
        )
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)
        ) {
            Text(
                text = stringResource(R.string.bird_frequency),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            )
            FrequencyIconImage(bird.frequency)
        }
    }
}

@Composable
fun FrequencyIconImage(birdFrequency: Int) {
    for (i in 4 downTo 1) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .padding(horizontal = 17.dp, vertical = 2.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(if (i <= birdFrequency) SkyBlueTertiaryLight else Color.White)
        )
    }
}

@Composable
fun BirdFullDescription(bird: Bird) {
    Text(
        modifier = Modifier.padding(top = 45.dp),
        text = stringResource(R.string.bird_full_description),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = MEDIUM_TEXT_SIZE,
            lineHeight = MEDIUM_TEXT_SIZE,
            textAlign = TextAlign.Left,
            color = SkyBluePrimary,
        )
    )
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = bird.description,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = SUBTITLE_TEXT_SIZE,
            lineHeight = SUBTITLE_TEXT_SIZE,
            textAlign = TextAlign.Justify,
            color = Color.White,
            lineBreak = LineBreak.Paragraph
        )
    )
}
