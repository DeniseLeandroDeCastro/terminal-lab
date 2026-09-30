package br.com.denisecastro.cielopaylab.ui.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.denisecastro.cielopaylab.ui.theme.BotaoDesabilitado
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme

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
        modifier = modifier
            .height(56.dp),
        enabled = enabled && !isLoading,
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContainerColor = if (isLoading) {
                loadingColor
            } else {
                BotaoDesabilitado
            },
            disabledContentColor = Color.White
        )
    ) {

        if (isLoading) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )

                Text(
                    text = "Aguarde...",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }

        } else {

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontWeight = FontWeight.SemiBold
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview(
    name = "Botão habilitado",
    showBackground = true
)
@Composable
fun LoadingButtonEnabledPreview() {
    CieloPayLabTheme {
        LoadingButton(
            text = "Processar venda",
            isLoading = false,
            enabled = true,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "Botão processando",
    showBackground = true
)
@Composable
fun LoadingButtonLoadingPreview() {
    CieloPayLabTheme {
        LoadingButton(
            text = "Processar venda",
            isLoading = true,
            enabled = true,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(
    name = "Botão desabilitado",
    showBackground = true
)
@Composable
fun LoadingButtonDisabledPreview() {
    CieloPayLabTheme {
        LoadingButton(
            text = "Processar venda",
            isLoading = false,
            enabled = false,
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}