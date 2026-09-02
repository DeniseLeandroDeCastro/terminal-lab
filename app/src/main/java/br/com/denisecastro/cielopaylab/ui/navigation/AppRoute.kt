package br.com.denisecastro.cielopaylab.ui.navigation

sealed class AppRoute(val route: String) {

    data object Home : AppRoute("home")

    data object Payment : AppRoute("payment")

    data object History : AppRoute("history")

    data object TransactionDetails :
        AppRoute("transaction-details/{transactionId}") {

        fun createRoute(transactionId: String): String {
            return "transaction-details/$transactionId"
        }
    }
}