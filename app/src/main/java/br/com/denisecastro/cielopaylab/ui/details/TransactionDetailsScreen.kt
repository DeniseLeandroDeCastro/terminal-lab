package br.com.denisecastro.cielopaylab.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import br.com.denisecastro.cielopaylab.ui.utils.toDisplayName
import br.com.denisecastro.cielopaylab.ui.utils.toFormattedDate
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailsScreen(
    transaction: Transaction?,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Detalhes da transação")
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

        if (transaction == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp)
            ) {
                Text(text = "Carregando transação...")
            }
        } else {
            TransactionDetailsContent(
                transaction = transaction,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp)
            )
        }
    }
}

@Composable
private fun TransactionDetailsContent(
    transaction: Transaction,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = CurrencyUtils.formatFromCents(
                transaction.amountInCents
            ),
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = transaction.status.toDisplayName(),
            style = MaterialTheme.typography.titleMedium,
            color = transaction.status.toStatusColor()
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionDetailRow(
                    label = "Forma de pagamento",
                    value = transaction.paymentType.toDisplayName()
                )

                HorizontalDivider()

                TransactionDetailRow(
                    label = "Data e hora",
                    value = transaction.timestamp.toFormattedDate()
                )

                HorizontalDivider()

                TransactionDetailRow(
                    label = "Tempo de resposta",
                    value = "${transaction.responseTimeMillis} ms"
                )

                HorizontalDivider()

                TransactionDetailRow(
                    label = "ID da transação",
                    value = transaction.id
                )
            }
        }
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

@Composable
private fun TransactionStatus.toStatusColor() =
    when (this) {
        TransactionStatus.APPROVED ->
            MaterialTheme.colorScheme.primary

        TransactionStatus.DECLINED ->
            MaterialTheme.colorScheme.error

        TransactionStatus.ERROR ->
            MaterialTheme.colorScheme.error
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
            onBack = {}
        )
    }
}