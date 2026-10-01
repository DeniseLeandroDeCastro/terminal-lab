package br.com.denisecastro.cielopaylab

import br.com.denisecastro.cielopaylab.data.local.TransactionDao
import br.com.denisecastro.cielopaylab.data.local.toEntity
import br.com.denisecastro.cielopaylab.data.processor.TransactionProcessor
import br.com.denisecastro.cielopaylab.data.repository.TransactionRepositoryImpl
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TransactionRepositoryImplTest {

    private lateinit var transactionProcessor: TransactionProcessor
    private lateinit var transactionDao: TransactionDao
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setUp() {
        transactionProcessor = mockk()
        transactionDao = mockk(relaxed = true)

        repository = TransactionRepositoryImpl(
            transactionProcessor = transactionProcessor,
            transactionDao = transactionDao
        )
    }

    @Test
    fun `should process and save transaction`() = runTest {

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
            transactionProcessor.process(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        } returns expectedTransaction

        // Act
        val result = repository.processTransaction(
            amountInCents = amountInCents,
            paymentType = paymentType
        )

        // Assert
        assertEquals(
            expectedTransaction,
            result
        )

        coVerify(exactly = 1) {
            transactionProcessor.process(
                amountInCents = amountInCents,
                paymentType = paymentType
            )
        }

        coVerify(exactly = 1) {
            transactionDao.insert(
                expectedTransaction.toEntity()
            )
        }
    }

    @Test
    fun `should cancel approved transaction`() = runTest {

        // Arrange
        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = 1000L,
            responseTimeMillis = 250L
        )

        coEvery {
            transactionDao.getTransactionById("123")
        } returns transaction.toEntity()

        // Act
        val result = repository.cancelTransaction("123")

        // Assert
        assertEquals(
            TransactionStatus.CANCELLED,
            result?.status
        )

        assertEquals(
            transaction.id,
            result?.id
        )

        assertEquals(
            transaction.amountInCents,
            result?.amountInCents
        )

        coVerify(exactly = 1) {
            transactionDao.getTransactionById("123")
        }

        coVerify(exactly = 1) {
            transactionDao.update(
                transaction.copy(
                    status = TransactionStatus.CANCELLED
                ).toEntity()
            )
        }
    }

    @Test
    fun `should not cancel transaction when already cancelled`() = runTest {

        // Arrange
        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.CANCELLED,
            timestamp = 1000L,
            responseTimeMillis = 250L
        )

        coEvery {
            transactionDao.getTransactionById("123")
        } returns transaction.toEntity()

        // Act
        val result = repository.cancelTransaction("123")

        // Assert
        assertEquals(
            null,
            result
        )

        coVerify(exactly = 1) {
            transactionDao.getTransactionById("123")
        }

        coVerify(exactly = 0) {
            transactionDao.update(any())
        }
    }

    @Test
    fun `should return null when cancelling transaction does not exist`() = runTest {

        // Arrange
        val transactionId = "999"

        coEvery {
            transactionDao.getTransactionById(transactionId)
        } returns null

        // Act
        val result = repository.cancelTransaction(transactionId)

        // Assert
        assertEquals(
            null,
            result
        )

        coVerify(exactly = 1) {
            transactionDao.getTransactionById(transactionId)
        }

        coVerify(exactly = 0) {
            transactionDao.update(any())
        }
    }

    @Test
    fun `should return transaction when id exists`() = runTest {

        // Arrange
        val transaction = Transaction(
            id = "123",
            amountInCents = 15000L,
            paymentType = PaymentType.PIX,
            status = TransactionStatus.APPROVED,
            timestamp = 1000L,
            responseTimeMillis = 250L
        )

        coEvery {
            transactionDao.getTransactionById("123")
        } returns transaction.toEntity()

        // Act
        val result = repository.getTransactionById("123")

        // Assert
        assertEquals(
            transaction,
            result
        )

        coVerify(exactly = 1) {
            transactionDao.getTransactionById("123")
        }
    }

    @Test
    fun `should delete transaction by id`() = runTest {

        // Arrange
        val transactionId = "123"

        // Act
        repository.deleteTransaction(transactionId)

        // Assert
        coVerify(exactly = 1) {
            transactionDao.deleteById(transactionId)
        }
    }
}