package com.zeroillusion.shopapp.presentation.utils

import androidx.annotation.StringRes
import com.zeroillusion.shopapp.R

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: Int) {
    object SignInScreen : Screen("sign_in_screen", R.string.sign_in, R.drawable.ic_home)
    object LoginScreen : Screen("login_screen", R.string.login, R.drawable.ic_home)
    object HomeScreen : Screen("home_screen", R.string.home, R.drawable.ic_home)
    object FavouriteScreen : Screen("favourite_screen", R.string.favourite, R.drawable.ic_favorite)
    object CartScreen : Screen("cart_screen", R.string.cart, R.drawable.ic_cart)
    object ChatScreen : Screen("chat_screen", R.string.chat, R.drawable.ic_chat)
    object ProfileScreen : Screen("profile_screen", R.string.profile, R.drawable.ic_profile)
}