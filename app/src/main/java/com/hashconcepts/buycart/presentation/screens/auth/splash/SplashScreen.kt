package com.hashconcepts.buycart.presentation.screens.auth.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hashconcepts.buycart.R
import com.hashconcepts.buycart.presentation.screens.auth.AuthViewModel
import com.hashconcepts.buycart.presentation.screens.destinations.HomeScreenDestination
import com.hashconcepts.buycart.presentation.screens.destinations.OnBoardingScreenDestination
import com.hashconcepts.buycart.ui.theme.primaryColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

/**
 * @created 26/06/2022 - 4:02 AM
 * @project BuyCart
 * @author  ifechukwu.udorji
 */

@RootNavGraph(start = true)
@Destination
@Composable
fun SplashScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<AuthViewModel>()
    val torTit = FontFamily(Font(R.font.urwgeometricmedium))

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(primaryColor)
        systemUiController.setNavigationBarColor(primaryColor)
    }

    LaunchedEffect(key1 = true) {
        delay(2000)
        if (viewModel.isFirstAppLaunch) {
            navigator.popBackStack()
            navigator.navigate(OnBoardingScreenDestination)
        } else {
            navigator.popBackStack()
            navigator.navigate(HomeScreenDestination)
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.hsv(0F, 0F, 0.2F))) {
        Text(

            text = "Tornado",

            color = Color.Red,
            fontSize = 80.sp,
            modifier = Modifier.align(Alignment.Center),
            fontFamily = torTit
        )
    }
}