package br.com.denisecastro.cielopaylab.ui.payment.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.components.textfield.CurrencyTextField
import br.com.denisecastro.cielopaylab.ui.components.button.LoadingButton
import br.com.denisecastro.cielopaylab.ui.components.PaymentTypeSelector
import br.com.denisecastro.cielopaylab.ui.components.TransactionResult
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import br.com.denisecastro.cielopaylab.ui.theme.BotaoProcessarVenda
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    state: PaymentUiState,
    onAmountChanged: (String) -> Unit,
    onPaymentTypeChanged: (PaymentType) -> Unit,
    onProcessPayment: () -> Unit,
    onNewPayment: () -> Unit,
    onBack: () -> Unit
) {
    val amountInCents = state.amount.toLongOrNull() ?: 0L

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Nova venda")
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CurrencyTextField(
                value = state.amount,
                enabled = !state.isLoading,
                onValueChange = onAmountChanged,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Forma de pagamento",
                style = MaterialTheme.typography.titleMedium
            )

            PaymentTypeSelector(
                selectedPaymentType = state.paymentType,
                enabled = !state.isLoading,
                onPaymentTypeChanged = onPaymentTypeChanged
            )

            LoadingButton(
                text = "Processar venda",
                isLoading = state.isLoading,
                enabled = amountInCents > 0L,
                onClick = onProcessPayment,
                modifier = Modifier.fillMaxWidth(),
                containerColor = BotaoProcessarVenda,
                contentColor = Color.White
            )

            state.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error
                )
            }

            state.transaction?.let { transaction ->
                TransactionResult(
                    transaction = transaction,
                    onNewPayment = onNewPayment
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(
        state = PaymentUiState(
            amount = "15000",
            paymentType = PaymentType.PIX,
            isLoading = false,
            transaction = Transaction(
                id = "123",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            errorMessage = null
        ),
        onAmountChanged = {},
        onPaymentTypeChanged = {},
        onProcessPayment = {},
        onNewPayment = {},
        onBack = {}
    )
}