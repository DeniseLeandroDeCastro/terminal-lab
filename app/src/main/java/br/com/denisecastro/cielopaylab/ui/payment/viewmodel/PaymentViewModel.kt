package br.com.denisecastro.cielopaylab.ui.payment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.usecase.ProcessTransactionUseCase
import br.com.denisecastro.cielopaylab.ui.payment.state.PaymentUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val processTransactionUseCase: ProcessTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())

    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun onAmountChanged(amount: String) {
        _uiState.value = _uiState.value.copy(
            amount = amount
        )
    }

    fun onPaymentTypeChanged(
        paymentType: PaymentType
    ) {
        _uiState.value = _uiState.value.copy(
            paymentType = paymentType
        )
    }

    fun processPayment() {

        val amount = _uiState.value.amount
            .replace(",", ".")
            .toDoubleOrNull()
            ?: return

        val paymentType =
            _uiState.value.paymentType

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            try {

                val transaction = processTransactionUseCase(
                    amount = amount,
                    paymentType = paymentType
                )

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    transaction = transaction
                )

            } catch (exception: Exception) {

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = exception.message
                )
            }
        }
    }
}