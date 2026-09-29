package br.com.denisecastro.cielopaylab.ui.history.model

enum class TransactionSort(
    val label: String
) {
    NEWEST("Mais recentes"),
    OLDEST("Mais antigas"),
    HIGHEST_VALUE("Maior valor"),
    LOWEST_VALUE("Menor valor")
}