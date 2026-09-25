package br.com.denisecastro.cielopaylab.ui.details.viewmodel

import br.com.denisecastro.cielopaylab.domain.model.Transaction

data class TransactionDetailsUiState(
    val transaction: Transaction? = null,
    val isLoading: Boolean = true,
    val isCancelling: Boolean = false,
    val isDeleting: Boolean = false,
    val isDeleted: Boolean = false,
    val errorMessage: String? = null
)
