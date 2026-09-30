package br.com.denisecastro.cielopaylab.ui.home.state

import br.com.denisecastro.cielopaylab.domain.model.Transaction

data class HomeUiState(
    val totalAmountInCents: Long = 0L,
    val totalTransactions: Int = 0,
    val approvedTransactions: Int = 0,
    val cancelledTransactions: Int = 0,
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false
)
