package com.example.mybirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.models.room.RoomBird
import com.example.mybirdsapp.ui.theme.MossGreenPrimary2
import com.example.mybirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.example.mybirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE
import com.example.mybirdsapp.viewModels.BirdsListViewModel

@Composable
fun BirdsListPage(
    birdsListViewModel: BirdsListViewModel,
    onBirdClick: (Bird) -> Unit
) {
    val dataBirdsList = birdsListViewModel.dataBirdsList
    LazyColumn(
        modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
    ) {
        items(dataBirdsList.chunked(2)) { rowBirds ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
            ) {
                for (bird in rowBirds) {
                    BirdCard(
                        bird,
                        onBirdClick,
                        birdsListViewModel,
                        Modifier.weight(1F)
                    )
                }
                if (rowBirds.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, MossGreenPrimary2),
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
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = "${bird.id}. ${bird.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_TEXT_SIZE,
                    lineHeight = TITLE_TEXT_SIZE,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                maxLines = 2
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(horizontal = 10.dp)
                    .border(BorderStroke(0.5.dp, MossGreenPrimary2)),
                painter = painterResource(id = bird.imageResId),
                contentDescription = stringResource(id = R.string.image_bird_description),
                contentScale = ContentScale.Crop
            )
            CardRowCheckBox(
                birdsListViewModel,
                bird.id,
                Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
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
    Row(
        modifier = modifier
            .padding(start =  15.dp, top = 10.dp, bottom = 10.dp, end = 10.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = stringResource(id = R.string.bird_observed),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = SUBTITLE_TEXT_SIZE,
                lineHeight = SUBTITLE_TEXT_SIZE,
                letterSpacing = 0.sp,
                color = Color.Black
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        var checked by remember {
            mutableStateOf(birdsListViewModel.listObservedBirds[birdId-1].wasObserved)
        }
        Checkbox(
            modifier = Modifier.align(Alignment.CenterVertically),
            checked = checked,
            onCheckedChange = {
                checked = it
                birdsListViewModel.editBirdWasObserved(RoomBird(birdId, checked))
                GlobalCounterBirdsObserved.modifyCounter(checked)
            },
        )
    }
}
