package com.example.mybirdsapp.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.Coordinates
import com.example.mybirdsapp.models.ObservationRoute
import com.example.mybirdsapp.ui.theme.MossGreenPrimary
import com.example.mybirdsapp.ui.theme.OrangeBird
import com.example.mybirdsapp.utils.Constants.Companion.BIG_TEXT_SIZE
import com.example.mybirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.example.mybirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE
import com.example.mybirdsapp.viewModels.BirdsListViewModel
import com.example.mybirdsapp.viewModels.ObservationRoutesViewModel

sealed class WidgetItemRoute {
    data class RowName(
        val observationRoute: ObservationRoute,
        val observationRouteViewModel: ObservationRoutesViewModel
    ) : WidgetItem()
    data class RowDescription(val observationRoute: ObservationRoute) : WidgetItem()
    data class RowBirdsList(
        val observationRoute: ObservationRoute,
        val observationRouteViewModel: ObservationRoutesViewModel,
        val birdsListViewModel: BirdsListViewModel
    ): WidgetItem()
}

@Composable
fun ObservationRouteDescriptionPage(
    observationRoute: ObservationRoute,
    observationRouteViewModel: ObservationRoutesViewModel,
    birdsListViewModel: BirdsListViewModel
) {
    val widgetItems = listOf(
        WidgetItemRoute.RowName(observationRoute, observationRouteViewModel),
        WidgetItemRoute.RowDescription(observationRoute),
        WidgetItemRoute.RowBirdsList(
            observationRoute,
            observationRouteViewModel,
            birdsListViewModel
        )
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(widgetItems) { item ->
            when (item) {
                is WidgetItemRoute.RowName ->
                    RowName(item.observationRoute, item.observationRouteViewModel)
                is WidgetItemRoute.RowDescription ->
                    RowDescription(item.observationRoute)
                is WidgetItemRoute.RowBirdsList ->
                    RowBirdsList(
                        item.observationRoute,
                        item.observationRouteViewModel,
                        item.birdsListViewModel
                    )
                else -> {}
            }
        }
    }
}

@Composable
fun RowName(
    observationRoute: ObservationRoute,
    observationRouteViewModel: ObservationRoutesViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Top)
                .weight(1F)
                .padding(end = 5.dp),
            text = "${observationRoute.id}. ${observationRoute.name}",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = BIG_TEXT_SIZE,
                lineHeight = BIG_TEXT_SIZE,
                letterSpacing = 0.sp,
                shadow = Shadow(OrangeBird, blurRadius = 1.0f),
                textAlign = TextAlign.Left,
                color = Color.White,
                lineBreak = LineBreak.Heading
            )
        )
        ExtendedRouteMapButton(Modifier.weight(1F), observationRoute)
    }
    val routeImageResId = observationRouteViewModel.getDrawableIdByRouteIdPosition(observationRoute.id)
    Image(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 5.dp, end = 5.dp)
            .border(BorderStroke(0.5.dp, OrangeBird)),
        painter = painterResource(id = routeImageResId),
        contentDescription = stringResource(R.string.image_route_description),
        contentScale = ContentScale.FillWidth
    )
}

@Composable
fun ExtendedRouteMapButton(modifier: Modifier, observationRoute: ObservationRoute) {
    val context = LocalContext.current

    ExtendedFloatingActionButton(
        onClick = { openGoogleMaps(context, observationRoute.coordinatesList) },
        text = { Text(stringResource(R.string.button_maps), fontSize = SUBTITLE_TEXT_SIZE) },
        icon = { Icon(Icons.Rounded.LocationOn, "") },
        containerColor = Color.Black,
        contentColor = MossGreenPrimary,
        modifier = modifier
            .height(60.dp)
            .padding(start = 15.dp)
    )
}

fun createGoogleMapsUrl(coordinatesList: List<Coordinates>): String {
    if (coordinatesList.isEmpty()) {
        throw IllegalArgumentException("La lista de coordenadas no puede estar vacía")
    }
    val baseUrl = "https://www.google.com/maps/dir/?api=1"
    val destination = "${coordinatesList.last().x},${coordinatesList.last().y}"
    val waypoints = coordinatesList.dropLast(1).joinToString("|") { "${it.x},${it.y}" }

    return "$baseUrl&waypoints=$waypoints&travelmode=driving&destination=$destination"
}

fun openGoogleMaps(context: Context, coordinatesList: List<Coordinates>) {
    val url = createGoogleMapsUrl(coordinatesList)
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    val resolvedActivities = context.packageManager.queryIntentActivities(intent, 0)

    if (resolvedActivities.isNotEmpty()) {
        context.startActivity(intent)
    } else {
        Toast.makeText(context, "No se puede abrir la ubicación", Toast.LENGTH_LONG).show()
    }
}

@Composable
fun RowDescription(
    observationRoute: ObservationRoute
) {
    Text(
        modifier = Modifier.padding(top = 45.dp),
        text = stringResource(R.string.bird_full_description),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = MEDIUM_TEXT_SIZE,
            lineHeight = MEDIUM_TEXT_SIZE,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Left,
            color = MossGreenPrimary,
        )
    )
    Text(
        modifier = Modifier.padding(top = 10.dp),
        text = observationRoute.description,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = SUBTITLE_TEXT_SIZE,
            lineHeight = SUBTITLE_TEXT_SIZE,
            letterSpacing = 0.sp,
            textAlign = TextAlign.Justify,
            color = Color.White,
            lineBreak = LineBreak.Paragraph
        )
    )
}

@Composable
fun RowBirdsList(
        observationRoute: ObservationRoute,
        observationRouteViewModel: ObservationRoutesViewModel,
        birdsListViewModel: BirdsListViewModel
) {

}