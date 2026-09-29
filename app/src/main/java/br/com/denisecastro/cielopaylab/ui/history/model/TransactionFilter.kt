package br.com.denisecastro.cielopaylab.ui.history.model

enum class TransactionFilter(
    val label: String
) {
    ALL("Todas"),
    APPROVED("Aprovadas"),
    CANCELLED("Canceladas"),
    DECLINED("Recusadas")
}