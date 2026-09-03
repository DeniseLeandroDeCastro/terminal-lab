package br.com.denisecastro.cielopaylab.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.theme.BotaoDesabilitado

@Composable
fun LoadingButton(
    text: String,
    isLoading: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = Color(0xFF1565C0),
    contentColor: Color = Color.White,
    loadingColor: Color = Color(0xFF616161)
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled && !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = if (isLoading) loadingColor else BotaoDesabilitado,
            disabledContentColor = Color.White
        )
    ) {
        if (isLoading) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )

                Text(
                    text = "Aguarde...",
                    color = Color.White
                )
            }
        } else {
            Text(text = text)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingButtonPreview() {
    LoadingButton(
        text = "Processar venda",
        isLoading = true,
        enabled = true,
        onClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}