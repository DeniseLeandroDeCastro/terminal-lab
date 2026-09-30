package br.com.denisecastro.cielopaylab.ui.components.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun CurrencyTextField(
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val amountInCents = value.toLongOrNull() ?: 0L
    val formattedAmount = CurrencyUtils.formatFromCents(amountInCents)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(
                horizontal = 24.dp,
                vertical = 28.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        Text(
            text = "VALOR DA VENDA",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
                .copy(alpha = 0.70f)
        )

        BasicTextField(
            value = formattedAmount,
            onValueChange = { newValue ->

                val digits = newValue.filter { character ->
                    character.isDigit()
                }

                onValueChange(digits)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(
                MaterialTheme.colorScheme.primary
            )
        )

        Text(
            text = "Digite o valor da transação",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
                .copy(alpha = 0.65f)
        )
    }
}

@Preview(
    name = "Valor preenchido",
    showBackground = true
)
@Composable
fun CurrencyTextFieldPreview() {
    CieloPayLabTheme {
        CurrencyTextField(
            value = "15000",
            enabled = true,
            onValueChange = {},
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Preview(
    name = "Valor zerado",
    showBackground = true
)
@Composable
fun CurrencyTextFieldEmptyPreview() {
    CieloPayLabTheme {
        CurrencyTextField(
            value = "",
            enabled = true,
            onValueChange = {},
            modifier = Modifier.padding(24.dp)
        )
    }
}