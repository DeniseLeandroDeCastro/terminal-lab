package br.com.denisecastro.cielopaylab.ui.history.components.summary

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
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Total aprovado",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                            .copy(alpha = 0.70f)
                    )

                    Text(
                        text = periodLabel,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                            .copy(alpha = 0.60f)
                    )
                }

                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary
                                .copy(alpha = 0.12f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.TrendingUp,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Text(
                text = CurrencyUtils.formatFromCents(
                    approvedAmountInCents
                ),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = approvedCount.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = if (approvedCount == 1) {
                        "venda aprovada"
                    } else {
                        "vendas aprovadas"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                        .copy(alpha = 0.70f)
                )
            }
        }
    }
}

@Preview(
    name = "Resumo com vendas",
    showBackground = true
)
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

@Preview(
    name = "Resumo sem vendas",
    showBackground = true
)
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