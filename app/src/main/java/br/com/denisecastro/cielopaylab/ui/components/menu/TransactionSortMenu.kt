package br.com.denisecastro.cielopaylab.ui.components.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.SwapVert
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
import br.com.denisecastro.cielopaylab.ui.history.model.TransactionSort
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun TransactionSortMenu(
    selectedSort: TransactionSort,
    onSortSelected: (TransactionSort) -> Unit,
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
                imageVector = Icons.Default.SwapVert,
                contentDescription = null
            )

            Text(
                text = selectedSort.label
            )

            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Opções de ordenação"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            TransactionSort.entries.forEach { sort ->

                DropdownMenuItem(
                    text = {
                        Text(
                            text = sort.label,
                            color = if (sort == selectedSort) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    },
                    onClick = {
                        onSortSelected(sort)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(
    name = "Ordenação - Mais recentes",
    showBackground = true
)
@Composable
fun TransactionSortMenuNewestPreview() {
    CieloPayLabTheme {
        TransactionSortMenu(
            selectedSort = TransactionSort.NEWEST,
            onSortSelected = {}
        )
    }
}

@Preview(
    name = "Ordenação - Maior valor",
    showSystemUi = true
)
@Composable
fun TransactionSortMenuHighestValuePreview() {
    CieloPayLabTheme {
        TransactionSortMenu(
            selectedSort = TransactionSort.HIGHEST_VALUE,
            onSortSelected = {}
        )
    }
}