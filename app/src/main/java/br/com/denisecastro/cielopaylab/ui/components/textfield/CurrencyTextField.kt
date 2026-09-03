package br.com.denisecastro.cielopaylab.ui.components.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils

@Composable
fun CurrencyTextField(
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val amountInCents = value.toLongOrNull() ?: 0L
    val formattedAmount = CurrencyUtils.formatFromCents(amountInCents)

    OutlinedTextField(
        value = formattedAmount,
        onValueChange = onValueChange,
        label = { Text("Valor") },
        modifier = modifier,
        enabled = enabled
    )
}

@Preview(showBackground = true)
@Composable
fun CurrencyTextFieldPreview() {
    CurrencyTextField(
        value = "15000",
        enabled = true,
        onValueChange = {},
        modifier = Modifier.fillMaxWidth()
    )
}