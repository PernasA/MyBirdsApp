package com.pernasa.mybirdsapp.views

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pernasa.mybirdsapp.R
import com.pernasa.mybirdsapp.ui.theme.MossGreenBright
import com.pernasa.mybirdsapp.ui.theme.MossGreenPrimaryContainer
import com.pernasa.mybirdsapp.ui.theme.MossGreenSecondary
import com.pernasa.mybirdsapp.ui.theme.OrangeBird
import com.pernasa.mybirdsapp.utils.Constants.Companion.BIG_TEXT_SIZE
import com.pernasa.mybirdsapp.utils.Constants.Companion.BUTTON_HOME_TEXT_SIZE
import com.pernasa.mybirdsapp.utils.Constants.Companion.MEDIUM_TEXT_SIZE
import com.pernasa.mybirdsapp.viewModels.GameGuessViewModel
import kotlinx.coroutines.delay

@Composable
fun GameGuessPage(gameGuessViewModel: GameGuessViewModel) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, start = 9.dp, end = 9.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.guess_bird_title),
                    style = TextStyle(
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily.Serif,
                        fontSize = 35.sp,
                        lineHeight = 35.sp,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        shadow = Shadow(OrangeBird),
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                RowGamePoints(gameGuessViewModel)

                val currentBird by gameGuessViewModel.currentBird.collectAsState()

                currentBird?.imageResId?.let { painterResource(id = it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = stringResource(R.string.main_page_image_description),
                        modifier = Modifier
                            .size(330.dp)
                            .padding(top = 20.dp)
                            .clip(RoundedCornerShape(32.dp))
                            .shadow(100.dp, RoundedCornerShape(32.dp))
                            .border(BorderStroke(2.dp, OrangeBird), RoundedCornerShape(32.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                ColumnOptions(gameGuessViewModel)
            }
        }
    }

}

@Composable
fun RowGamePoints(gameGuessViewModel: GameGuessViewModel) {
    val newScore by gameGuessViewModel.newScore.collectAsState()
    val highScore by gameGuessViewModel.highScore.collectAsState()

    Row (
        Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)) {
            Text(
                text = stringResource(R.string.actual_points),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = MossGreenPrimaryContainer,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = newScore.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = BIG_TEXT_SIZE,
                    lineHeight = BIG_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = MossGreenPrimaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp)
            )
        }
        VerticalDivider (
            color = OrangeBird,
            modifier = Modifier
                .width(1.dp)
                .height(60.dp)
        )
        Column(
            Modifier
                .weight(1F)
                .align(Alignment.Top)
        ) {
            Text(
                text = stringResource(R.string.record_points),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MEDIUM_TEXT_SIZE,
                    lineHeight = MEDIUM_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = MossGreenPrimaryContainer,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = highScore.toString(),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = BIG_TEXT_SIZE,
                    lineHeight = BIG_TEXT_SIZE,
                    textAlign = TextAlign.Center,
                    color = MossGreenPrimaryContainer,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 7.dp)
            )
        }
    }
}

@Composable
fun ColumnOptions(gameGuessViewModel: GameGuessViewModel) {
    val options by gameGuessViewModel.getOptionsForCurrentBird().collectAsState()
    var selectedOption by remember { mutableStateOf<String?>(null) }
    var isOptionSelected by remember { mutableStateOf(false) }

    Column(Modifier.padding(start = 30.dp, end = 30.dp, top = 50.dp)) {
        options.forEach { option ->
            val isCorrect = gameGuessViewModel.isCorrectOption(option)
            val buttonColor by animateColorAsState(
                targetValue = when {
                    selectedOption == null -> MossGreenSecondary
                    isCorrect && option == selectedOption -> MossGreenBright
                    !isCorrect && option == selectedOption -> Color.Red
                    else -> MossGreenSecondary
                },
                animationSpec = tween(durationMillis = 1000), label = ""
            )

            val scale by animateFloatAsState(
                targetValue = if (selectedOption != null && option == selectedOption) 1.1f else 1f,
                animationSpec = tween(durationMillis = 500), label = ""
            )

            MyFilledButtonOption(
                optionText = option,
                buttonColor = buttonColor,
                scale = scale,
                onClick = {
                    if (!isOptionSelected) {
                        selectedOption = option
                        isOptionSelected = true
                        gameGuessViewModel.onOptionSelected(option)
                    }
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    LaunchedEffect(selectedOption) {
        if (selectedOption != null) {
            delay(1000)
            selectedOption = null
            isOptionSelected = false
            gameGuessViewModel.moveToNextBird()
        }
    }
}

@Composable
fun MyFilledButtonOption(
    optionText: String,
    buttonColor: Color,
    scale: Float,
    onClick: () -> Unit
) {
    FilledTonalButton(
        onClick = { onClick() },
        Modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            },
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = buttonColor
        ),
        border = BorderStroke(0.7.dp, buttonColor),
    ) {
        Text(
            optionText,
            fontSize = BUTTON_HOME_TEXT_SIZE,
            textAlign = TextAlign.Center
        )
    }
}