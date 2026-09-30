package br.com.denisecastro.cielopaylab.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun SalesSummaryCard(
    totalAmountInCents: Long,
    totalTransactions: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Vendas de hoje",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = "Total aprovado",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(
                            alpha = 0.75f
                        )
                    )
                }

                Icon(
                    imageVector = Icons.Default.Payments,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Text(
                text = CurrencyUtils.formatFromCents(
                    totalAmountInCents
                ),
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = when (totalTransactions) {
                    0 -> "Nenhuma venda aprovada hoje"
                    1 -> "1 venda aprovada hoje"
                    else -> "$totalTransactions vendas aprovadas hoje"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(
                    alpha = 0.85f
                )
            )
        }
    }
}

@Preview(
    name = "Resumo de vendas",
    showBackground = true
)
@Composable
fun SalesSummaryCardPreview() {
    CieloPayLabTheme {
        SalesSummaryCard(
            totalAmountInCents = 158000L,
            totalTransactions = 10,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(
    name = "Resumo sem vendas",
    showBackground = true
)
@Composable
fun SalesSummaryCardEmptyPreview() {
    CieloPayLabTheme {
        SalesSummaryCard(
            totalAmountInCents = 0L,
            totalTransactions = 0,
            modifier = Modifier.padding(16.dp)
        )
    }
}