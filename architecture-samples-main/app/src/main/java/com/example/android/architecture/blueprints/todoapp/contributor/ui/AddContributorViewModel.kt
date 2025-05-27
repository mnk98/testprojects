package com.example.android.architecture.blueprints.todoapp.contributor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.contributor.data.Contributor
import com.example.android.architecture.blueprints.todoapp.contributor.data.ContributorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AddContributorViewModel @Inject constructor(
    private val contributorRepository: ContributorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddContributorUiState())
    val uiState: StateFlow<AddContributorUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddContributorEvent>()
    val events: SharedFlow<AddContributorEvent> = _events.asSharedFlow()

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
    }

    fun onAddressChanged(address: String) {
        _uiState.value = _uiState.value.copy(address = address)
    }

    fun onAmountChanged(amount: String) {
        val amountDouble = amount.toDoubleOrNull() ?: 0.0
        _uiState.value = _uiState.value.copy(amount = amountDouble, amountText = amount)
    }

    fun onDateChanged(timestamp: Long) {
        _uiState.value = _uiState.value.copy(date = timestamp)
    }

    fun onSaveClick() {
        viewModelScope.launch {
            val state = _uiState.value
            if (state.name.isBlank() || state.address.isBlank() || state.amount <= 0) {
                _events.emit(AddContributorEvent.ShowError("Please fill all fields"))
                return@launch
            }

            val contributor = Contributor(
                name = state.name,
                address = state.address,
                amount = state.amount,
                date = Date(state.date)
            )

            contributorRepository.addContributor(contributor)
                .onSuccess {
                    _events.emit(AddContributorEvent.NavigateBack)
                }
                .onFailure { error ->
                    _events.emit(AddContributorEvent.ShowError(error.message ?: "Error adding contributor"))
                }
        }
    }
}

data class AddContributorUiState(
    val name: String = "",
    val address: String = "",
    val amount: Double = 0.0,
    val amountText: String = "",
    val date: Long = System.currentTimeMillis()
)

sealed class AddContributorEvent {
    data class ShowError(val message: String) : AddContributorEvent()
    data object NavigateBack : AddContributorEvent()
}
