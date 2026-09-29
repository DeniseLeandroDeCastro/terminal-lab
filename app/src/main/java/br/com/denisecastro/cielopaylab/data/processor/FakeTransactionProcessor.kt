package br.com.denisecastro.cielopaylab.data.processor

import br.com.denisecastro.cielopaylab.domain.model.PaymentType
import br.com.denisecastro.cielopaylab.domain.model.Transaction
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class FakeTransactionProcessor @Inject constructor() :
    TransactionProcessor {

    override suspend fun process(
        amountInCents: Long,
        paymentType: PaymentType
    ): Transaction {

        val startTime = System.currentTimeMillis()

        delay(800)

        val status = if (Random.nextInt(100) < 90) {
            TransactionStatus.APPROVED
        } else {
            TransactionStatus.DECLINED
        }

        return Transaction(
            id = UUID.randomUUID().toString(),
            amountInCents = amountInCents,
            paymentType = paymentType,
            status = status,
            timestamp = System.currentTimeMillis(),
            responseTimeMillis =
                System.currentTimeMillis() - startTime
        )
    }
}