package br.com.denisecastro.cielopaylab.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import br.com.denisecastro.cielopaylab.ui.components.button.LoadingButton
import br.com.denisecastro.cielopaylab.ui.theme.BotaoNovaVenda

@Composable
fun TransactionResult(
    transaction: Transaction,
    onNewPayment: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HorizontalDivider()

        Text(
            text = if (transaction.status == TransactionStatus.APPROVED) {
                "Venda aprovada"
            } else {
                "Venda recusada"
            },
            style = MaterialTheme.typography.titleLarge,
            color = if (transaction.status == TransactionStatus.APPROVED) {
                Color(0xFF2E7D32)
            } else {
                MaterialTheme.colorScheme.error
            }
        )

        Text(
            text = "Valor: ${CurrencyUtils.formatFromCents(transaction.amountInCents)}"
        )

        Text(
            text = "Tempo: ${transaction.responseTimeMillis} ms"
        )

        LoadingButton(
            text = "Nova venda",
            isLoading = false,
            enabled = true,
            onClick = onNewPayment,
            modifier = Modifier.fillMaxWidth(),
            containerColor = BotaoNovaVenda,
            contentColor = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionResultPreview() {
    TransactionResult(
        transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.CREDIT,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        ),
        onNewPayment = {}
    )
}