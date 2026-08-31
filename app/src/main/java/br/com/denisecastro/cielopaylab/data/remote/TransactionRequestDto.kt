package br.com.denisecastro.cielopaylab.data.remote

data class TransactionRequestDto(
    val amount: Double,
    val paymentType: String
)
