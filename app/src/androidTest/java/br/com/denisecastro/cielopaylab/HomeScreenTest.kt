package br.com.denisecastro.cielopaylab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.denisecastro.cielopaylab.ui.home.screen.HomeScreen
import br.com.denisecastro.cielopaylab.ui.home.state.HomeUiState
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import org.junit.Rule
import org.junit.Test
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import androidx.compose.ui.test.onNodeWithContentDescription

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayHomeShortcuts() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(),
                    onNewPayment = {},
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Nova venda")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Histórico")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallNewPaymentWhenNewPaymentIsClicked() {

        var newPaymentCalled = false

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(),
                    onNewPayment = {
                        newPaymentCalled = true
                    },
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Nova venda")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                newPaymentCalled
            )
        }
    }

    @Test
    fun shouldCallHistoryWhenHistoryIsClicked() {

        var historyCalled = false

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(),
                    onNewPayment = {},
                    onHistory = {
                        historyCalled = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Histórico")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                historyCalled
            )
        }
    }

    @Test
    fun shouldDisplayRecentTransactionsWhenThereAreTransactions() {

        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        recentTransactions = listOf(transaction),
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Últimas movimentações")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Ver todas")
            .assertIsDisplayed()
    }

    @Test
    fun shouldNotDisplayRecentTransactionsWhenListIsEmpty() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        recentTransactions = emptyList(),
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Últimas movimentações")
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText("Ver todas")
            .assertDoesNotExist()
    }

    @Test
    fun shouldDisplayHomeBalanceSummary() {

        val totalAmountInCents = 158000L

        val formattedAmount = CurrencyUtils.formatFromCents(
            totalAmountInCents
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        totalAmountInCents = totalAmountInCents,
                        totalTransactions = 12,
                        approvedTransactions = 10,
                        cancelledTransactions = 2,
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("TOTAL APROVADO HOJE")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(formattedAmount)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("10 aprovadas")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("2 canceladas")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallHistoryWhenSeeAllIsClicked() {

        var historyCalled = false

        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        recentTransactions = listOf(transaction),
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {
                        historyCalled = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Ver todas")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                historyCalled
            )
        }
    }

    @Test
    fun shouldDisplayRecentTransactionData() {

        val transaction = Transaction(
            id = "123",
            amountInCents = 5800L,
            paymentType = PaymentType.DEBIT,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        val formattedAmount = CurrencyUtils.formatFromCents(
            transaction.amountInCents
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        recentTransactions = listOf(transaction),
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Débito")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(formattedAmount)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Ver detalhes")
            .assertIsDisplayed()
    }

    @Test
    fun shouldCallHistoryWhenRecentTransactionIsClicked() {

        var historyCalled = false

        val transaction = Transaction(
            id = "123",
            amountInCents = 5800L,
            paymentType = PaymentType.DEBIT,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                HomeScreen(
                    state = HomeUiState(
                        recentTransactions = listOf(transaction),
                        isLoading = false
                    ),
                    onNewPayment = {},
                    onHistory = {
                        historyCalled = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Ver detalhes")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                historyCalled
            )
        }
    }
}