package br.com.denisecastro.cielopaylab.ui.details.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.components.button.LoadingButton
import br.com.denisecastro.cielopaylab.ui.components.dialog.CancelTransactionDialog
import br.com.denisecastro.cielopaylab.ui.components.dialog.DeleteTransactionDialog
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import br.com.denisecastro.cielopaylab.ui.utils.toDisplayName
import br.com.denisecastro.cielopaylab.ui.utils.toFormattedDate
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import br.com.denisecastro.cielopaylab.ui.details.components.TransactionDetailsHeader

@Composable
fun TransactionDetailsScreen(
    transaction: Transaction?,
    isLoading: Boolean,
    isCancelling: Boolean,
    isDeleting: Boolean,
    errorMessage: String?,
    onCancelTransaction: () -> Unit,
    onDeleteTransaction: () -> Unit,
    onBack: () -> Unit
) {
    var showCancelDialog by remember {
        mutableStateOf(false)
    }

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        TransactionDetailsHeader(
            onBack = onBack
        )

        when {
            isLoading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = "Carregando transação...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            transaction != null -> {
                TransactionDetailsContent(
                    transaction = transaction,
                    isCancelling = isCancelling,
                    isDeleting = isDeleting,
                    errorMessage = errorMessage,
                    onCancelTransaction = {
                        showCancelDialog = true
                    },
                    onDeleteTransaction = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                )
            }

            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    Text(
                        text = errorMessage ?: "Transação não encontrada.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    if (showCancelDialog) {
        CancelTransactionDialog(
            onConfirm = {
                showCancelDialog = false
                onCancelTransaction()
            },
            onDismiss = {
                showCancelDialog = false
            }
        )
    }

    if (showDeleteDialog) {
        DeleteTransactionDialog(
            onConfirm = {
                showDeleteDialog = false
                onDeleteTransaction()
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }
}

@Composable
private fun TransactionDetailsContent(
    transaction: Transaction,
    isCancelling: Boolean,
    isDeleting: Boolean,
    errorMessage: String?,
    onCancelTransaction: () -> Unit,
    onDeleteTransaction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val statusColor = transaction.status.toStatusColor()

    Column(
        modifier = modifier.verticalScroll(
            rememberScrollState()
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // Status
        Box(
            modifier = Modifier
                .size(72.dp)
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
                modifier = Modifier.size(34.dp)
            )
        }

        // Valor e status
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = CurrencyUtils.formatFromCents(
                    transaction.amountInCents
                ),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = transaction.status.toDisplayName(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = statusColor
            )
        }

        // Informações da transação
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
                    .copy(alpha = 0.40f)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TransactionDetailRow(
                    label = "Forma de pagamento",
                    value = transaction.paymentType.toDisplayName()
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                TransactionDetailRow(
                    label = "Data e hora",
                    value = transaction.timestamp.toFormattedDate()
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                TransactionDetailRow(
                    label = "Tempo de resposta",
                    value = "${transaction.responseTimeMillis} ms"
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                TransactionDetailRow(
                    label = "ID da transação",
                    value = transaction.id.toShortTransactionId()
                )
            }
        }

        errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Ações
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (transaction.status == TransactionStatus.APPROVED) {
                LoadingButton(
                    text = "Cancelar venda",
                    isLoading = isCancelling,
                    enabled = !isDeleting,
                    onClick = onCancelTransaction,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    loadingColor = MaterialTheme.colorScheme.primary
                )
            }

            TextButton(
                onClick = onDeleteTransaction,
                enabled = !isCancelling && !isDeleting,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isDeleting) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.error
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Excluindo...",
                        color = MaterialTheme.colorScheme.error
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text(
                        text = "Excluir transação",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )
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

@Composable
private fun TransactionDetailRow(
    label: String,
    value: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun String.toShortTransactionId(): String {
    return if (length > 20) {
        "${take(8)}...${takeLast(8)}"
    } else {
        this
    }
}

@Composable
private fun TransactionStatus.toStatusColor() = when (this) {
    TransactionStatus.APPROVED -> MaterialTheme.colorScheme.primary
    TransactionStatus.DECLINED -> MaterialTheme.colorScheme.error
    TransactionStatus.ERROR -> MaterialTheme.colorScheme.error
    TransactionStatus.CANCELLED -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Preview(name = "Transação aprovada", showSystemUi = true)
@Composable
fun TransactionDetailsApprovedPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = Transaction(
                id = "123e4567-e89b-12d3-a456-426614174000",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            isLoading = false,
            isCancelling = false,
            isDeleting = false,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Transação recusada", showSystemUi = true)
@Composable
fun TransactionDetailsDeclinedPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = Transaction(
                id = "987e6543-e21b-45d3-b654-123456789000",
                amountInCents = 8990L,
                paymentType = PaymentType.CREDIT,
                status = TransactionStatus.DECLINED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 430L
            ),
            isLoading = false,
            isCancelling = false,
            isDeleting = false,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Cancelando transação", showSystemUi = true)
@Composable
fun TransactionDetailsCancellingPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = Transaction(
                id = "123e4567-e89b-12d3-a456-426614174000",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            isLoading = false,
            isCancelling = true,
            isDeleting = false,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Excluindo transação", showSystemUi = true)
@Composable
fun TransactionDetailsDeletingPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = Transaction(
                id = "123e4567-e89b-12d3-a456-426614174000",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            isLoading = false,
            isCancelling = false,
            isDeleting = true,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Transação cancelada", showSystemUi = true)
@Composable
fun TransactionDetailsCancelledPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = Transaction(
                id = "456e7890-e89b-12d3-a456-426614174111",
                amountInCents = 15000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.CANCELLED,
                timestamp = System.currentTimeMillis(),
                responseTimeMillis = 250L
            ),
            isLoading = false,
            isCancelling = false,
            isDeleting = false,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Carregando transação", showSystemUi = true)
@Composable
fun TransactionDetailsLoadingPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = null,
            isLoading = true,
            isCancelling = false,
            isDeleting = false,
            errorMessage = null,
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}

@Preview(name = "Transação não encontrada", showSystemUi = true)
@Composable
fun TransactionDetailsNotFoundPreview() {
    CieloPayLabTheme {
        TransactionDetailsScreen(
            transaction = null,
            isLoading = false,
            isCancelling = false,
            isDeleting = false,
            errorMessage = "Transação não encontrada.",
            onCancelTransaction = {},
            onDeleteTransaction = {},
            onBack = {}
        )
    }
}