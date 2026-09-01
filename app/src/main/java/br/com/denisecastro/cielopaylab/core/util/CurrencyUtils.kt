package br.com.denisecastro.cielopaylab.core.util

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    private val brazilianLocale = Locale.forLanguageTag("pt-BR")

    private val currencyFormatter =
        NumberFormat.getCurrencyInstance(
            brazilianLocale
        )

    fun formatFromCents(
        amountInCents: Long
    ): String {
        val amount = amountInCents / 100.0
        return currencyFormatter.format(amount)
    }

    fun digitsToCents(
        value: String
    ): Long {
        val digits =
            value.filter {
                it.isDigit()
            }
        return digits.toLongOrNull()
            ?: 0L
    }
}