package com.example.mybirdsapp.views

import android.app.Application
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.viewModels.BirdsListViewModel

@Composable
fun BirdsListPage(
    birdsListViewModel: BirdsListViewModel,
    onBirdClick: (Bird) -> Unit
) {
    Column(
        Modifier.padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        val dataBirdsList by birdsListViewModel.dataBirdsList.observeAsState(emptyList())

        LazyColumn(
            modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
        ) {
            items(dataBirdsList.chunked(2)) { rowBirds ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
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
            defaultElevation = 12.dp,
        ),
        border = BorderStroke(2.dp, Color.White),
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 6.dp, top = 6.dp, end = 6.dp, bottom = 6.dp)
            .clickable(onClick = { onBirdClick(bird) })
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
                    text = "${bird.id} ",
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
                    text = bird.name,
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
    MyBirdsAppTheme {
        val context = LocalContext.current.applicationContext as Application
        val birdsListViewModel = BirdsListViewModel(context)
        BirdsListPage(
            birdsListViewModel = birdsListViewModel,
            onBirdClick = {}
        )
    }
}
