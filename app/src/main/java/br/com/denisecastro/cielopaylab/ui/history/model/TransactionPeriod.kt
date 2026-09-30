package br.com.denisecastro.cielopaylab.ui.history.model

enum class TransactionPeriod(
    val label: String
) {
    ALL("Todos"),
    TODAY("Hoje"),
    LAST_7_DAYS("Últimos 7 dias"),
    LAST_30_DAYS("Últimos 30 dias")
}