package com.example.mybirdsapp.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mybirdsapp.viewModels.BirdsListViewModel
import com.example.mybirdsapp.views.BirdsListPage
import com.example.mybirdsapp.views.room.DatabaseWidget
import com.example.mybirdsapp.viewModels.HomeViewModel
import com.example.mybirdsapp.views.first_page.HomePage
import com.example.mybirdsapp.views.MyAppBar
import com.example.mybirdsapp.views.ObservationRoutesPage

@Composable
fun Navigation(
    homeViewModel: HomeViewModel,
    birdsListViewModel: BirdsListViewModel,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = NameOfScreen.valueOf(
        backStackEntry?.destination?.route ?: NameOfScreen.Start.name
    )

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

private fun cancelOrderAndNavigateToStart(
    navController: NavHostController
) {
    navController.popBackStack(NameOfScreen.Start.name, inclusive = false)
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
        startDestination = NameOfScreen.Start.name,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(route = NameOfScreen.Start.name) {
            HomePage(
                birdsListOnClick = {
                    navController.navigate(NameOfScreen.BirdsPage.name)
                },
                observationRoutesOnClick = {
                    navController.navigate(NameOfScreen.ObservationRoutesPage.name)
                },
                aboutUsOnClick = {
                    navController.navigate(NameOfScreen.AboutUsPage.name)
                }
            )
        }

        composable(route = NameOfScreen.BirdsPage.name) {
            BirdsListPage(
                birdsListViewModel,
                birdDescriptionOnClick = {
                    navController.navigate(NameOfScreen.ObservationRoutesPage.name)
                }
            )
        }

        composable(route = NameOfScreen.ObservationRoutesPage.name) {
            ObservationRoutesPage(
                observationRouteDescriptionOnClick = {
                    navController.navigate(NameOfScreen.ObservationRoutesPage.name)
                }
            )
        }

        composable(route = NameOfScreen.AboutUsPage.name) {
            DatabaseWidget(
                homeViewModel = homeViewModel,
                state = homeViewModel.state,
                onNextButtonClicked = {
                    navController.navigate(NameOfScreen.ObservationRoutesPage.name)
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
