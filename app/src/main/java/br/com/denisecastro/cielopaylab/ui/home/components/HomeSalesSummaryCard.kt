package br.com.denisecastro.cielopaylab.ui.home.components

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
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun HomeSalesSummaryCard(
    approvedAmountInCents: Long,
    approvedCount: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Vendas de hoje",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = CurrencyUtils.formatFromCents(
                    approvedAmountInCents
                ),
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = if (approvedCount == 1) {
                    "1 venda aprovada"
                } else {
                    "$approvedCount vendas aprovadas"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(name = "Resumo com vendas", showBackground = true)
@Composable
fun HomeSalesSummaryCardPreview() {
    CieloPayLabTheme {
        HomeSalesSummaryCard(
            approvedAmountInCents = 45000L,
            approvedCount = 4,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(name = "Resumo sem vendas", showBackground = true)
@Composable
fun HomeSalesSummaryCardEmptyPreview() {
    CieloPayLabTheme {
        HomeSalesSummaryCard(
            approvedAmountInCents = 0L,
            approvedCount = 0,
            modifier = Modifier.padding(16.dp)
        )
    }
}