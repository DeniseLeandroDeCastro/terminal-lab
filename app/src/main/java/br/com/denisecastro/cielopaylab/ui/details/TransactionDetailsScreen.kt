package br.com.denisecastro.cielopaylab.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (transaction == null) {
                Text(text = "Carregando transação...")
            } else {
                Text(text = "ID da transação:")
                Text(text = transaction.id)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TransactionDetailsScreenPreview() {
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