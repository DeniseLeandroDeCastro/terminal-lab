package br.com.denisecastro.cielopaylab.data.remote

data class TransactionRequestDto(
    val amountInCents: Long,
    val paymentType: String
)
