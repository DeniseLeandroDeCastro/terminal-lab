package br.com.denisecastro.cielopaylab.domain.repository

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun processTransaction(
        amount: Double,
        paymentType: PaymentType
    ): Transaction

    fun observeTransactions(): Flow<List<Transaction>>
}