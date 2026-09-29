package br.com.denisecastro.cielopaylab.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.components.card.SalesSummaryCard
import br.com.denisecastro.cielopaylab.ui.components.card.TransactionStatusCard
import br.com.denisecastro.cielopaylab.ui.home.state.HomeUiState
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun HomeScreen(
    state: HomeUiState,
    onNewPayment: () -> Unit,
    onHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "CieloPayLab",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Acompanhe suas vendas",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        SalesSummaryCard(
            totalAmountInCents = state.totalAmountInCents,
            totalTransactions = state.totalTransactions
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TransactionStatusCard(
                title = "Aprovadas",
                value = state.approvedTransactions,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.weight(1f)
            )

            TransactionStatusCard(
                title = "Canceladas",
                value = state.cancelledTransactions,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier.weight(1f)
            )
        }

        Text(
            text = "Ações",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            onClick = onNewPayment,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            Text(text = "Nova venda")
        }

        OutlinedButton(
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Ver histórico")
        }
    }
}

@Preview(
    name = "Home com vendas",
    showSystemUi = true
)
@Composable
fun HomeScreenPreview() {
    CieloPayLabTheme {
        HomeScreen(
            state = HomeUiState(
                totalAmountInCents = 158000L,
                totalTransactions = 12,
                approvedTransactions = 10,
                cancelledTransactions = 2,
                isLoading = false
            ),
            onNewPayment = {},
            onHistory = {}
        )
    }
}

@Preview(
    name = "Home sem vendas",
    showSystemUi = true
)
@Composable
fun HomeScreenEmptyPreview() {
    CieloPayLabTheme {
        HomeScreen(
            state = HomeUiState(),
            onNewPayment = {},
            onHistory = {}
        )
    }
}