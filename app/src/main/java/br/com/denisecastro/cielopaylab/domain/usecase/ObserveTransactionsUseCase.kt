package br.com.denisecastro.cielopaylab.domain.usecase

import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<List<Transaction>> {
        return repository.observeTransactions()
    }
}