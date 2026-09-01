package br.com.denisecastro.cielopaylab.ui.payment.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.components.LoadingButton
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import br.com.denisecastro.cielopaylab.ui.theme.BotaoNovaVenda
import br.com.denisecastro.cielopaylab.ui.theme.BotaoProcessarVenda

@Composable
fun PaymentScreen(
    state: PaymentUiState,
    onAmountChanged: (String) -> Unit,
    onPaymentTypeChanged: (PaymentType) -> Unit,
    onProcessPayment: () -> Unit,
    onNewPayment: () -> Unit
) {
    val amountInCents = state.amount.toLongOrNull() ?: 0L
    val formattedAmount = CurrencyUtils.formatFromCents(amountInCents)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nova venda",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = formattedAmount,
            onValueChange = onAmountChanged,
            label = { Text("Valor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        )
        Text(
            text = "Forma de pagamento",
            style = MaterialTheme.typography.titleMedium
        )
        PaymentType.entries.forEach { type ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                RadioButton(
                    selected = state.paymentType == type,
                    onClick = { onPaymentTypeChanged(type) },
                    enabled = !state.isLoading
                )
                Text(
                    text = when (type) {
                        PaymentType.CREDIT -> "Crédito"
                        PaymentType.DEBIT -> "Débito"
                        PaymentType.PIX -> "Pix"
                    },
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
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
            HorizontalDivider()

            Text(
                text = if (transaction.status == TransactionStatus.APPROVED) {
                    "Venda aprovada"
                } else {
                    "Venda recusada"
                },
                style = MaterialTheme.typography.titleLarge
            )
            Text(text = "Valor: ${CurrencyUtils.formatFromCents(transaction.amountInCents)}")
            Text(text = "Tempo: ${transaction.responseTimeMillis} ms")
            Button(
                onClick = onNewPayment,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BotaoNovaVenda,
                    contentColor = Color.White
                )
            ) {
                Text("Nova venda")
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
        onNewPayment = {}
    )
}