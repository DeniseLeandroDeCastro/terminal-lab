package br.com.denisecastro.cielopaylab.ui.home.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.components.LoadingButton
import br.com.denisecastro.cielopaylab.ui.theme.BotaoHistorico
import br.com.denisecastro.cielopaylab.ui.theme.BotaoNovaVenda

@Composable
fun HomeScreen(
    onNewPayment: () -> Unit,
    onHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp,32.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "CieloPayLab",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Escolha uma opção",
            style = MaterialTheme.typography.titleMedium
        )

        LoadingButton(
            text = "Nova venda",
            isLoading = false,
            enabled = true,
            onClick = onNewPayment,
            modifier = Modifier.fillMaxWidth(),
            containerColor = BotaoNovaVenda,
            contentColor = Color.White
        )

        LoadingButton(
            text = "Histórico de transações",
            isLoading = false,
            enabled = true,
            onClick = onHistory,
            modifier = Modifier.fillMaxWidth(),
            containerColor = BotaoHistorico,
            contentColor = Color.White
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onNewPayment = {},
        onHistory = {}
    )
}