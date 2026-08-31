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
import kotlin.random.Random

class FakeTransactionRepository : TransactionRepository {

    private val transactions =
        MutableStateFlow<List<Transaction>>(emptyList())

    override suspend fun processTransaction(
        amount: Double,
        paymentType: PaymentType
    ): Transaction {

        val startTime = System.currentTimeMillis()

        delay(800)

        val status =
            if (Random.nextInt(100) < 90) {
                TransactionStatus.APROVVED
            } else {
                TransactionStatus.DECLINED
            }

        val transaction = Transaction(
            id = UUID.randomUUID().toString(),
            amount = amount,
            paymentType = paymentType,
            status = status,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis =
                System.currentTimeMillis() - startTime
        )

        transactions.value =
            listOf(transaction) + transactions.value
        return transaction
    }

    override fun observeTransactions():
            Flow<List<Transaction>> {
        return transactions.asStateFlow()
    }
}