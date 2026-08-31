package br.com.denisecastro.cielopaylab.ui.payment.state

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction

data class PaymentUiState(
    val amount: String = "",
    val paymentType: PaymentType = PaymentType.CREDIT,
    val isLoading: Boolean = false,
    val transaction: Transaction? = null,
    val errorMessage: String? = null
)