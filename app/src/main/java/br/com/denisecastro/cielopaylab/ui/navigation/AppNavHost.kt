package br.com.denisecastro.cielopaylab.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.com.denisecastro.cielopaylab.ui.details.TransactionDetailsScreen
import br.com.denisecastro.cielopaylab.ui.details.viewmodel.TransactionDetailsViewModel
import br.com.denisecastro.cielopaylab.ui.history.screen.TransactionHistoryScreen
import br.com.denisecastro.cielopaylab.ui.home.screen.HomeScreen
import br.com.denisecastro.cielopaylab.ui.payment.screen.PaymentScreen
import br.com.denisecastro.cielopaylab.ui.payment.viewmodel.PaymentViewModel
import br.com.denisecastro.cielopaylab.ui.history.viewmodel.TransactionHistoryViewModel
import androidx.compose.runtime.LaunchedEffect

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
                },
                onHistory = {
                    navController.navigate(AppRoute.History.route)
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
                onNewPayment = viewModel::newPayment,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoute.History.route) {
            val viewModel: TransactionHistoryViewModel = hiltViewModel()
            val transactions by viewModel.transactions.collectAsStateWithLifecycle()

            TransactionHistoryScreen(
                transactions = transactions,
                onTransactionClick = { transaction ->
                    navController.navigate(
                        AppRoute.TransactionDetails.createRoute(
                            transaction.id
                        )
                    )
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(route = AppRoute.TransactionDetails.route) { backStackEntry ->
            val transactionId = backStackEntry.arguments?.getString("transactionId").orEmpty()

            val viewModel: TransactionDetailsViewModel = hiltViewModel()

            val uiState by viewModel.uiState.collectAsStateWithLifecycle()

            LaunchedEffect(transactionId) {
                viewModel.loadTransaction(transactionId)
            }

            TransactionDetailsScreen(
                transaction = uiState.transaction,
                isCancelling = uiState.isCancelling,
                errorMessage = uiState.errorMessage,
                onCancelTransaction = {
                    viewModel.cancelTransaction()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}