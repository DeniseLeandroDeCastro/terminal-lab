package br.com.denisecastro.cielopaylab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.denisecastro.cielopaylab.ui.home.screen.HomeScreen
import br.com.denisecastro.cielopaylab.ui.navigation.AppNavHost
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CieloPayApp()
        }
    }
}

@Composable
fun CieloPayApp() {
    CieloPayLabTheme {
        val navController = rememberNavController()
        AppNavHost(
            navController = navController
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun CieloPayAppPreview() {
    CieloPayLabTheme {
        HomeScreen(onNewPayment = {})
    }
}