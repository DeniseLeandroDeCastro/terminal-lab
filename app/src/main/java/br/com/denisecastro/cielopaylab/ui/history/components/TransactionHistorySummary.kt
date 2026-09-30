package br.com.denisecastro.cielopaylab.ui.history.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun TransactionHistorySummary(
    approvedCount: Int,
    approvedAmountInCents: Long,
    periodLabel: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Resumo de vendas • $periodLabel",
                style = MaterialTheme.typography.titleMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Vendas aprovadas",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = approvedCount.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                Column {
                    Text(
                        text = "Total vendido",
                        style = MaterialTheme.typography.bodySmall
                    )

                    Text(
                        text = CurrencyUtils.formatFromCents(
                            approvedAmountInCents
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

@Preview(name = "Resumo com vendas", showBackground = true)
@Composable
fun TransactionHistorySummaryPreview() {
    CieloPayLabTheme {
        TransactionHistorySummary(
            approvedCount = 8,
            approvedAmountInCents = 128000L,
            periodLabel = "Hoje",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Resumo sem vendas", showBackground = true)
@Composable
fun TransactionHistorySummaryEmptyPreview() {
    CieloPayLabTheme {
        TransactionHistorySummary(
            approvedCount = 0,
            approvedAmountInCents = 0L,
            periodLabel = "Todos",
            modifier = Modifier.padding(16.dp)
        )
    }
}