package com.example.mybirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.ObservationRoute
import com.example.mybirdsapp.ui.theme.MossGreenPrimary
import com.example.mybirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE
import com.example.mybirdsapp.viewModels.ObservationRoutesViewModel

@Composable
fun ObservationRoutesPage(
    observationRoutesViewModel: ObservationRoutesViewModel,
    observationRouteDescriptionOnClick: (ObservationRoute) -> Unit
) {
    val observationRouteList = observationRoutesViewModel.observationRouteList
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.topographic_map),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(0.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(observationRouteList) { observationRoute ->
                MyListItem(observationRoute, observationRouteDescriptionOnClick)
            }
        }
    }
}

@Composable
fun MyListItem(
    observationRoute: ObservationRoute,
    onObservationRouteClick: (ObservationRoute) -> Unit,
    ) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MossGreenPrimary,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 22.dp,
        ),
        border = BorderStroke(1.dp, Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 4.dp, top = 6.dp, end = 4.dp, bottom = 6.dp)
            .clickable(onClick = { onObservationRouteClick(observationRoute) })
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${observationRoute.id}. ${observationRoute.name}",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = TITLE_TEXT_SIZE,
                    lineHeight = TITLE_TEXT_SIZE,
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2
            )
        }
    }
}
