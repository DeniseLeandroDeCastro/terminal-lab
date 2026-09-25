package br.com.denisecastro.cielopaylab.di

import android.content.Context
import androidx.room.Room
import br.com.denisecastro.cielopaylab.data.local.CieloPayDatabase
import br.com.denisecastro.cielopaylab.data.local.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCieloPayDatabase(
        @ApplicationContext context: Context
    ): CieloPayDatabase {
        return Room.databaseBuilder(
            context,
            CieloPayDatabase::class.java,
            "cielopay_database"
        ).build()
    }

    @Provides
    fun provideTransactionDao(
        database: CieloPayDatabase
    ): TransactionDao {
        return database.transactionDao()
    }
}