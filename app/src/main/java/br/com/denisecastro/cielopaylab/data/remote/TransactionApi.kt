package br.com.denisecastro.cielopaylab.data.remote

import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface TransactionApi {

    @POST("transactions")
    suspend fun processTransaction(
        @Header("Idempotency-Key")
        idempotencyKey: String,

        @Body
        request: TransactionRequestDto
    ): TransactionResponseDto
}