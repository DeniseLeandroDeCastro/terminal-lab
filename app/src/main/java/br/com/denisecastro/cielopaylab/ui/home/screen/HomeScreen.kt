package br.com.denisecastro.cielopaylab.ui.home.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.home.components.HomeBalanceHeader
import br.com.denisecastro.cielopaylab.ui.home.state.HomeUiState
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.filled.Add
import br.com.denisecastro.cielopaylab.ui.home.components.HomeShortcutCard
import br.com.denisecastro.cielopaylab.ui.home.components.RecentTransactionItem
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun HomeScreen(
    state: HomeUiState,
    onNewPayment: () -> Unit,
    onHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        HomeBalanceHeader(
            totalAmountInCents = state.totalAmountInCents,
            approvedTransactions = state.approvedTransactions,
            cancelledTransactions = state.cancelledTransactions
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 24.dp,
                    vertical = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Acesso rápido",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                HomeShortcutCard(
                    title = "Nova venda",
                    description = "Criar transação",
                    icon = Icons.Default.Add,
                    onClick = onNewPayment,
                    modifier = Modifier.weight(1f)
                )

                HomeShortcutCard(
                    title = "Histórico",
                    description = "Ver movimentações",
                    icon = Icons.Default.History,
                    onClick = onHistory,
                    modifier = Modifier.weight(1f)
                )
            }

            if (state.recentTransactions.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Últimas movimentações",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        TextButton(
                            onClick = onHistory
                        ) {
                            Text(
                                text = "Ver todas"
                            )
                        }
                    }

                    state.recentTransactions.forEach { transaction ->

                        RecentTransactionItem(
                            transaction = transaction,
                            onClick = onHistory
                        )
                    }
                }
            }
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