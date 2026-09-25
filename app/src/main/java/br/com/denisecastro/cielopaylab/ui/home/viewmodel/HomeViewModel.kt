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

@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: TransactionRepository
) : ViewModel() {

    val uiState = repository
        .observeTransactions()
        .map { transactions ->

            val approvedTransactions = transactions.filter { transaction ->
                transaction.status == TransactionStatus.APPROVED
            }

            val cancelledTransactions = transactions.count { transaction ->
                transaction.status == TransactionStatus.CANCELLED
            }

            HomeUiState(
                totalAmountInCents = approvedTransactions.sumOf { transaction ->
                    transaction.amountInCents
                },
                totalTransactions = transactions.size,
                approvedTransactions = approvedTransactions.size,
                cancelledTransactions = cancelledTransactions,
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