package br.com.denisecastro.cielopaylab.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun CancelTransactionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Cancelar venda")
        },
        text = {
            Text(
                text = "Tem certeza de que deseja cancelar esta venda?"
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Confirmar",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text(text = "Voltar")
            }
        }
    )
}

@Preview(name = "Confirmação de cancelamento", showSystemUi = true)
@Composable
fun CancelTransactionDialogPreview() {
    CieloPayLabTheme {
        CancelTransactionDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}