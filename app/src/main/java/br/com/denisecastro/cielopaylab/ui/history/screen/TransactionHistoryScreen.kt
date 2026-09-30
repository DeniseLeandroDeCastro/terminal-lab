package br.com.denisecastro.cielopaylab.ui.history.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.history.components.TransactionHistoryItem
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.denisecastro.cielopaylab.ui.components.filterbar.TransactionFilterBar
import br.com.denisecastro.cielopaylab.ui.components.menu.TransactionSortMenu
import br.com.denisecastro.cielopaylab.ui.history.model.TransactionFilter
import br.com.denisecastro.cielopaylab.ui.history.model.TransactionSort

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    onBack: () -> Unit
) {
    var selectedFilter by remember {
        mutableStateOf(TransactionFilter.ALL)
    }

    var selectedSort by remember {
        mutableStateOf(TransactionSort.NEWEST)
    }

    val filteredTransactions = when (selectedFilter) {
        TransactionFilter.ALL -> transactions

        TransactionFilter.APPROVED -> transactions.filter {
            it.status == TransactionStatus.APPROVED
        }

        TransactionFilter.CANCELLED -> transactions.filter {
            it.status == TransactionStatus.CANCELLED
        }

        TransactionFilter.DECLINED -> transactions.filter {
            it.status == TransactionStatus.DECLINED
        }
    }

    val sortedTransactions = when (selectedSort) {
        TransactionSort.NEWEST ->
            filteredTransactions.sortedByDescending {
                it.timestamp
            }

        TransactionSort.OLDEST ->
            filteredTransactions.sortedBy {
                it.timestamp
            }

        TransactionSort.HIGHEST_VALUE ->
            filteredTransactions.sortedByDescending {
                it.amountInCents
            }

        TransactionSort.LOWEST_VALUE ->
            filteredTransactions.sortedBy {
                it.amountInCents
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Histórico de transações")
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            TransactionFilterBar(
                selectedFilter = selectedFilter,
                onFilterSelected = { filter ->
                    selectedFilter = filter
                },
                modifier = Modifier.padding(
                    horizontal = 24.dp,
                    vertical = 8.dp
                )
            )

            TransactionSortMenu(
                selectedSort = selectedSort,
                onSortSelected = { sort ->
                    selectedSort = sort
                },
                modifier = Modifier.padding(
                    horizontal = 24.dp
                )
            )

            if (filteredTransactions.isEmpty()) {
                Text(
                    text = if (transactions.isEmpty()) {
                        "Nenhuma transação encontrada."
                    } else {
                        "Nenhuma transação para este filtro."
                    },
                    modifier = Modifier.padding(24.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = sortedTransactions,
                        key = { transaction ->
                            transaction.id
                        }
                    ) { transaction ->

                        TransactionHistoryItem(
                            transaction = transaction,
                            onClick = {
                                onTransactionClick(transaction)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(name = "Histórico preenchido", showSystemUi = true)
@Composable
fun TransactionHistoryFilledPreview() {

    val transactions = listOf(
        Transaction(
            id = "1",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        ),
        Transaction(
            id = "2",
            amountInCents = 8990L,
            paymentType = PaymentType.CREDIT,
            status = TransactionStatus.DECLINED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 430L
        ),
        Transaction(
            id = "3",
            amountInCents = 22500L,
            paymentType = PaymentType.DEBIT,
            status = TransactionStatus.CANCELLED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 310L
        )
    )

    CieloPayLabTheme {
        TransactionHistoryScreen(
            transactions = transactions,
            onTransactionClick = {},
            onBack = {}
        )
    }
}

@Preview(name = "Histórico vazio", showSystemUi = true)
@Composable
fun TransactionHistoryEmptyPreview() {
    CieloPayLabTheme {
        TransactionHistoryScreen(
            transactions = emptyList(),
            onTransactionClick = {},
            onBack = {}
        )
    }
}