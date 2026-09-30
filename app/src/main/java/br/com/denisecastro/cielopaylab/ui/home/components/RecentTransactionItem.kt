package br.com.denisecastro.cielopaylab.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import br.com.denisecastro.cielopaylab.ui.utils.toDisplayName
import br.com.denisecastro.cielopaylab.ui.utils.toFormattedDate

@Composable
fun RecentTransactionItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        TransactionStatusIcon(
            status = transaction.status
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = transaction.paymentType.toDisplayName(),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "${transaction.status.toDisplayName()} • " +
                        transaction.timestamp.toFormattedDate(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = CurrencyUtils.formatFromCents(
                    transaction.amountInCents
                ),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Ver detalhes",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun TransactionStatusIcon(
    status: TransactionStatus
) {
    val approved = status == TransactionStatus.APPROVED

    val backgroundColor = if (approved) {
        Color(0xFFE8F5EE)
    } else {
        Color(0xFFFFEBEE)
    }

    val iconColor = if (approved) {
        Color(0xFF16834B)
    } else {
        Color(0xFFC62828)
    }

    Surface(
        shape = CircleShape,
        color = backgroundColor
    ) {
        Box(
            modifier = Modifier
                .size(44.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (approved) {
                    Icons.Default.Check
                } else {
                    Icons.Default.Close
                },
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(
    name = "Movimentação aprovada",
    showBackground = true
)
@Composable
private fun RecentTransactionApprovedPreview() {
    CieloPayLabTheme {
        RecentTransactionItem(
            transaction = Transaction(
                id = "1",
                amountInCents = 5800L,
                paymentType = PaymentType.DEBIT,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}

@Preview(
    name = "Movimentação cancelada",
    showBackground = true
)
@Composable
private fun RecentTransactionCancelledPreview() {
    CieloPayLabTheme {
        RecentTransactionItem(
            transaction = Transaction(
                id = "2",
                amountInCents = 12000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.CANCELLED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 300L
            ),
            onClick = {},
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}