package br.com.denisecastro.cielopaylab.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class CieloPayDatabase :
    RoomDatabase() {

    abstract fun transactionDao():
            TransactionDao
}