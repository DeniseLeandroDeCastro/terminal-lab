package br.com.denisecastro.cielopaylab.ui.home.state

data class HomeUiState(
    val totalAmountInCents: Long = 0L,
    val totalTransactions: Int = 0,
    val approvedTransactions: Int = 0,
    val cancelledTransactions: Int = 0,
    val isLoading: Boolean = false
)
