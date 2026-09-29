package br.com.denisecastro.cielopaylab.di

import br.com.denisecastro.cielopaylab.data.processor.FakeTransactionProcessor
import br.com.denisecastro.cielopaylab.data.processor.TransactionProcessor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ProcessorModule {

    @Binds
    @Singleton
    abstract fun bindTransactionProcessor(
        processor: FakeTransactionProcessor
    ): TransactionProcessor
}