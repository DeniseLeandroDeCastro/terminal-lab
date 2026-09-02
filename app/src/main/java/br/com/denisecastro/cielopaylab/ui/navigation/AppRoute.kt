package br.com.denisecastro.cielopaylab.ui.navigation

sealed class AppRoute(val route: String) {
    data object Home : AppRoute("home")
    data object Payment : AppRoute("payment")
    data object History : AppRoute("history")
}