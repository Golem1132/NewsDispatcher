package com.example.newsdispatcher.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.newsdispatcher.screen.accountscreen.AccountScreen
import com.example.newsdispatcher.screen.createaccontscreen.CredentialsScreen
import com.example.newsdispatcher.screen.createaccontscreen.PersonalInfoScreen
import com.example.newsdispatcher.screen.createaccontscreen.PickSourcesScreen
import com.example.newsdispatcher.screen.loginscreen.LoginScreen
import com.example.newsdispatcher.screen.newsscreen.NewsScreen
import com.example.newsdispatcher.screen.webviewscreen.WebViewScreen

@Composable
fun NewsDispatcherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {

        navigation("credentials", "createAccount") {
            composable("credentials") {
                CredentialsScreen(navController, 1, 3)
            }
            composable("PersonalInfo") {
                PersonalInfoScreen(navController, 2, 3)
            }
            composable("PickSources") {
                PickSourcesScreen(navController, 3, 3)
            }
        }
        composable("login") {
            LoginScreen(navController)
        }

        composable("feed") {
            NewsScreen(navController)
        }

        composable("webView/{url}",
            arguments = listOf(
                navArgument("url") {
                    type = NavType.StringType
                }
            )
        ) { backstackEntry ->
            WebViewScreen(
                navController = navController,
                url = backstackEntry.arguments?.getString("url") ?: ""
            )
        }

        composable("account") {
            AccountScreen(navController)
        }

    }
}