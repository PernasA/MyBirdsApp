package com.pernasa.mybirdsapp

import android.content.Context
import com.pernasa.mybirdsapp.models.ObservationRoute
import com.pernasa.mybirdsapp.utils.DrawableResourcesList
import com.pernasa.mybirdsapp.utils.JsonReader
import com.pernasa.mybirdsapp.viewModels.ObservationRoutesViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

@ExperimentalCoroutinesApi
class ObservationRoutesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockContext = mockk<Context>(relaxed = true)
    private val mockJsonReader = mockk<JsonReader>()

    private lateinit var observationRouteViewModel: ObservationRoutesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { mockJsonReader.loadJsonObservationRoutesFromAssets(mockContext, any()) } returns emptyList()
        observationRouteViewModel = ObservationRoutesViewModel(mockContext, mockJsonReader)
    }

    @Test
    fun `test loadJsonObservationRoutesFromAssets is called in init`(): Unit = runTest {
        advanceUntilIdle()

        assert(observationRouteViewModel.observationRouteList.isEmpty())
        coVerify { mockJsonReader.loadJsonObservationRoutesFromAssets(mockContext, "observation_routes.json") }
    }

    @Test
    fun `test getDrawableIdByRouteId returns correct drawable id`() {
        mockkObject(DrawableResourcesList)
        every { DrawableResourcesList.drawableListObservationRoutes } returns listOf(111, 222, 333)

        val drawableId1 = observationRouteViewModel.getDrawableIdByRouteIdPosition(1)
        assert(drawableId1 == 111)

        val drawableId3 = observationRouteViewModel.getDrawableIdByRouteIdPosition(3)
        assert(drawableId3 == 333)
    }

    @Test
    fun `test getObservationRouteById returns correct route`(): Unit = runTest {
        val mockRoute = ObservationRoute(1, "Route 1", "Description", listOf())
        val mockRoute4 = ObservationRoute(4, "Route 1", "Description", listOf())
        observationRouteViewModel.observationRouteList = listOf(mockRoute, mockRoute4)

        val result = observationRouteViewModel.getObservationRouteById(1)
        assert(result == mockRoute)

        val otherResult = observationRouteViewModel.getObservationRouteById(4)
        assert(otherResult == mockRoute4)
    }

    @Test
    fun `test getObservationRouteById returns null when there is no route with the id`(): Unit = runTest {
        val mockRoute = ObservationRoute(1, "Route 1", "Description", listOf())
        val mockRoute4 = ObservationRoute(4, "Route 1", "Description", listOf())
        observationRouteViewModel.observationRouteList = listOf(mockRoute, mockRoute4)

        val result = observationRouteViewModel.getObservationRouteById(2)
        assert(result == null)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
