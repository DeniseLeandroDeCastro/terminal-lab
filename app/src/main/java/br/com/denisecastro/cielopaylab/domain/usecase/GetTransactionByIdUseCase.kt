package br.com.denisecastro.cielopaylab.domain.usecase

import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import javax.inject.Inject

class GetTransactionByIdUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        id: String
    ): Transaction? {
        return repository.getTransactionById(id)
    }
}