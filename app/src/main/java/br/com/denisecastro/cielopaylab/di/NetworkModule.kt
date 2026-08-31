package br.com.denisecastro.cielopaylab.di

import br.com.denisecastro.cielopaylab.data.remote.TransactionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL =
        "https://example.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                MoshiConverterFactory.create()
            )
            .build()
    }

    @Provides
    @Singleton
    fun provideTransactionApi(
        retrofit: Retrofit
    ): TransactionApi {
        return retrofit.create(TransactionApi::class.java)
    }
}