package com.example.mybirdsapp.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shadow
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

@Composable
fun HomePage(
    birdsListOnClick: () -> Unit,
    observationRoutesOnClick: () -> Unit,
    aboutUsOnClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = stringResource(id = R.string.title_main_page),
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 36.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.sp,
                shadow = Shadow(LightBlueDark, blurRadius = 1.0f),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier,
        )
        Image(
            painter = painterResource(id = R.drawable.orange_bird_draw),
            contentDescription = stringResource(id = R.string.main_page_image_description),
            modifier = Modifier.size(200.dp)
        )
        MyFilledButton(birdsListOnClick, R.string.birds_list)
        MyFilledButton(observationRoutesOnClick, R.string.observation_routes)
        MyFilledButton(aboutUsOnClick, R.string.about_us)
    }
}

@Composable
fun MyFilledButton(onClick: () -> Unit, buttonText: Int) {
    FilledTonalButton(
        onClick = { onClick() },
        Modifier.padding(top = 40.dp)
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
