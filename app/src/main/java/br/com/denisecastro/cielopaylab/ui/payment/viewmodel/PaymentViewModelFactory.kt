package br.com.denisecastro.cielopaylab.ui.payment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.denisecastro.cielopaylab.domain.usecase.ProcessTransactionUseCase

class PaymentViewModelFactory(
    private val useCase: ProcessTransactionUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {

        return PaymentViewModel(
            useCase
        ) as T
    }
}