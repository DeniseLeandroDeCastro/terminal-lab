package br.com.denisecastro.cielopaylab

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import org.junit.Test
import br.com.denisecastro.cielopaylab.domain.usecase.ProcessTransactionUseCase
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import br.com.denisecastro.cielopaylab.ui.payment.viewmodel.PaymentViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
class PaymentViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var processTransactionUseCase: ProcessTransactionUseCase
    private lateinit var viewModel: PaymentViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        processTransactionUseCase = mockk()

        viewModel = PaymentViewModel(
            processTransactionUseCase = processTransactionUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should keep only digits when amount changes`() {

        // Arrange
        val amount = "R$ 15,90"

        // Act
        viewModel.onAmountChanged(amount)

        // Assert
        assertEquals(
            "1590",
            viewModel.uiState.value.amount
        )
    }

    @Test
    fun `should change payment type`() {

        // Arrange
        val paymentType = PaymentType.DEBIT

        // Act
        viewModel.onPaymentTypeChanged(paymentType)

        // Assert
        assertEquals(
            PaymentType.DEBIT,
            viewModel.uiState.value.paymentType
        )
    }

    @Test
    fun `should reset state when starting new payment`() {

        // Arrange
        viewModel.onAmountChanged("15000")
        viewModel.onPaymentTypeChanged(PaymentType.DEBIT)

        // Act
        viewModel.newPayment()

        // Assert
        assertEquals(
            PaymentUiState(),
            viewModel.uiState.value
        )
    }

    @Test
    fun `should show error when amount is invalid`() {

        // Act
        viewModel.processPayment()

        // Assert
        assertEquals(
            "Informe um valor maior que zero.",
            viewModel.uiState.value.errorMessage
        )

        coVerify(exactly = 0) {
            processTransactionUseCase(
                any(),
                any()
            )
        }
    }

    @Test
    fun `should process payment successfully`() = runTest(testDispatcher) {

        // Arrange
        val amountInCents = 15000L
        val paymentType = PaymentType.PIX

        val expectedTransaction = Transaction(
            id = "123",
            amountInCents = amountInCents,
            paymentType = paymentType,
            status = TransactionStatus.APPROVED,
            timestamp = 1000L,
            responseTimeMillis = 250L
        )

        coEvery {
            processTransactionUseCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } returns expectedTransaction

        viewModel.onAmountChanged("15000")
        viewModel.onPaymentTypeChanged(paymentType)

        // Act
        viewModel.processPayment()

        advanceUntilIdle()

        // Assert
        assertEquals(
            false,
            viewModel.uiState.value.isLoading
        )

        assertEquals(
            expectedTransaction,
            viewModel.uiState.value.transaction
        )

        assertEquals(
            null,
            viewModel.uiState.value.errorMessage
        )

        coVerify(exactly = 1) {
            processTransactionUseCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        }
    }

    @Test
    fun `should show error when processing payment fails`() = runTest(testDispatcher) {

        // Arrange
        val amountInCents = 15000L
        val paymentType = PaymentType.CREDIT
        val errorMessage = "Erro ao processar transação."

        coEvery {
            processTransactionUseCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } throws RuntimeException(errorMessage)

        viewModel.onAmountChanged("15000")
        viewModel.onPaymentTypeChanged(paymentType)

        // Act
        viewModel.processPayment()

        advanceUntilIdle()

        // Assert
        assertEquals(
            false,
            viewModel.uiState.value.isLoading
        )

        assertEquals(
            errorMessage,
            viewModel.uiState.value.errorMessage
        )

        assertEquals(
            null,
            viewModel.uiState.value.transaction
        )

        coVerify(exactly = 1) {
            processTransactionUseCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        }
    }
}