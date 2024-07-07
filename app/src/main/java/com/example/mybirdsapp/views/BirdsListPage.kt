package com.example.mybirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.mybirdsapp.ui.theme.LightBlueDark
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.viewModels.BirdsListViewModel

@Composable
fun BirdsListPage(
    birdsListViewModel: BirdsListViewModel?,
    birdDescriptionOnClick: () -> Unit
) {
    Column(
        Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        TwoBirdsRow()
        TwoBirdsRow()
    }
}

@Composable
fun TwoBirdsRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        BirdCard(
            "Zorzal",
            1,
            R.drawable.orange_bird_draw,
            Modifier.weight(1F)
        )
        BirdCard(
            "Pajaro Rojo",
            2,
            R.drawable.ic_launcher_foreground,
            Modifier.weight(1F)
        )
    }
}

@Composable
fun BirdCard(
    birdName: String,
    birdNumber:Int,
    birdImage: Int,
    modifier: Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
        ),
        border = BorderStroke(2.dp, Color.White),
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 6.dp, top = 6.dp, end = 6.dp, bottom = 6.dp)
    ) {
        Column (
            modifier.fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .padding(top = 10.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "$birdNumber. ",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        lineHeight = 22.sp,
                        letterSpacing = 0.sp,
                        shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                        textAlign = TextAlign.Left,
                        color = Color.Black
                    )
                )
                Text(
                    text = birdName,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        lineHeight = 22.sp,
                        letterSpacing = 0.sp,
                        shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    ),
                )
            }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                painter = painterResource(id = birdImage),
                contentDescription = stringResource(id = R.string.image_bird_description),
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BirdsListPagePreview() {
    MyBirdsAppTheme {
        BirdsListPage(
            null,
            birdDescriptionOnClick = {}
        )
    }
}
