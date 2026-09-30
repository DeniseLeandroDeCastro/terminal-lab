package br.com.denisecastro.cielopaylab.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Pix
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun PaymentTypeSelector(
    selectedPaymentType: PaymentType,
    enabled: Boolean,
    onPaymentTypeChanged: (PaymentType) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        PaymentType.entries.forEach { type ->

            PaymentTypeOption(
                paymentType = type,
                selected = selectedPaymentType == type,
                enabled = enabled,
                onClick = {
                    onPaymentTypeChanged(type)
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun PaymentTypeOption(
    paymentType: PaymentType,
    selected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val containerColor = if (selected) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    val contentColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outline
    }

    Card(
        modifier = modifier
            .clickable(
                enabled = enabled,
                onClick = onClick
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            disabledContainerColor = containerColor.copy(alpha = 0.5f)
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 1.dp,
            color = borderColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 2.dp else 0.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 8.dp,
                    vertical = 18.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = paymentType.toIcon(),
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(26.dp)
            )

            Text(
                text = paymentType.toDisplayName(),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (selected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Medium
                },
                color = contentColor
            )
        }
    }
}

private fun PaymentType.toIcon(): ImageVector {
    return when (this) {
        PaymentType.CREDIT -> Icons.Default.CreditCard
        PaymentType.DEBIT -> Icons.Default.CreditCard
        PaymentType.PIX -> Icons.Default.Pix
    }
}

private fun PaymentType.toDisplayName(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.PIX -> "Pix"
    }
}

@Preview(
    name = "Pagamento - Crédito",
    showBackground = true
)
@Composable
fun PaymentTypeSelectorCreditPreview() {
    CieloPayLabTheme {
        PaymentTypeSelector(
            selectedPaymentType = PaymentType.CREDIT,
            enabled = true,
            onPaymentTypeChanged = {}
        )
    }
}

@Preview(
    name = "Pagamento - Pix",
    showBackground = true
)
@Composable
fun PaymentTypeSelectorPixPreview() {
    CieloPayLabTheme {
        PaymentTypeSelector(
            selectedPaymentType = PaymentType.PIX,
            enabled = true,
            onPaymentTypeChanged = {}
        )
    }
}