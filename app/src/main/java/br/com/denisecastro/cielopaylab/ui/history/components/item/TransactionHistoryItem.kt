package br.com.denisecastro.cielopaylab.ui.history.components.item

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
fun TransactionHistoryItem(
    transaction: Transaction,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = transaction.status.toStatusColor()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(
                vertical = 14.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        Box(
            modifier = Modifier
                .size(44.dp)
                .background(
                    color = statusColor.copy(alpha = 0.12f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = transaction.status.toStatusIcon(),
                contentDescription = null,
                tint = statusColor,
                modifier = Modifier.size(22.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                text = transaction.paymentType.toDisplayName(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = transaction.status.toDisplayName(),
                style = MaterialTheme.typography.bodyMedium,
                color = statusColor
            )

            Text(
                text = transaction.timestamp.toFormattedDate(),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            Text(
                text = CurrencyUtils.formatFromCents(
                    transaction.amountInCents
                ),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${transaction.responseTimeMillis} ms",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Ver detalhes",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun TransactionStatus.toStatusColor(): Color {
    return when (this) {
        TransactionStatus.APPROVED ->
            Color(0xFF148A5B)

        TransactionStatus.DECLINED ->
            MaterialTheme.colorScheme.error

        TransactionStatus.ERROR ->
            MaterialTheme.colorScheme.error

        TransactionStatus.CANCELLED ->
            MaterialTheme.colorScheme.onSurfaceVariant
    }
}

private fun TransactionStatus.toStatusIcon(): ImageVector {
    return when (this) {
        TransactionStatus.APPROVED ->
            Icons.Default.Check

        TransactionStatus.DECLINED ->
            Icons.Default.Close

        TransactionStatus.ERROR ->
            Icons.Default.ErrorOutline

        TransactionStatus.CANCELLED ->
            Icons.Default.Remove
    }
}

@Preview(
    name = "Transação aprovada",
    showBackground = true
)
@Composable
fun TransactionHistoryItemApprovedPreview() {
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
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@Preview(
    name = "Transação recusada",
    showBackground = true
)
@Composable
fun TransactionHistoryItemDeclinedPreview() {
    CieloPayLabTheme {
        TransactionHistoryItem(
            transaction = Transaction(
                id = "456",
                amountInCents = 8990L,
                paymentType = PaymentType.CREDIT,
                status = TransactionStatus.DECLINED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 430L
            ),
            onClick = {},
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}