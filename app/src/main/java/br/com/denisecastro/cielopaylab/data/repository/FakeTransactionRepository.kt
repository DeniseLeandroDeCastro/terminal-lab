package br.com.denisecastro.cielopaylab.data.repository


import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FakeTransactionRepository @Inject constructor() :
    TransactionRepository {

    private val transactions = MutableStateFlow<List<Transaction>>(emptyList())

    override suspend fun processTransaction(
        amountInCents: Long,
        paymentType: PaymentType
    ): Transaction {
        val startTime = System.currentTimeMillis()
        delay(800)

        val status =
            if (Random.nextInt(100) < 90) {
                TransactionStatus.APPROVED
            } else {
                TransactionStatus.DECLINED
            }

        val transaction =
            Transaction(
                id = UUID.randomUUID().toString(),
                amountInCents = amountInCents,
                paymentType = paymentType,
                status = status,
                timestamp =
                    System.currentTimeMillis(),
                responseTimeMillis =
                    System.currentTimeMillis() -
                            startTime
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

    override suspend fun cancelTransaction(
        id: String
    ): Transaction? {
        delay(800)

        val transaction =
            transactions.value.find { transaction ->
                transaction.id == id
            } ?: return null

        if (transaction.status != TransactionStatus.APPROVED) {
            return null
        }

        val cancelledTransaction =
            transaction.copy(
                status = TransactionStatus.CANCELLED
            )

        transactions.value =
            transactions.value.map { currentTransaction ->
                if (currentTransaction.id == id) {
                    cancelledTransaction
                } else {
                    currentTransaction
                }
            }

        return cancelledTransaction
    }
}