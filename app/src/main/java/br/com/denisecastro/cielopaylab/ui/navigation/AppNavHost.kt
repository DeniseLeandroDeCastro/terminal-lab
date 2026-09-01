package br.com.denisecastro.cielopaylab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.denisecastro.cielopaylab.ui.home.screen.HomeScreen
import br.com.denisecastro.cielopaylab.ui.payment.screen.PaymentScreen
import br.com.denisecastro.cielopaylab.ui.payment.viewmodel.PaymentViewModel

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Home.route
    ) {
        composable(AppRoute.Home.route) {
            HomeScreen(
                onNewPayment = {
                    navController.navigate(AppRoute.Payment.route)
                }
            )
        }

        composable(AppRoute.Payment.route) {
            val viewModel: PaymentViewModel = hiltViewModel()
            val state by viewModel.uiState.collectAsStateWithLifecycle()
            PaymentScreen(
                state = state,
                onAmountChanged = viewModel::onAmountChanged,
                onPaymentTypeChanged = viewModel::onPaymentTypeChanged,
                onProcessPayment = viewModel::processPayment,
                onNewPayment = viewModel::newPayment
            )
        }
    }
}