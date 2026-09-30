package br.com.denisecastro.cielopaylab

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import br.com.denisecastro.cielopaylab.domain.usecase.ProcessTransactionUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProcessTransactionUseCaseTest {

    private lateinit var repository: TransactionRepository
    private lateinit var useCase: ProcessTransactionUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ProcessTransactionUseCase(repository)
    }

    @Test
    fun `should process transaction when amount is valid`() = runTest {

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
            repository.processTransaction(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } returns expectedTransaction

        // Act
        val result = useCase(
            amountInCents = amountInCents,
            paymentType = paymentType
        )

        // Assert
        assertEquals(
            expectedTransaction,
            result
        )

        coVerify(exactly = 1) {
            repository.processTransaction(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        }
    }

    @Test
    fun `should throw exception when amount is zero`() = runTest {

        // Arrange
        val amountInCents = 0L
        val paymentType = PaymentType.PIX

        // Act
        var exception: IllegalArgumentException? = null

        try {
            useCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } catch (error: IllegalArgumentException) {
            exception = error
        }

        // Assert
        assertEquals(
            "O valor da transação deve ser maior que zero.",
            exception?.message
        )

        coVerify(exactly = 0) {
            repository.processTransaction(
                any(),
                any()
            )
        }
    }

    @Test
    fun `should throw exception when amount is negative`() = runTest {

        // Arrange
        val amountInCents = -100L
        val paymentType = PaymentType.CREDIT

        // Act
        var exception: IllegalArgumentException? = null

        try {
            useCase(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } catch (error: IllegalArgumentException) {
            exception = error
        }

        // Assert
        assertEquals(
            "O valor da transação deve ser maior que zero.",
            exception?.message
        )

        coVerify(exactly = 0) {
            repository.processTransaction(
                any(),
                any()
            )
        }
    }
}