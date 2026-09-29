package br.com.denisecastro.cielopaylab.data.processor

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction

interface TransactionProcessor {

    suspend fun process(
        amountInCents: Long,
        paymentType: PaymentType
    ): Transaction
}