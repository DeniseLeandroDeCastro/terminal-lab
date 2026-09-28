package br.com.denisecastro.cielopaylab.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Total vendido",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = CurrencyUtils.formatFromCents(totalAmountInCents),
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = if (totalTransactions == 1) {
                    "1 transação"
                } else {
                    "$totalTransactions transações"
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalesSummaryCardPreview() {
    CieloPayLabTheme {
        SalesSummaryCard(
            totalAmountInCents = 158000L,
            totalTransactions = 12,
            modifier = Modifier.padding(16.dp)
        )
    }
}