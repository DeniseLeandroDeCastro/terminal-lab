package br.com.denisecastro.cielopaylab.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TransactionHistoryItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = CurrencyUtils.formatFromCents(transaction.amountInCents),
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = transaction.paymentType.toDisplayName()
            )

            Text(
                text = transaction.status.toDisplayName(),
                color = transaction.status.toStatusColor()
            )

            Text(
                text = transaction.timestamp.toFormattedDate()
            )

            Text(
                text = "Tempo de resposta: ${transaction.responseTimeMillis} ms",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

private fun PaymentType.toDisplayName(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.PIX -> "Pix"
    }
}

private fun TransactionStatus.toDisplayName(): String {
    return when (this) {
        TransactionStatus.APPROVED -> "Venda aprovada"
        TransactionStatus.DECLINED -> "Venda recusada"
        TransactionStatus.ERROR -> "Erro na transação"
    }
}

@Composable
private fun TransactionStatus.toStatusColor() =
    when (this) {
        TransactionStatus.APPROVED -> MaterialTheme.colorScheme.primary
        TransactionStatus.DECLINED -> MaterialTheme.colorScheme.error
        TransactionStatus.ERROR -> MaterialTheme.colorScheme.error
    }

private fun Long.toFormattedDate(): String {
    val formatter = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale("pt", "BR")
    )

    return formatter.format(Date(this))
}

@Preview(showBackground = true)
@Composable
fun TransactionHistoryItemPreview() {
    CieloPayLabTheme {
        TransactionHistoryItem(
            transaction = Transaction(
                id = "123",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            onClick = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}