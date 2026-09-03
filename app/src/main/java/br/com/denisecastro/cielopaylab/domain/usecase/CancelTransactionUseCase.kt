package br.com.denisecastro.cielopaylab.domain.usecase

import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import javax.inject.Inject

class CancelTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        transactionId: String
    ): Transaction? {
        return repository.cancelTransaction(transactionId)
    }
}