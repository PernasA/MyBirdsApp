package com.pernasa.mybirdsapp.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.pernasa.mybirdsapp.R
import com.pernasa.mybirdsapp.ui.theme.MossGreenPrimary
import com.pernasa.mybirdsapp.ui.theme.MossGreenPrimaryContainer
import com.pernasa.mybirdsapp.ui.theme.MossGreenRealTertiary
import com.pernasa.mybirdsapp.ui.theme.MossGreenSecondaryLight
import com.pernasa.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.pernasa.mybirdsapp.ui.theme.OrangeBird
import com.pernasa.mybirdsapp.utils.Constants.Companion.BUTTON_HOME_TEXT_SIZE
import com.pernasa.mybirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.pernasa.mybirdsapp.utils.Constants.Companion.TITLE_TEXT_SIZE

@Composable
fun HomePage(
    birdsListOnClick: () -> Unit,
    observationRoutesOnClick: () -> Unit,
    gameGuessOnClick: () -> Unit,
    aboutUsOnClick: () -> Unit,
    isLoading: Boolean = false
) {
    val showTooltip = remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures {
                        if (showTooltip.value) {
                            showTooltip.value = false
                        }
                    }
                },
        ) {
            item {
                HomePageInit(
                    birdsListOnClick, observationRoutesOnClick,
                    gameGuessOnClick,
                    aboutUsOnClick, showTooltip
                )
            }
        }
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            ) {
                CircularProgressIndicator(
                    color = OrangeBird,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        ExtendedShareButton()
    }
}

@Composable
fun HomePageInit(
    birdsListOnClick: () -> Unit,
    observationRoutesOnClick: () -> Unit,
    gameGuessOnClick: () -> Unit,
    aboutUsOnClick: () -> Unit,
    showTooltip: MutableState<Boolean>
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        RowTitle(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 8.dp)
        )

        ZoomableImage(
            R.drawable.b195_zorzal_chiguanco,
            Modifier
                .size(300.dp)
                .padding(top = 20.dp)
                .clip(RoundedCornerShape(32.dp))
                .shadow(100.dp, RoundedCornerShape(32.dp))
                .border(BorderStroke(1.dp, OrangeBird), RoundedCornerShape(32.dp)),
            ContentScale.Crop
        )

        Column (
            Modifier
                .width(250.dp)
                .padding(top = 26.dp)) {
            MyFilledButton(
                birdsListOnClick,
                R.string.birds_list,
                Modifier.padding(top = 15.dp)
            )
            RowRoutesButtons(observationRoutesOnClick, showTooltip)
            MyFilledButton(
                onClick = gameGuessOnClick,
                R.string.game_guess,
                Modifier.padding(top = 15.dp),
                OrangeBird
            )
            MyFilledButton(
                aboutUsOnClick,
                R.string.about_us,
                Modifier.padding(top = 15.dp)
            )
        }
    }
}

@Composable
fun RowTitle(modifier: Modifier) {
    Text(
        text = stringResource(R.string.title_main_page),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily.Serif,
            fontSize = 65.sp,
            lineHeight = 65.sp,
            letterSpacing = 6.sp,
            textAlign = TextAlign.Center,
            color = Color.White,
            shadow = Shadow(OrangeBird),
        ),
        modifier = modifier.padding(top = 15.dp)
    )
    Text(
        text = stringResource(R.string.subtitle_main_page),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            fontSize = TITLE_TEXT_SIZE,
            lineHeight = TITLE_TEXT_SIZE,
            textAlign = TextAlign.Center,
            color = OrangeBird,
            shadow = Shadow(MossGreenRealTertiary)
        ),
        modifier = modifier.padding(5.dp)
    )
    Text(
        text = stringResource(R.string.second_subtitle_main_page),
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = MEDIUM_TEXT_SIZE,
            lineHeight = MEDIUM_TEXT_SIZE,
            textAlign = TextAlign.Center,
            color = Color.White,
            shadow = Shadow(OrangeBird)
        ),
        modifier = modifier
            .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 25.dp)
    )
}

@Composable
fun MyFilledButton(
    onClick: () -> Unit,
    buttonText: Int,
    modifier: Modifier,
    textColor: Color? = Color.White
    ) {
    FilledTonalButton(
        onClick = { onClick() },
        modifier.fillMaxWidth(),
        border = BorderStroke(0.7.dp, MossGreenPrimaryContainer),
    ) {
        Text(
            stringResource(buttonText),
            fontSize = BUTTON_HOME_TEXT_SIZE,
            color = textColor ?: Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun RowRoutesButtons(
    observationRoutesOnClick: () -> Unit,
    showTooltipState: MutableState<Boolean>
) {
    val showTooltip by showTooltipState
    Row (modifier = Modifier.padding(top = 18.dp)) {
        MyFilledButton(
            observationRoutesOnClick,
            R.string.observation_routes,
            Modifier
                .padding(end = 5.dp)
                .weight(1F)
        )
        Box(modifier = Modifier.width(5.dp))
        Box(
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    showTooltipState.value = !showTooltip
                })
            }
        ) {
            LargeFloatingActionButton(
                onClick = { showTooltipState.value = !showTooltip },
                shape = CircleShape,
                modifier = Modifier.size(45.dp)
            ) {
                Icon(
                    Icons.Filled.Info,
                    stringResource(R.string.button_info),
                    tint = Color.Black,
                    modifier = Modifier.size(33.dp)
                )
            }

            if (showTooltip) {
                Popup(
                    alignment = Alignment.TopEnd,
                    offset = IntOffset(200, -200),
                    properties = PopupProperties(focusable = false)
                ) {
                    Surface(
                        modifier = Modifier.padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = MossGreenPrimary,
                        shadowElevation = 4.dp,
                        border = BorderStroke(0.5.dp, OrangeBird)
                    ) {
                        Text(
                            text = stringResource(R.string.tooltip_message_observation_routes),
                            modifier = Modifier.padding(8.dp),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExtendedShareButton() {
    val context = LocalContext.current
    val shareText = stringResource(id = R.string.button_share_text)
    val whatsappIntent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("https://api.whatsapp.com/send?text=$shareText") //TODO: CHECKEAR EL LINK A LA PLAY STORE
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        LargeFloatingActionButton(
            onClick = { context.startActivity(whatsappIntent) },
            shape = CircleShape,
            containerColor = MossGreenSecondaryLight,
            contentColor = Color.Black,
            modifier = Modifier.size(60.dp)
        ) {
            Icon(Icons.Filled.Share, stringResource(R.string.button_share))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    MyBirdsAppTheme (true) {
        HomePage(
            birdsListOnClick = {},
            observationRoutesOnClick = {},
            gameGuessOnClick = {},
            aboutUsOnClick = {},
            isLoading = true
        )
    }
}
