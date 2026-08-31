package br.com.denisecastro.cielopaylab.domain.usecase

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import javax.inject.Inject

class ProcessTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(
        amount: Double,
        paymentType: PaymentType
    ): Transaction {

        require(amount > 0) {
            "O valor da transação deve ser maior que zero."
        }

        return repository.processTransaction(
            amount = amount,
            paymentType = paymentType
        )
    }
}