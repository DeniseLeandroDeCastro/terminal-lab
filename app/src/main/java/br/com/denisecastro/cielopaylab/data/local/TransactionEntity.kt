package br.com.denisecastro.cielopaylab.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey
    val id: String,

    val amount: Double,

    val paymentType: String,

    val status: String,

    val timestamp: Long,

    val responseTimeMillis: Long
)
