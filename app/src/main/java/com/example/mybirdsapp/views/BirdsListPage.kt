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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.ui.theme.LightBlueDark
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
                    .height(225.dp)
            ) {
                for (bird in rowBirds) {
                    BirdCard(
                        bird,
                        onBirdClick,
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
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(2.dp, Color.White),
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 6.dp)
            .clickable(onClick = { onBirdClick(bird) })
    ) {
        Column (
            modifier.fillMaxSize().align(Alignment.CenterHorizontally)
        ) {
            Text(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 4.dp)
                    .fillMaxWidth(),
                text = "${bird.id}. ${bird.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    lineHeight = 22.sp,
                    letterSpacing = 0.sp,
                    shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .border(BorderStroke(0.5.dp, Color.Black)),
                painter = painterResource(id = bird.imageResId),
                contentDescription = stringResource(id = R.string.image_bird_description),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirdsListPagePreview() {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ){
        BirdCard(
            bird = Bird(
                1, "Zorzal colorado", 1, 1, 1, "",
                "", "", "", R.drawable.orange_bird_draw, false
            ), {},
            modifier = Modifier.weight(1F)
        )
    }
}
