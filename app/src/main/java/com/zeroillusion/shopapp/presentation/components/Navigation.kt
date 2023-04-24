package com.zeroillusion.shopapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.zeroillusion.shopapp.presentation.utils.Screen
import com.zeroillusion.shopapp.feature.cart.presentation.components.CartScreen
import com.zeroillusion.shopapp.feature.chat.presentation.components.ChatScreen
import com.zeroillusion.shopapp.feature.favorite.presentation.components.FavoriteScreen
import com.zeroillusion.shopapp.feature.home.presentation.components.HomeScreen
import com.zeroillusion.shopapp.feature.profile.presentation.components.ProfileScreen
import com.zeroillusion.shopapp.feature.signin.presentation.components.SignInScreen
import com.zeroillusion.shopapp.feature.login.presentation.components.LoginScreen

@Composable
fun Navigation(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SignInScreen.route,
        modifier = modifier
    ) {
        composable(Screen.SignInScreen.route) { SignInScreen(navController) }
        composable(Screen.LoginScreen.route) { LoginScreen(navController) }
        composable(Screen.HomeScreen.route) { HomeScreen(navController) }
        composable(Screen.FavouriteScreen.route) { FavoriteScreen(navController) }
        composable(Screen.CartScreen.route) { CartScreen(navController) }
        composable(Screen.ChatScreen.route) { ChatScreen(navController) }
        composable(Screen.ProfileScreen.route) { ProfileScreen(navController) }
    }
}