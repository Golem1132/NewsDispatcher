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
    NavHost(navController = navController, startDestination = LoginRoutes.LOGIN_SCREEN) {

        navigation(CreateAccountRoutes.CREDENTIALS_SCREEN, CreateAccountRoutes.ROUTE) {
            composable(CreateAccountRoutes.CREDENTIALS_SCREEN) {
                CredentialsScreen(navController, 1, 3)
            }
            composable(CreateAccountRoutes.PERSONAL_INFO_SCREEN) {
                PersonalInfoScreen(navController, 2, 3)
            }
            composable(CreateAccountRoutes.PICK_SOURCES_SCREEN) {
                PickSourcesScreen(navController, 3, 3)
            }
        }
        composable(LoginRoutes.LOGIN_SCREEN) {
            LoginScreen(navController)
        }

        composable(NewsRoutes.NEWS_SCREEN) {
            NewsScreen(navController)
        }

        composable("${WebViewRoutes.WEB_VIEW_SCREEN}/{url}",
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
        navigation(startDestination = AccountRoutes.ACCOUNT_SCREEN, route = AccountRoutes.ROUTE) {
            composable(AccountRoutes.ACCOUNT_SCREEN) {
                AccountScreen(navController)
            }
        }

    }
}