package br.com.denisecastro.cielopaylab.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.components.button.LoadingButton
import br.com.denisecastro.cielopaylab.ui.theme.BotaoNovaVenda
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun TransactionResult(
    transaction: Transaction,
    onNewPayment: () -> Unit
) {
    val isApproved = transaction.status == TransactionStatus.APPROVED

    val statusTitle = when (transaction.status) {
        TransactionStatus.APPROVED -> "Venda aprovada"
        TransactionStatus.DECLINED -> "Venda recusada"
        TransactionStatus.CANCELLED -> "Venda cancelada"
        TransactionStatus.ERROR -> "Erro na transação"
    }

    val statusColor = if (isApproved) {
        Color(0xFF148A5B)
    } else {
        MaterialTheme.colorScheme.error
    }

    val statusBackground = if (isApproved) {
        Color(0xFFE5F5EE)
    } else {
        Color(0xFFFFE8E8)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier
                .size(64.dp)
                .background(
                    color = statusBackground,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isApproved) {
                    Icons.Default.Check
                } else {
                    Icons.Default.Close
                },
                contentDescription = null,
                tint = statusColor,
                modifier = Modifier.size(32.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = statusTitle,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = statusColor
            )

            Text(
                text = CurrencyUtils.formatFromCents(
                    transaction.amountInCents
                ),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = transaction.paymentType.toDisplayName(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
                    .copy(alpha = 0.45f)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {

                TransactionInfoRow(
                    label = "Status",
                    value = statusTitle
                )

                TransactionInfoRow(
                    label = "Pagamento",
                    value = transaction.paymentType.toDisplayName()
                )

                TransactionInfoRow(
                    label = "Tempo de resposta",
                    value = "${transaction.responseTimeMillis} ms"
                )
            }
        }

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

@Composable
private fun TransactionInfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
    }
}

private fun PaymentType.toDisplayName(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.PIX -> "Pix"
    }
}

@Preview(
    name = "Venda aprovada",
    showBackground = true
)
@Composable
fun TransactionResultApprovedPreview() {
    CieloPayLabTheme {
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
}

@Preview(
    name = "Venda recusada",
    showBackground = true
)
@Composable
fun TransactionResultDeclinedPreview() {
    CieloPayLabTheme {
        TransactionResult(
            transaction = Transaction(
                id = "456",
                amountInCents = 8990L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.DECLINED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 430L
            ),
            onNewPayment = {}
        )
    }
}