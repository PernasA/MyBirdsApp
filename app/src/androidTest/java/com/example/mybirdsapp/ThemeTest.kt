package com.example.mybirdsapp

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.unit.dp
import com.example.mybirdsapp.ui.theme.DarkColorScheme
import com.example.mybirdsapp.ui.theme.LightColorScheme
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.captureToImage
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MyBirdsAppThemeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testDarkTheme() {
        composeTestRule.setContent {
            MyBirdsAppTheme(darkTheme = true) {
                TestContent()
            }
        }

        composeTestRule.onNodeWithTag("testBox")
            .assertExists()
            .assertBackgroundColor(DarkColorScheme.primary)
    }

    @Test
    fun testLightTheme() {
        composeTestRule.setContent {
            MyBirdsAppTheme(darkTheme = false) {
                TestContent()
            }
        }

        composeTestRule.onNodeWithTag("testBox")
            .assertExists()
            .assertBackgroundColor(LightColorScheme.primary)
    }

    @Composable
    fun TestContent() {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(MaterialTheme.colorScheme.primary)
                .testTag("testBox")
        )
    }
}

fun SemanticsNodeInteraction.assertBackgroundColor(expectedColor: Color) {
    this.captureToImage().asAndroidBitmap().apply {
        val pixelColor = getPixel(width / 2, height / 2)
        val color = Color(pixelColor)
        assert(color == expectedColor) {
            "Expected color: $expectedColor but was: $color"
        }
    }
}