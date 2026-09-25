package br.com.denisecastro.cielopaylab.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.denisecastro.cielopaylab.domain.usecase.CancelTransactionUseCase
import br.com.denisecastro.cielopaylab.domain.usecase.GetTransactionByIdUseCase
import br.com.denisecastro.cielopaylab.domain.usecase.DeleteTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TransactionDetailsViewModel @Inject constructor(
    private val getTransactionByIdUseCase: GetTransactionByIdUseCase,
    private val cancelTransactionUseCase: CancelTransactionUseCase,
    private val deleteTransactionUseCase: DeleteTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionDetailsUiState())

    val uiState: StateFlow<TransactionDetailsUiState> = _uiState.asStateFlow()

    fun loadTransaction(transactionId: String) {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )

            try {
                val transaction =
                    getTransactionByIdUseCase(transactionId)

                if (transaction != null) {
                    _uiState.value =
                        _uiState.value.copy(
                            transaction = transaction,
                            isLoading = false
                        )
                } else {
                    _uiState.value =
                        _uiState.value.copy(
                            transaction = null,
                            isLoading = false,
                            errorMessage = "Transação não encontrada."
                        )
                }
            } catch (exception: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        transaction = null,
                        isLoading = false,
                        errorMessage = exception.message
                            ?: "Erro ao carregar a transação."
                    )
            }
        }
    }

    fun cancelTransaction() {
        val transactionId = _uiState.value.transaction?.id ?: return

        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(
                    isCancelling = true,
                    errorMessage = null
                )

            try {
                val cancelledTransaction =
                    cancelTransactionUseCase(transactionId)

                if (cancelledTransaction != null) {
                    _uiState.value =
                        _uiState.value.copy(
                            transaction = cancelledTransaction,
                            isCancelling = false
                        )
                } else {
                    _uiState.value =
                        _uiState.value.copy(
                            isCancelling = false,
                            errorMessage = "Não foi possível cancelar a venda."
                        )
                }
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isCancelling = false,
                    errorMessage = exception.message
                        ?: "Erro ao cancelar a venda."
                )
            }
        }
    }

    fun deleteTransaction() {
        val transactionId = _uiState.value.transaction?.id ?: return

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isDeleting = true,
                errorMessage = null
            )

            try {
                deleteTransactionUseCase(transactionId)

                _uiState.value = _uiState.value.copy(
                    transaction = null,
                    isDeleting = false,
                    isDeleted = true
                )
            } catch (exception: Exception) {
                _uiState.value = _uiState.value.copy(
                    isDeleting = false,
                    errorMessage = exception.message
                        ?: "Erro ao excluir a transação."
                )
            }
        }
    }
}