package br.com.denisecastro.cielopaylab.ui.components.filterbar

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.history.model.TransactionFilter
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun TransactionFilterBar(
    selectedFilter: TransactionFilter,
    onFilterSelected: (TransactionFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(
            rememberScrollState()
        ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TransactionFilter.entries.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = {
                    onFilterSelected(filter)
                },
                label = {
                    Text(text = filter.label)
                }
            )
        }
    }
}

@Preview(
    name = "Filtro - Todas",
    showSystemUi = true
)
@Composable
fun TransactionFilterBarAllPreview() {
    CieloPayLabTheme {
        TransactionFilterBar(
            selectedFilter = TransactionFilter.ALL,
            onFilterSelected = {}
        )
    }
}

@Preview(
    name = "Filtro - Canceladas",
    showSystemUi = true
)
@Composable
fun TransactionFilterBarCancelledPreview() {
    CieloPayLabTheme {
        TransactionFilterBar(
            selectedFilter = TransactionFilter.CANCELLED,
            onFilterSelected = {}
        )
    }
}