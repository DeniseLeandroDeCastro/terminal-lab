package br.com.denisecastro.cielopaylab.domain.usecase

import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        transactionId: String
    ) {
        repository.deleteTransaction(transactionId)
    }
}