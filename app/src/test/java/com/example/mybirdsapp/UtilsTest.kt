package com.example.mybirdsapp

import android.content.Context
import androidx.compose.ui.unit.sp
import com.example.mybirdsapp.utils.Constants
import com.example.mybirdsapp.utils.JsonReader
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import org.junit.Test

class UtilsTest {
    private val mockContext = mockk<Context>(relaxed = true)

    private val jsonReader = JsonReader()

    @Test
    fun testLoadJsonObservationRoutesFromAssets() {
        val jsonString = "[{\"id\":1,\"name\":\"Route1\"},{\"id\":2,\"name\":\"Route2\"}]"
        val inputStream = jsonString.byteInputStream()

        coEvery { mockContext.assets.open("observation_routes.json") } returns inputStream

        val routes = jsonReader.loadJsonObservationRoutesFromAssets(mockContext, "observation_routes.json")

        assert(routes.size == 2)
        assert(routes[0].id == 1)
        assert(routes[0].name == "Route1")
        coVerify { mockContext.assets.open(any()) }
    }

    @Test
    fun testLoadJsonBirdsFromAssets() {
        val jsonString = "[{\"id\":1,\"name\":\"Bird1\"},{\"id\":2,\"name\":\"Bird2\"}]"
        val inputStream = jsonString.byteInputStream()

        coEvery { mockContext.assets.open("birds.json") } returns inputStream

        val birds = jsonReader.loadJsonBirdsFromAssets(mockContext, "birds.json")

        assert(birds.size == 2)
        assert(birds[0].id == 1)
        assert(birds[0].name == "Bird1")
        coVerify { mockContext.assets.open(any()) }
    }

    @Test
    fun testBigTextSize() {
        assertEquals(25.sp, Constants.BIG_TEXT_SIZE)
    }

    @Test
    fun testTitleTextSize() {
        assertEquals(20.sp, Constants.TITLE_TEXT_SIZE)
    }

    @Test
    fun testMediumTextSize() {
        assertEquals(16.sp, Constants.MEDIUM_TEXT_SIZE)
    }

    @Test
    fun testSubtitleTextSize() {
        assertEquals(14.sp, Constants.SUBTITLE_TEXT_SIZE)
    }
}
