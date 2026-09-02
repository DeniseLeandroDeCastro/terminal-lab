package br.com.denisecastro.cielopaylab.ui.utils

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun PaymentType.toDisplayName(): String {
    return when (this) {
        PaymentType.CREDIT -> "Crédito"
        PaymentType.DEBIT -> "Débito"
        PaymentType.PIX -> "Pix"
    }
}

fun TransactionStatus.toDisplayName(): String {
    return when (this) {
        TransactionStatus.APPROVED -> "Venda aprovada"
        TransactionStatus.DECLINED -> "Venda recusada"
        TransactionStatus.ERROR -> "Erro na transação"
    }
}

fun Long.toFormattedDate(): String {
    val formatter = SimpleDateFormat(
        "dd/MM/yyyy HH:mm",
        Locale("pt", "BR")
    )

    return formatter.format(Date(this))
}