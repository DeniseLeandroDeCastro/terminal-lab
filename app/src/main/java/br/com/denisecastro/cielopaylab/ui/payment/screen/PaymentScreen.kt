package br.com.denisecastro.cielopaylab.ui.payment.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState

@Composable
fun PaymentScreen(
    state: PaymentUiState,
    onAmountChanged: (String) -> Unit,
    onPaymentTypeChanged: (PaymentType) -> Unit,
    onProcessPayment: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Nova venda",
            style =
                MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = state.amount,
            onValueChange = onAmountChanged,
            label = {
                Text("Valor")
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        )

        Text(
            text = "Forma de pagamento",
            style =
                MaterialTheme.typography.titleMedium
        )

        PaymentType.entries.forEach { type ->

            Row(
                modifier =
                    Modifier.fillMaxWidth()
            ) {

                RadioButton(
                    selected = state.paymentType == type,
                    onClick = { onPaymentTypeChanged(type) },
                    enabled = !state.isLoading
                )

                Text(
                    text = when (type) {

                        PaymentType.CREDIT ->
                            "Crédito"

                        PaymentType.DEBIT ->
                            "Débito"

                        PaymentType.PIX ->
                            "Pix"
                    },
                    modifier =
                        Modifier.padding(top = 12.dp)
                )
            }
        }

        Button(
            onClick = onProcessPayment,
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {

            Text("Processar venda")
        }

        if (state.isLoading) {

            CircularProgressIndicator()
        }

        state.errorMessage?.let { error ->

            Text(
                text = error,
                color =
                    MaterialTheme.colorScheme.error
            )
        }
        state.transaction?.let { transaction ->
            HorizontalDivider()

            Text(
                text =
                    if (
                        transaction.status == TransactionStatus.APROVVED
                    ) {
                        "Venda aprovada"
                    } else {
                        "Venda recusada"
                    },
                style =
                    MaterialTheme.typography.titleLarge
            )

            Text(
                text =
                    "Valor: R$ %.2f"
                        .format(transaction.amount)
            )

            Text(
                text =
                    "Tempo: ${transaction.responseTimeMillis} ms"
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    PaymentScreen(
        state = PaymentUiState(
            amount = "150.00",
            paymentType = PaymentType.PIX,
            isLoading = false,
            transaction = Transaction(
                id = "123",
                amount = 150.00,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APROVVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250
            ),
            errorMessage = null
        ),
        onAmountChanged = {},
        onPaymentTypeChanged = {},
        onProcessPayment = {}
    )
}