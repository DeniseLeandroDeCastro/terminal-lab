package br.com.denisecastro.cielopaylab.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.domain.model.PaymentType

@Composable
fun PaymentTypeSelector(
    selectedPaymentType: PaymentType,
    enabled: Boolean,
    onPaymentTypeChanged: (PaymentType) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PaymentType.entries.forEach { type ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedPaymentType == type,
                    onClick = { onPaymentTypeChanged(type) },
                    enabled = enabled
                )

                Text(
                    text = type.toDisplayName()
                )
            }
        }
    }
}

private fun PaymentType.toDisplayName(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.PIX -> "Pix"
    }
}

@Preview(showSystemUi = true)
@Composable
fun PaymentTypeSelectorPreview() {
    PaymentTypeSelector(
        selectedPaymentType = PaymentType.CREDIT,
        enabled = true,
        onPaymentTypeChanged = {}
    )
}