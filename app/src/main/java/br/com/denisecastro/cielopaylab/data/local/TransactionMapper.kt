package br.com.denisecastro.cielopaylab.data.local

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus

fun Transaction.toEntity() =
    TransactionEntity(
        id = id,
        amountInCents = amountInCents,
        paymentType = paymentType.name,
        status = status.name,
        timestamp = timestamp,
        responseTimeMillis = responseTimeMillis
    )

fun TransactionEntity.toDomain() =
    Transaction(
        id = id,
        amountInCents = amountInCents,
        paymentType = PaymentType.valueOf(paymentType),
        status = TransactionStatus.valueOf(status),
        timestamp = timestamp,
        responseTimeMillis = responseTimeMillis
    )