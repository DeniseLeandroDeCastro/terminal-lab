package br.com.denisecastro.cielopaylab.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.denisecastro.cielopaylab.domain.model.TransactionStatus
import br.com.denisecastro.cielopaylab.domain.repository.TransactionRepository
import br.com.denisecastro.cielopaylab.ui.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import java.util.Calendar

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: TransactionRepository
) : ViewModel() {

    val uiState = repository
        .observeTransactions()
        .map { transactions ->

            val startOfToday = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val todayTransactions = transactions.filter { transaction ->
                transaction.timestamp >= startOfToday
            }

            val approvedTransactions = todayTransactions.filter { transaction ->
                transaction.status == TransactionStatus.APPROVED
            }

            val cancelledTransactions = todayTransactions.count { transaction ->
                transaction.status == TransactionStatus.CANCELLED
            }

            HomeUiState(
                totalAmountInCents = approvedTransactions.sumOf { transaction ->
                    transaction.amountInCents
                },
                totalTransactions = approvedTransactions.size,
                approvedTransactions = approvedTransactions.size,
                cancelledTransactions = cancelledTransactions,
                recentTransactions = transactions
                    .sortedByDescending { transaction ->
                        transaction.timestamp
                    }
                    .take(3),
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState(
                isLoading = true
            )
        )
}