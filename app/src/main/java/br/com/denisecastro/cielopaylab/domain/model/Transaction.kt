package br.com.denisecastro.cielopaylab.domain.model

data class Transaction(
    val id: String,
    val amountInCents: Long,
    val paymentType: PaymentType,
    val status: TransactionStatus,
    val timestamp: Long,
    val responseTimeMillis: Long
)
