package br.com.denisecastro.cielopaylab.data.repository

import br.com.denisecastro.cielopaylab.data.local.TransactionDao
import br.com.denisecastro.cielopaylab.data.local.toDomain
import br.com.denisecastro.cielopaylab.data.local.toEntity
import br.com.denisecastro.cielopaylab.data.remote.TransactionApi
import br.com.denisecastro.cielopaylab.data.remote.TransactionRequestDto
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    private val api: TransactionApi,
    private val transactionDao: TransactionDao
) : TransactionRepository {

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
            idempotencyKey = UUID.randomUUID().toString(),
            request = request
        )

        val responseTime = System.currentTimeMillis() - startTime

        val transaction = Transaction(
            id = response.id,
            amountInCents = amountInCents,
            paymentType = paymentType,
            status = TransactionStatus.valueOf(response.status),
            timestamp = System.currentTimeMillis(),
            responseTimeMillis = responseTime
        )

        transactionDao.insert(
            transaction.toEntity()
        )

        return transaction
    }

    override fun observeTransactions(): Flow<List<Transaction>> {
        return transactionDao
            .observeTransactions()
            .map { entities ->
                entities.map { entity ->
                    entity.toDomain()
                }
            }
    }

    override suspend fun getTransactionById(
        id: String
    ): Transaction? {
        return transactionDao
            .getTransactionById(id)
            ?.toDomain()
    }

    override suspend fun cancelTransaction(
        id: String
    ): Transaction? {
        val transaction = transactionDao
            .getTransactionById(id)
            ?.toDomain()
            ?: return null

        if (transaction.status != TransactionStatus.APPROVED) {
            return null
        }

        val cancelledTransaction = transaction.copy(
            status = TransactionStatus.CANCELLED
        )

        transactionDao.update(
            cancelledTransaction.toEntity()
        )
        return cancelledTransaction
    }

    override suspend fun deleteTransaction(
        id: String
    ) {
        transactionDao.deleteById(id)
    }
}