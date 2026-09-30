package br.com.denisecastro.cielopaylab.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.denisecastro.cielopaylab.ui.history.model.TransactionPeriod
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun TransactionPeriodMenu(
    selectedPeriod: TransactionPeriod,
    onPeriodSelected: (TransactionPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
    ) {
        TextButton(
            onClick = {
                expanded = true
            }
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null
            )

            Text(
                text = selectedPeriod.label
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Opções de período"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            TransactionPeriod.entries.forEach { period ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = period.label,
                            color = if (period == selectedPeriod) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    },
                    onClick = {
                        onPeriodSelected(period)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(
    name = "Período - Todos",
    showBackground = true
)
@Composable
fun TransactionPeriodMenuAllPreview() {
    CieloPayLabTheme {
        TransactionPeriodMenu(
            selectedPeriod = TransactionPeriod.ALL,
            onPeriodSelected = {}
        )
    }
}

@Preview(
    name = "Período - Últimos 7 dias",
    showSystemUi = true
)
@Composable
fun TransactionPeriodMenuLast7DaysPreview() {
    CieloPayLabTheme {
        TransactionPeriodMenu(
            selectedPeriod = TransactionPeriod.LAST_7_DAYS,
            onPeriodSelected = {}
        )
    }
}