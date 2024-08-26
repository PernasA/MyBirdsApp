package com.pernasa.mybirdsapp.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pernasa.mybirdsapp.R
import com.pernasa.mybirdsapp.viewModels.BirdsListViewModel
import com.pernasa.mybirdsapp.viewModels.ObservationRoutesViewModel
import com.pernasa.mybirdsapp.views.BirdsListPage
import com.pernasa.mybirdsapp.views.AboutUsPage
import com.pernasa.mybirdsapp.views.BirdDescriptionPage
import com.pernasa.mybirdsapp.views.GameGuessPage
import com.pernasa.mybirdsapp.views.HomePage
import com.pernasa.mybirdsapp.views.ObservationRouteDescriptionPage
import com.pernasa.mybirdsapp.views.ObservationRoutesPage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class NameOfScreen(@StringRes val title: Int) {
    StartNav(title = R.string.title_main_page_appbar),
    BirdsPageNav(title = R.string.birds_page_title),
    BirdDescriptionPageNav(title = R.string.bird_description_page_title),
    ObservationRoutesPageNav(title = R.string.observation_routes_page_title),
    GameGuessPageNav(title = R.string.game_guess_page_title),
    ObservationRouteDescriptionPageNav(title = R.string.observation_route_description_page_title),
    AboutUsPageNav(title = R.string.about_us_page_title)
}

@Composable
fun Navigation(
    birdsListViewModel: BirdsListViewModel,
    observationRoutesViewModel: ObservationRoutesViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = getCurrentScreen(backStackEntry?.destination?.route)

    Scaffold(
        modifier = Modifier,
        topBar = {
            MyAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        CreateNavigationHost(
            navController,
            birdsListViewModel,
            observationRoutesViewModel,
            innerPadding
        )
    }
}

@Composable
private fun CreateNavigationHost(
    navController: NavHostController,
    birdsListViewModel: BirdsListViewModel,
    observationRoutesViewModel: ObservationRoutesViewModel,
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NameOfScreen.StartNav.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = NameOfScreen.StartNav.name) {
            val scope = rememberCoroutineScope()
            var isLoading by remember { mutableStateOf(false) }
            HomePage(
                birdsListOnClick = {
                    scope.launch {
                        isLoading = true
                        delay(1000)
                        navController.navigate(NameOfScreen.BirdsPageNav.name)
                        isLoading = false
                    }
                },
                observationRoutesOnClick = {
                    scope.launch {
                        isLoading = true
                        delay(500)
                        navController.navigate(NameOfScreen.ObservationRoutesPageNav.name)
                        isLoading = false
                    }
                },
                gameGuessOnClick = {
                    scope.launch {
                        isLoading = true
                        delay(600)
                        navController.navigate(NameOfScreen.GameGuessPageNav.name)
                        isLoading = false
                    }
                },
                aboutUsOnClick = {
                    scope.launch {
                        isLoading = true
                        delay(700)
                        navController.navigate(NameOfScreen.AboutUsPageNav.name)
                        isLoading = false
                    }
                },
                isLoading = isLoading
            )
        }

        composable(route = NameOfScreen.BirdsPageNav.name) {
            BirdsListPage(
                birdsListViewModel,
                onBirdClick = { bird ->
                    navController.navigate("${NameOfScreen.BirdDescriptionPageNav.name}/${bird.id}")
                }
            )
        }

        composable(
            route = "${NameOfScreen.BirdDescriptionPageNav.name}/{birdId}",
            arguments = listOf(
                navArgument("birdId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val birdId = backStackEntry.arguments?.getInt("birdId") ?: return@composable
            val bird = birdsListViewModel.getBirdById(birdId) ?: return@composable
            BirdDescriptionPage(bird = bird, birdsListViewModel)
        }

        composable(route = NameOfScreen.ObservationRoutesPageNav.name) {
            ObservationRoutesPage(
                observationRoutesViewModel,
                observationRouteDescriptionOnClick = { observationRoute ->
                    navController.navigate(
                        "${NameOfScreen.ObservationRouteDescriptionPageNav.name}/${observationRoute.id}"
                    )
                },
            )
        }

        composable(route = NameOfScreen.GameGuessPageNav.name) {
            GameGuessPage(birdsListViewModel.gameGuessViewModel)
        }

        composable(
            route = "${NameOfScreen.ObservationRouteDescriptionPageNav.name}/{observationRouteId}",
            arguments = listOf(
                navArgument("observationRouteId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val observationRouteId = backStackEntry.arguments?.getInt("observationRouteId") ?: return@composable
            val observationRoute = observationRoutesViewModel.getObservationRouteById(observationRouteId) ?: return@composable
            ObservationRouteDescriptionPage(observationRoute, observationRoutesViewModel)
        }

        composable(route = NameOfScreen.AboutUsPageNav.name) {
            AboutUsPage()
        }
    }
}

fun getCurrentScreen(route: String?): NameOfScreen {
    return when {
        route == null -> NameOfScreen.StartNav
        route.startsWith("${NameOfScreen.BirdDescriptionPageNav.name}/") ->
            NameOfScreen.BirdDescriptionPageNav
        route == NameOfScreen.BirdsPageNav.name -> NameOfScreen.BirdsPageNav
        route == NameOfScreen.ObservationRoutesPageNav.name -> NameOfScreen.ObservationRoutesPageNav
        route.startsWith("${NameOfScreen.ObservationRouteDescriptionPageNav.name}/") ->
            NameOfScreen.ObservationRouteDescriptionPageNav
        route == NameOfScreen.GameGuessPageNav.name -> NameOfScreen.GameGuessPageNav
        route == NameOfScreen.AboutUsPageNav.name -> NameOfScreen.AboutUsPageNav
        else -> NameOfScreen.StartNav
    }
}
