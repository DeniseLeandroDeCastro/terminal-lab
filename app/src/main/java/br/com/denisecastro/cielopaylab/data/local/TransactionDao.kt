package br.com.denisecastro.cielopaylab.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(
        transaction: TransactionEntity
    )

    @Update
    suspend fun update(
        transaction: TransactionEntity
    )

    @Query(
        """
        SELECT * FROM transactions
        ORDER BY timestamp DESC
        """
    )
    fun observeTransactions(): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT * FROM transactions
        WHERE id = :id
        LIMIT 1
        """
    )
    suspend fun getTransactionById(
        id: String
    ): TransactionEntity?

    @Query(
        """
        DELETE FROM transactions
        WHERE id = :id
        """
    )
    suspend fun deleteById(
        id: String
    )
}