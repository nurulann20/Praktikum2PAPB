package com.example.praktikum1n

import android.graphics.drawable.Icon
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, @StringRes val resourceId: Int, val icon: ImageVector){
    object Matkul : Screen("matkul", R.string.title_activity_list, Icons.Filled.Info)
    object Profile: Screen("profile", R.string.title_activity_github_profile, Icons.Filled.Person)
    object Cerita: Screen("cerita", R.string.title_activity_homepage, Icons.Filled.Favorite)
    object Login: Screen("login", R.string.title_activity_github_profile, Icons.Filled.Person)
}

val items = listOf(
    Screen.Matkul,
    Screen.Cerita,
    Screen.Profile
)