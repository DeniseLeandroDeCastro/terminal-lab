package br.com.denisecastro.cielopaylab.di

import br.com.denisecastro.cielopaylab.data.repository.TransactionRepositoryImpl
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        repository: TransactionRepositoryImpl
    ): TransactionRepository
}