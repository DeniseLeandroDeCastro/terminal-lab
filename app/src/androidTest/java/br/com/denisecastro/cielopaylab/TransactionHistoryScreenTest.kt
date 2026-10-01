package br.com.denisecastro.cielopaylab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.denisecastro.cielopaylab.ui.history.screen.TransactionHistoryScreen
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import org.junit.Rule
import org.junit.Test
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onLast
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import org.junit.Assert.assertEquals

class TransactionHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayEmptyMessageWhenThereAreNoTransactions() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = emptyList(),
                    onTransactionClick = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Nenhuma transação encontrada.")
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayTransactionWhenHistoryIsNotEmpty() {

        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        val formattedAmount = CurrencyUtils.formatFromCents(
            transaction.amountInCents
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = listOf(transaction),
                    onTransactionClick = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText(formattedAmount)
            .assertCountEquals(2)

        composeTestRule
            .onNodeWithText("Nenhuma transação encontrada.")
            .assertDoesNotExist()
    }

    @Test
    fun shouldDisplayOnlyApprovedTransactionsWhenApprovedFilterIsSelected() {

        val approvedTransaction = Transaction(
            id = "1",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        val cancelledTransaction = Transaction(
            id = "2",
            amountInCents = 8000L,
            paymentType = PaymentType.CREDIT,
            status = TransactionStatus.CANCELLED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 300L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = listOf(
                        approvedTransaction,
                        cancelledTransaction
                    ),
                    onTransactionClick = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Aprovadas")
            .performClick()

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    approvedTransaction.amountInCents
                )
            )
            .onFirst()
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    cancelledTransaction.amountInCents
                )
            )
            .assertCountEquals(0)
    }

    @Test
    fun shouldDisplayOnlyCancelledTransactionsWhenCancelledFilterIsSelected() {

        val approvedTransaction = Transaction(
            id = "1",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        val cancelledTransaction = Transaction(
            id = "2",
            amountInCents = 8000L,
            paymentType = PaymentType.CREDIT,
            status = TransactionStatus.CANCELLED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 300L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = listOf(
                        approvedTransaction,
                        cancelledTransaction
                    ),
                    onTransactionClick = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Canceladas")
            .performClick()

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    cancelledTransaction.amountInCents
                )
            )
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    approvedTransaction.amountInCents
                )
            )
            .assertCountEquals(1)
    }

    @Test
    fun shouldDisplayOnlyDeclinedTransactionsWhenDeclinedFilterIsSelected() {

        val approvedTransaction = Transaction(
            id = "1",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        val declinedTransaction = Transaction(
            id = "2",
            amountInCents = 9900L,
            paymentType = PaymentType.CREDIT,
            status = TransactionStatus.DECLINED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 300L
        )

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = listOf(
                        approvedTransaction,
                        declinedTransaction
                    ),
                    onTransactionClick = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Recusadas")
            .performClick()

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    declinedTransaction.amountInCents
                )
            )
            .assertCountEquals(1)

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    approvedTransaction.amountInCents
                )
            )
            .assertCountEquals(1)
    }

    @Test
    fun shouldCallOnTransactionClickWhenTransactionIsClicked() {

        val transaction = Transaction(
            id = "123",
            amountInCents = 7500L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 250L
        )

        var clickedTransaction: Transaction? = null

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = listOf(transaction),
                    onTransactionClick = { selectedTransaction ->
                        clickedTransaction = selectedTransaction
                    },
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText(
                CurrencyUtils.formatFromCents(
                    transaction.amountInCents
                )
            )
            .onLast()
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                transaction,
                clickedTransaction
            )
        }
    }

    @Test
    fun shouldCallOnBackWhenBackButtonIsClicked() {

        var backCalled = false

        composeTestRule.setContent {
            CieloPayLabTheme {
                TransactionHistoryScreen(
                    transactions = emptyList(),
                    onTransactionClick = {},
                    onBack = {
                        backCalled = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Voltar")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                backCalled
            )
        }
    }
}