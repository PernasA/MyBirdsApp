package com.example.mybirdsapp

import GlobalCounterBirdsObserved
import android.content.Context
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.models.room.RoomBird
import com.example.mybirdsapp.models.room.RoomBirdsDao
import com.example.mybirdsapp.utils.DrawableResourcesList
import com.example.mybirdsapp.utils.JsonReader
import com.example.mybirdsapp.viewModels.BirdsListViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

@ExperimentalCoroutinesApi
class BirdsListViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val mockContext = mockk<Context>(relaxed = true)
    private val mockJsonReader = mockk<JsonReader>()
    private val mockRoomBirdsDao = mockk<RoomBirdsDao>()

    private lateinit var birdsListViewModel: BirdsListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { mockJsonReader.loadJsonBirdsFromAssets(mockContext, any()) } returns emptyList()

        val testBird = RoomBird(id = 1, false)
        coEvery { mockRoomBirdsDao.getAllBirds() } returns flowOf(listOf(testBird))
        mockkObject(GlobalCounterBirdsObserved)
        coEvery { GlobalCounterBirdsObserved.setCounter(any()) } just runs

        birdsListViewModel = BirdsListViewModel(mockContext, mockRoomBirdsDao, mockJsonReader)
    }

    @Test
    fun `test loadJsonBirdsFromAssets is called in init`(): Unit = runTest {
        advanceUntilIdle()

        assert(birdsListViewModel.dataBirdsList.isEmpty())
        coVerify { mockJsonReader.loadJsonBirdsFromAssets(mockContext, "birds_list.json") }
    }

    @Test
    fun `test getDrawableIdByRouteId returns correct drawable id`() {
        mockkObject(DrawableResourcesList)
        every { DrawableResourcesList.drawableListBirds } returns listOf(111, 222, 333)

        val drawableId1 = birdsListViewModel.getDrawableIdByBirdIdPosition(1)
        assert(drawableId1 == 111)

        val drawableId3 = birdsListViewModel.getDrawableIdByBirdIdPosition(3)
        assert(drawableId3 == 333)
    }

    @Test
    fun `test getBirdById returns correct route`(): Unit = runTest {
        val mockBird = Bird(1, "Bird 1", 0, 0, 0)
        val mockBird4 = Bird(4, "Bird 4", 0, 0, 0)
        birdsListViewModel.dataBirdsList = listOf(mockBird, mockBird4)

        val result = birdsListViewModel.getBirdById(1)
        assert(result == mockBird)

        val otherResult = birdsListViewModel.getBirdById(4)
        assert(otherResult == mockBird4)
    }

    @Test
    fun `test getObservationRouteById returns null when there is no route with the id`(): Unit = runTest {
        val mockBird = Bird(1, "Bird 1", 0, 0, 0)
        val mockBird4 = Bird(4, "Bird 4", 0, 0, 0)
        birdsListViewModel.dataBirdsList = listOf(mockBird, mockBird4)

        val result = birdsListViewModel.getBirdById(2)
        assert(result == null)
    }

    @Test
    fun `test should invoke editWasObservedBird in roomBirdsDao`(): Unit = runTest {
        val roomBird = RoomBird(1, true)
        coEvery { mockRoomBirdsDao.editWasObservedBird(roomBird) } just runs
        birdsListViewModel.editBirdWasObserved(roomBird)
        advanceUntilIdle()

        coVerify { mockRoomBirdsDao.editWasObservedBird(roomBird) }
    }

    @Test
    fun `test should createFileAllBirds when roomBirdsDao_getAllBirds returns emptyList`(): Unit = runTest {
        coEvery { mockRoomBirdsDao.insertAll(emptyList()) } just runs

        birdsListViewModel.createFileAllBirds(0)
        advanceUntilIdle()

        coVerify { mockRoomBirdsDao.insertAll(emptyList()) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }
}
