package br.com.denisecastro.cielopaylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.denisecastro.cielopaylab.ui.payment.screen.PaymentScreen
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import br.com.denisecastro.cielopaylab.ui.payment.viewmodel.PaymentViewModel
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val viewModel: PaymentViewModel = hiltViewModel()

            val state by viewModel.uiState.collectAsState()

            CieloPayLabTheme {

                PaymentScreen(
                    state = state,
                    onAmountChanged = viewModel::onAmountChanged,
                    onPaymentTypeChanged = viewModel::onPaymentTypeChanged,
                    onProcessPayment = viewModel::processPayment
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    CieloPayLabTheme {
        PaymentScreen(
            state = PaymentUiState(),
            onAmountChanged = {},
            onPaymentTypeChanged = {},
            onProcessPayment = {}
        )
    }
}