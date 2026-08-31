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

    private val transactions =
        MutableStateFlow<List<Transaction>>(emptyList())

    override suspend fun processTransaction(
        amount: Double,
        paymentType: PaymentType
    ): Transaction {

        val request = TransactionRequestDto(
            amount = amount,
            paymentType = paymentType.name
        )

        val response = api.processTransaction(
            idempotencyKey = UUID.randomUUID().toString(),
            request = request
        )

        val transaction = Transaction(
            id = response.id,
            amount = amount,
            paymentType = paymentType,
            status = TransactionStatus.valueOf(
                response.status
            ),
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = 0L
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