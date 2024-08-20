package com.example.newsdispatcher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsdispatcher.screen.webviewscreen.WebViewScreen

@Composable
fun NewsDispatcherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "") {

        composable("webView/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backstackEntry ->
            WebViewScreen(url = backstackEntry.arguments?.getString("url") ?: "")
        }

    }
}