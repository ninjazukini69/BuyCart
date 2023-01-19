package com.hashconcepts.buycart.presentation.screens.auth.welcome

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hashconcepts.buycart.R
import com.hashconcepts.buycart.presentation.screens.destinations.LoginScreenDestination
import com.hashconcepts.buycart.presentation.screens.destinations.RegisterScreenDestination
import com.hashconcepts.buycart.ui.theme.welcomeStatusBarColor
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

/**
 * @created 29/06/2022 - 9:17 PM
 * @project BuyCart
 * @author  ifechukwu.udorji
 */

@Destination
@Composable
fun WelcomeScreen(
    navigator: DestinationsNavigator,
) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(welcomeStatusBarColor)
        systemUiController.setNavigationBarColor(welcomeStatusBarColor)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.onbr),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            Button(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                onClick = { navigator.navigate(LoginScreenDestination) }
            ) {
                Text(text = "Login", style = MaterialTheme.typography.button, color = Color.White)
            }

            Spacer(modifier = Modifier.width(10.dp))

            OutlinedButton(
                modifier = Modifier
                    .height(50.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(width = 2.dp, color = Color.White),
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.Transparent
                ),
                onClick = { navigator.navigate(RegisterScreenDestination) }
            ) {
                Text(text = "Register", style = MaterialTheme.typography.button,color=Color.White)
            }
        }
    }
}