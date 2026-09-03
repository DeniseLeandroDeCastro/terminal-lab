package br.com.denisecastro.cielopaylab.data.repository

import br.com.denisecastro.cielopaylab.data.remote.TransactionApi
import br.com.denisecastro.cielopaylab.data.remote.TransactionRequestDto
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi
) : TransactionRepository {

    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())

    override suspend fun processTransaction(
        amountInCents: Long,
        paymentType: PaymentType
    ): Transaction {
        val request = TransactionRequestDto(
                amountInCents = amountInCents,
                paymentType = paymentType.name
        )

        val startTime = System.currentTimeMillis()

        val response = api.processTransaction(
                idempotencyKey =
                    UUID.randomUUID().toString(),
                request = request
        )

        val responseTime = System.currentTimeMillis() - startTime

        val transaction = Transaction(
            id = response.id,
            amountInCents = amountInCents,
            paymentType = paymentType,
            status = TransactionStatus.valueOf(
                    response.status
            ),
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = responseTime
        )

        transactions.value = listOf(transaction) + transactions.value
        return transaction
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactions.asStateFlow()
    }

    override suspend fun getTransactionById(id: String): Transaction? {
        return transactions.value.find { transaction ->
            transaction.id == id
        }
    }

    override suspend fun cancelTransaction(id: String
    ): Transaction? {
        val transaction =
            transactions.value.find { transaction ->
                transaction.id == id
        } ?: return null

        if (transaction.status != TransactionStatus.APPROVED) {
            return null
        }

        val cancelledTransaction = transaction.copy(
                status = TransactionStatus.CANCELLED
        )

        transactions.value = transactions.value.map { currentTransaction ->
            if (currentTransaction.id == id) {
                cancelledTransaction
            } else {
                currentTransaction
            }
        }
        return cancelledTransaction
    }
}