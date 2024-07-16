package com.example.mybirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.R
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.ui.theme.YellowLetter

@Composable
fun HomePage(
    birdsListOnClick: () -> Unit,
    observationRoutesOnClick: () -> Unit,
    aboutUsOnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Image(
            painter = painterResource(id = R.drawable.tapa_titulo),
            contentDescription = stringResource(id = R.string.main_page_image_description),
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Fit
        )
        MyFilledButton(birdsListOnClick, R.string.birds_list)
        MyFilledButton(observationRoutesOnClick, R.string.observation_routes)
        MyFilledButton(aboutUsOnClick, R.string.about_us)
        Image(
            painter = painterResource(id = R.drawable.zorzal_chiguanco_pruebaa),
            contentDescription = stringResource(id = R.string.main_page_image_description),
            modifier = Modifier.size(200.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun MyFilledButton(onClick: () -> Unit, buttonText: Int) {
    FilledTonalButton(
        onClick = { onClick() },
        Modifier.padding(top = 40.dp),
        border = BorderStroke(0.7.dp, YellowLetter),
    ) {
        Text(stringResource(buttonText), fontSize = 30.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MyBirdsAppTheme {
        HomePage(
            birdsListOnClick = {},
            observationRoutesOnClick = {},
            aboutUsOnClick = {}
        )
    }
}
