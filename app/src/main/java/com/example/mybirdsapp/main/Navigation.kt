package com.example.mybirdsapp.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mybirdsapp.R
import com.example.mybirdsapp.viewModels.BirdsListViewModel
import com.example.mybirdsapp.views.BirdsListPage
import com.example.mybirdsapp.views.room.DatabaseWidget
import com.example.mybirdsapp.viewModels.HomeViewModel
import com.example.mybirdsapp.views.BirdDescriptionPage
import com.example.mybirdsapp.views.first_page.HomePage
import com.example.mybirdsapp.views.MyAppBar
import com.example.mybirdsapp.views.ObservationRoutesPage

enum class NameOfScreen(@StringRes val title: Int) {
    StartNav(title = R.string.title_main_page_appbar),
    BirdsPageNav(title = R.string.birds_page_title),
    BirdDescriptionPageNav(title = R.string.bird_description_page_title),
    ObservationRoutesPageNav(title = R.string.observation_routes_page_title),
    AboutUsPageNav(title = R.string.about_us_page_title)
}

@Composable
fun Navigation(
    homeViewModel: HomeViewModel,
    birdsListViewModel: BirdsListViewModel,
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
        CreateNavigationHost(navController, homeViewModel, birdsListViewModel, innerPadding)
    }
}

@Composable
private fun CreateNavigationHost(
    navController: NavHostController,
    homeViewModel: HomeViewModel,
    birdsListViewModel: BirdsListViewModel,
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
            HomePage(
                birdsListOnClick = {
                    navController.navigate(NameOfScreen.BirdsPageNav.name)
                },
                observationRoutesOnClick = {
                    navController.navigate(NameOfScreen.ObservationRoutesPageNav.name)
                },
                aboutUsOnClick = {
                    navController.navigate(NameOfScreen.AboutUsPageNav.name)
                }
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
            BirdDescriptionPage(bird = bird)
        }

        composable(route = NameOfScreen.ObservationRoutesPageNav.name) {
            ObservationRoutesPage(
                observationRouteDescriptionOnClick = {
                    navController.navigate(NameOfScreen.ObservationRoutesPageNav.name)
                }
            )
        }

        composable(route = NameOfScreen.AboutUsPageNav.name) {
            DatabaseWidget(
                homeViewModel = homeViewModel,
                state = homeViewModel.state,
                onNextButtonClicked = {
                    navController.navigate(NameOfScreen.ObservationRoutesPageNav.name)
                },
                onCancelButtonClicked = {
                    cancelOrderAndNavigateToStart(navController)
                },
                Modifier.padding(innerPadding)
            )
            //AboutUsPage() Todo: Modificar
        }
    }
}

fun getCurrentScreen(route: String?): NameOfScreen {
    return when {
        route == null -> NameOfScreen.StartNav
        route.startsWith("${NameOfScreen.BirdDescriptionPageNav.name}/") -> NameOfScreen.BirdDescriptionPageNav
        route == NameOfScreen.BirdsPageNav.name -> NameOfScreen.BirdsPageNav
        route == NameOfScreen.ObservationRoutesPageNav.name -> NameOfScreen.ObservationRoutesPageNav
        route == NameOfScreen.AboutUsPageNav.name -> NameOfScreen.AboutUsPageNav
        else -> NameOfScreen.StartNav
    }
}

private fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(NameOfScreen.StartNav.name, inclusive = false)
}
