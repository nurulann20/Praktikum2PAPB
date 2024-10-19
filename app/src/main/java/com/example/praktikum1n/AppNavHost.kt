package com.example.praktikum1n

import android.window.SplashScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

//@Composable
//fun AppNavHost(
//    modifier: Modifier = Modifier,
//    navController: NavHostController,
//    startDestination: String = NavigationItem.Login.route,
//){
//    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
//        composable(NavigationItem.Login.route){
//            LoginScreen()
//        }
//        composable(NavigationItem.Matkul.route){
//            ListActivity()
//        }
//        composable(NavigationItem.Cerita.route){
////            Cerita(navController)
//        }
//        composable(NavigationItem.Profile.route){
//            GithubProfile()
//        }
//    }
//}