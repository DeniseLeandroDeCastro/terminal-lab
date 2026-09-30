package br.com.denisecastro.cielopaylab

import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.ui.home.viewmodel.HomeViewModel
import io.mockk.every
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Calendar

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: TransactionRepository

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should calculate approved transactions from today`() =
        runTest(testDispatcher) {

            // Arrange
            val now = System.currentTimeMillis()

            val transactionsFlow = MutableStateFlow(
                listOf(
                    Transaction(
                        id = "1",
                        amountInCents = 10000L,
                        paymentType = PaymentType.PIX,
                        status = TransactionStatus.APPROVED,
                        timestamp = now,
                        responseTimeMillis = 200L
                    ),
                    Transaction(
                        id = "2",
                        amountInCents = 5000L,
                        paymentType = PaymentType.CREDIT,
                        status = TransactionStatus.APPROVED,
                        timestamp = now,
                        responseTimeMillis = 250L
                    )
                )
            )

            every {
                repository.observeTransactions()
            } returns transactionsFlow

            val viewModel = HomeViewModel(repository)

            // Mantém uiState sendo observado
            val collectJob = backgroundScope.launch {
                viewModel.uiState.collect()
            }

            // Act
            advanceUntilIdle()

            // Assert
            assertEquals(
                15000L,
                viewModel.uiState.value.totalAmountInCents
            )

            assertEquals(
                2,
                viewModel.uiState.value.totalTransactions
            )

            assertEquals(
                2,
                viewModel.uiState.value.approvedTransactions
            )

            assertEquals(
                false,
                viewModel.uiState.value.isLoading
            )

            collectJob.cancel()
        }

    @Test
    fun `should calculate transactions by status`() =
        runTest(testDispatcher) {

            // Arrange
            val now = System.currentTimeMillis()

            val transactionsFlow = MutableStateFlow(
                listOf(
                    Transaction(
                        id = "1",
                        amountInCents = 10000L,
                        paymentType = PaymentType.PIX,
                        status = TransactionStatus.APPROVED,
                        timestamp = now,
                        responseTimeMillis = 200L
                    ),
                    Transaction(
                        id = "2",
                        amountInCents = 5000L,
                        paymentType = PaymentType.CREDIT,
                        status = TransactionStatus.CANCELLED,
                        timestamp = now,
                        responseTimeMillis = 250L
                    ),
                    Transaction(
                        id = "3",
                        amountInCents = 8000L,
                        paymentType = PaymentType.DEBIT,
                        status = TransactionStatus.DECLINED,
                        timestamp = now,
                        responseTimeMillis = 300L
                    )
                )
            )

            every {
                repository.observeTransactions()
            } returns transactionsFlow

            val viewModel = HomeViewModel(repository)

            val collectJob = backgroundScope.launch {
                viewModel.uiState.collect()
            }

            // Act
            advanceUntilIdle()

            // Assert
            assertEquals(
                10000L,
                viewModel.uiState.value.totalAmountInCents
            )

            assertEquals(
                1,
                viewModel.uiState.value.totalTransactions
            )

            assertEquals(
                1,
                viewModel.uiState.value.approvedTransactions
            )

            assertEquals(
                1,
                viewModel.uiState.value.cancelledTransactions
            )

            assertEquals(
                3,
                viewModel.uiState.value.recentTransactions.size
            )

            collectJob.cancel()
        }

    @Test
    fun `should ignore old transactions in today summary`() =
        runTest(testDispatcher) {

            // Arrange
            val now = System.currentTimeMillis()

            val yesterday = Calendar.getInstance().apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }.timeInMillis

            val transactionsFlow = MutableStateFlow(
                listOf(
                    // Venda de hoje
                    Transaction(
                        id = "1",
                        amountInCents = 10000L,
                        paymentType = PaymentType.PIX,
                        status = TransactionStatus.APPROVED,
                        timestamp = now,
                        responseTimeMillis = 200L
                    ),

                    // Venda de ontem
                    Transaction(
                        id = "2",
                        amountInCents = 50000L,
                        paymentType = PaymentType.CREDIT,
                        status = TransactionStatus.APPROVED,
                        timestamp = yesterday,
                        responseTimeMillis = 250L
                    )
                )
            )

            every {
                repository.observeTransactions()
            } returns transactionsFlow

            val viewModel = HomeViewModel(repository)

            val collectJob = backgroundScope.launch {
                viewModel.uiState.collect()
            }

            // Act
            advanceUntilIdle()

            // Assert
            assertEquals(
                10000L,
                viewModel.uiState.value.totalAmountInCents
            )

            assertEquals(
                1,
                viewModel.uiState.value.totalTransactions
            )

            assertEquals(
                1,
                viewModel.uiState.value.approvedTransactions
            )

            collectJob.cancel()
        }

    @Test
    fun `should return only three most recent transactions`() =
        runTest(testDispatcher) {

            // Arrange
            val now = System.currentTimeMillis()

            val transaction1 = Transaction(
                id = "1",
                amountInCents = 1000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = now - 5000L,
                responseTimeMillis = 100L
            )

            val transaction2 = Transaction(
                id = "2",
                amountInCents = 2000L,
                paymentType = PaymentType.CREDIT,
                status = TransactionStatus.APPROVED,
                timestamp = now - 1000L,
                responseTimeMillis = 100L
            )

            val transaction3 = Transaction(
                id = "3",
                amountInCents = 3000L,
                paymentType = PaymentType.DEBIT,
                status = TransactionStatus.CANCELLED,
                timestamp = now - 3000L,
                responseTimeMillis = 100L
            )

            val transaction4 = Transaction(
                id = "4",
                amountInCents = 4000L,
                paymentType = PaymentType.PIX,
                status = TransactionStatus.APPROVED,
                timestamp = now,
                responseTimeMillis = 100L
            )

            val transaction5 = Transaction(
                id = "5",
                amountInCents = 5000L,
                paymentType = PaymentType.CREDIT,
                status = TransactionStatus.DECLINED,
                timestamp = now - 2000L,
                responseTimeMillis = 100L
            )

            val transactionsFlow = MutableStateFlow(
                listOf(
                    transaction1,
                    transaction4,
                    transaction3,
                    transaction5,
                    transaction2
                )
            )

            every {
                repository.observeTransactions()
            } returns transactionsFlow

            val viewModel = HomeViewModel(repository)

            val collectJob = backgroundScope.launch {
                viewModel.uiState.collect()
            }

            // Act
            advanceUntilIdle()

            // Assert
            val recentTransactions =
                viewModel.uiState.value.recentTransactions

            assertEquals(
                3,
                recentTransactions.size
            )

            assertEquals(
                "4",
                recentTransactions[0].id
            )

            assertEquals(
                "2",
                recentTransactions[1].id
            )

            assertEquals(
                "5",
                recentTransactions[2].id
            )

            collectJob.cancel()
        }

    @Test
    fun `should show empty state when there are no transactions`() =
        runTest(testDispatcher) {

            // Arrange
            val transactionsFlow = MutableStateFlow(
                emptyList<Transaction>()
            )

            every {
                repository.observeTransactions()
            } returns transactionsFlow

            val viewModel = HomeViewModel(repository)

            val collectJob = backgroundScope.launch {
                viewModel.uiState.collect()
            }

            // Act
            advanceUntilIdle()

            // Assert
            assertEquals(
                0L,
                viewModel.uiState.value.totalAmountInCents
            )

            assertEquals(
                0,
                viewModel.uiState.value.totalTransactions
            )

            assertEquals(
                0,
                viewModel.uiState.value.approvedTransactions
            )

            assertEquals(
                0,
                viewModel.uiState.value.cancelledTransactions
            )

            assertEquals(
                0,
                viewModel.uiState.value.recentTransactions.size
            )

            assertEquals(
                false,
                viewModel.uiState.value.isLoading
            )

            collectJob.cancel()
        }
}