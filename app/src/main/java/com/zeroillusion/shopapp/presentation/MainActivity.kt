package com.zeroillusion.shopapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.zeroillusion.shopapp.presentation.components.BottomNavigationBar
import com.zeroillusion.shopapp.presentation.components.Navigation
import com.zeroillusion.shopapp.presentation.ui.theme.ShopAppTheme
import com.zeroillusion.shopapp.presentation.utils.Screen
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShopAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                Scaffold(
                    bottomBar = {
                        if (
                            navBackStackEntry?.destination?.route != Screen.SignInScreen.route &&
                            navBackStackEntry?.destination?.route != Screen.LoginScreen.route
                        ) {
                            BottomNavigationBar(
                                items = listOf(
                                    Screen.HomeScreen,
                                    Screen.FavouriteScreen,
                                    Screen.CartScreen,
                                    Screen.ChatScreen,
                                    Screen.ProfileScreen
                                ),
                                navController = navController,
                            )
                        }
                    }
                ) { innerPadding ->
                    Navigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}