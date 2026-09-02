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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    transactions: List<Transaction>,
    onTransactionClick: (Transaction) -> Unit,
    onBack: () -> Unit
) {
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

        if (transactions.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp)
            ) {
                Text(text = "Nenhuma transação encontrada.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = transactions,
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
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 310L
        )
    )

    CieloPayLabTheme {
        TransactionHistoryScreen(
            transactions = transactions,
            onBack = {},
            onTransactionClick = {}
        )
    }
}

@Preview(name = "Histórico vazio", showSystemUi = true)
@Composable
fun TransactionHistoryEmptyPreview() {
    CieloPayLabTheme {
        TransactionHistoryScreen(
            transactions = emptyList(),
            onBack = {},
            onTransactionClick = {}
        )
    }
}