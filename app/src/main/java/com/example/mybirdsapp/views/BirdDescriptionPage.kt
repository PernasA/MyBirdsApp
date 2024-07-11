package com.example.mybirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.models.room.RoomBird
import com.example.mybirdsapp.ui.theme.LightBlueDark
import com.example.mybirdsapp.viewModels.BirdsListViewModel

@Composable
fun BirdDescriptionPage(bird: Bird, birdsListViewModel: BirdsListViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        RowNames(bird = bird, modifier = Modifier.align(Alignment.CenterHorizontally))
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(BorderStroke(0.5.dp, Color.White)),
            painter = painterResource(id = bird.imageResId),
            contentDescription = stringResource(id = R.string.image_bird_description),
            contentScale = ContentScale.FillWidth
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {
            Text(
                text = stringResource(R.string.have_you_observed)
            )
            SwitchWasObserved(birdsListViewModel, bird.id)
            Text(
                text = "${stringResource(R.string.birds_observed)}: ${bird.id}"
            )
        }
    }
}

@Composable
fun RowNames(bird: Bird, modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(90.dp)
    ) {
        Text(
            modifier = Modifier
                .weight(1F)
                .align(Alignment.Top),
            text = "${bird.id}. ${bird.name}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
                shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                textAlign = TextAlign.Left,
                color = Color.White,
                lineBreak = LineBreak.Heading
            )
        )
        Column (
            modifier = Modifier
                .weight(1F)
                .fillMaxHeight()
                .align(Alignment.CenterVertically),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = bird.scientificName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.sp,
                    shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                    textAlign = TextAlign.Right,
                    color = Color.White,
                    fontStyle = FontStyle.Italic
                )
            )
            Text(
                text = bird.englishName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.sp,
                    shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                    textAlign = TextAlign.Right,
                    color = Color.Yellow
                )
            )
        }
    }
}

@Composable
fun SwitchWasObserved(birdsListViewModel: BirdsListViewModel, birdId: Int) {
    var checked by remember {
        mutableStateOf(birdsListViewModel.listObservedBirds[birdId-1].wasObserved)
    }
    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
            birdsListViewModel.editBirdWasObserved(RoomBird(birdId, checked))
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else { null }
    )
}
