package br.com.denisecastro.cielopaylab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,
    val amountInCents: Long,
    val paymentType: String,
    val status: String,
    val timestamp: Long,
    val responseTimeMillis: Long
)