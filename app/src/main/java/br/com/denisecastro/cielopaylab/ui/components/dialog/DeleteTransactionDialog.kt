package br.com.denisecastro.cielopaylab.ui.components.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

@Composable
fun DeleteTransactionDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Excluir venda")
        },
        text = {
            Text(
                text = "Tem certeza de que deseja excluir esta venda? Esta ação não poderá ser desfeita."
            )
        },
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(
                    text = "Excluir",
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

@Preview(name = "Confirmação de exclusão", showSystemUi = true)
@Composable
fun DeleteTransactionDialogPreview() {
    CieloPayLabTheme {
        DeleteTransactionDialog(
            onConfirm = {},
            onDismiss = {}
        )
    }
}