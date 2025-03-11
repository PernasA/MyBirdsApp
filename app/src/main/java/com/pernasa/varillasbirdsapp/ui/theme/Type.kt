package com.pernasa.varillasbirdsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pernasa.varillasbirdsapp.utils.Constants.Companion.SUBTITLE_TEXT_SIZE

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = SUBTITLE_TEXT_SIZE,
        lineHeight = SUBTITLE_TEXT_SIZE,
        letterSpacing = 0.5.sp
    )
)
