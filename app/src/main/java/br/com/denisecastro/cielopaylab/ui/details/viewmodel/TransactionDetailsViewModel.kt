package br.com.denisecastro.cielopaylab.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.usecase.CancelTransactionUseCase
import br.com.denisecastro.cielopaylab.domain.usecase.GetTransactionByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val cancelTransactionUseCase: CancelTransactionUseCase
) : ViewModel() {

    private val _transaction = MutableStateFlow<Transaction?>(null)

    val transaction: StateFlow<Transaction?> = _transaction.asStateFlow()

    fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            _transaction.value =
                getTransactionByIdUseCase(transactionId)
        }
    }

    fun cancelTransaction() { val transactionId = _transaction.value?.id ?: return
        viewModelScope.launch {
            val cancelledTransaction =
                cancelTransactionUseCase(transactionId)

            if (cancelledTransaction != null) {
                _transaction.value =
                    cancelledTransaction
            }
        }
    }
}