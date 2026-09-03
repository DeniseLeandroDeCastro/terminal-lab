package br.com.denisecastro.cielopaylab.ui.details.viewmodel

import br.com.denisecastro.cielopaylab.domain.model.Transaction

data class TransactionDetailsUiState(
    val transaction: Transaction? = null,
    val isCancelling: Boolean = false,
    val errorMessage: String? = null
)
