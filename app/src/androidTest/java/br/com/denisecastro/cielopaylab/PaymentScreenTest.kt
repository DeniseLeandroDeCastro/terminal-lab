package br.com.denisecastro.cielopaylab

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import br.com.denisecastro.cielopaylab.ui.payment.screen.PaymentScreen
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import br.com.denisecastro.cielopaylab.ui.theme.CieloPayLabTheme
import org.junit.Rule
import org.junit.Test
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.performTextInput
import androidx.compose.runtime.remember
import androidx.compose.ui.test.hasSetTextAction
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import org.junit.Assert.assertEquals
import androidx.compose.ui.test.assertCountEquals
import br.com.denisecastro.cielopaylab.core.util.CurrencyUtils
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithTag

class PaymentScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun shouldDisplayInitialPaymentScreen() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Forma de pagamento")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Processar venda")
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun shouldEnableProcessButtonWhenAmountIsEntered() {

        composeTestRule.setContent {

            var state by remember {
                mutableStateOf(
                    PaymentUiState()
                )
            }

            CieloPayLabTheme {
                PaymentScreen(
                    state = state,
                    onAmountChanged = { amount ->
                        state = state.copy(
                            amount = amount
                        )
                    },
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNode(hasSetTextAction())
            .performTextInput("1000")

        composeTestRule
            .onNodeWithText("Processar venda")
            .assertIsEnabled()
    }

    @Test
    fun shouldSelectPixPaymentType() {

        var selectedPaymentType: PaymentType? = null

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(),
                    onAmountChanged = {},
                    onPaymentTypeChanged = { paymentType ->
                        selectedPaymentType = paymentType
                    },
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Pix")
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                PaymentType.PIX,
                selectedPaymentType
            )
        }
    }

    @Test
    fun shouldCallProcessPaymentWhenButtonIsClicked() {

        var processPaymentCalled = false

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "1000",
                        paymentType = PaymentType.PIX
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {
                        processPaymentCalled = true
                    },
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Processar venda")
            .assertIsEnabled()
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                processPaymentCalled
            )
        }
    }

    @Test
    fun shouldDisplayLoadingStateWhenProcessingPayment() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "1000",
                        paymentType = PaymentType.PIX,
                        isLoading = true
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Aguarde...")
            .assertIsDisplayed()
            .assertIsNotEnabled()
    }

    @Test
    fun shouldDisplayErrorMessage() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "1000",
                        paymentType = PaymentType.PIX,
                        isLoading = false,
                        errorMessage = "Erro ao processar a venda."
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Erro ao processar a venda.")
            .assertIsDisplayed()
    }

    @Test
    fun shouldDisplayTransactionResultWhenPaymentIsProcessed() {

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
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "15000",
                        paymentType = PaymentType.PIX,
                        isLoading = false,
                        transaction = transaction,
                        errorMessage = null
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onAllNodesWithText("Venda aprovada")
            .assertCountEquals(2)

        composeTestRule
            .onNodeWithText(formattedAmount)
            .assertIsDisplayed()

        composeTestRule
            .onAllNodesWithText("Nova venda")
            .assertCountEquals(2)
    }

    @Test
    fun shouldCallNewPaymentWhenNewPaymentButtonIsClicked() {

        var newPaymentCalled = false

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
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "15000",
                        paymentType = PaymentType.PIX,
                        transaction = transaction
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {
                        newPaymentCalled = true
                    },
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNode(
                hasText("Nova venda") and hasClickAction()
            )
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                newPaymentCalled
            )
        }
    }

    @Test
    fun shouldCallOnBackWhenBackButtonIsClicked() {

        var backCalled = false

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {
                        backCalled = true
                    }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Voltar")
            .assertIsDisplayed()
            .performClick()

        composeTestRule.runOnIdle {
            assertEquals(
                true,
                backCalled
            )
        }
    }

    @Test
    fun shouldDisableInputsWhilePaymentIsLoading() {

        composeTestRule.setContent {
            CieloPayLabTheme {
                PaymentScreen(
                    state = PaymentUiState(
                        amount = "1000",
                        paymentType = PaymentType.PIX,
                        isLoading = true
                    ),
                    onAmountChanged = {},
                    onPaymentTypeChanged = {},
                    onProcessPayment = {},
                    onNewPayment = {},
                    onBack = {}
                )
            }
        }

        composeTestRule
            .onNodeWithTag("currency_input")
            .assertIsNotEnabled()

        composeTestRule
            .onNode(
                hasText("Pix") and hasClickAction()
            )
            .assertIsNotEnabled()
    }
}