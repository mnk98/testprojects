package com.example.android.architecture.blueprints.todoapp.expense.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.expense.data.Expense
import com.example.android.architecture.blueprints.todoapp.expense.data.ExpenseRepository
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
class AddExpenseViewModel @Inject constructor(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddExpenseUiState())
    val uiState: StateFlow<AddExpenseUiState> = _uiState.asStateFlow()

    private val _events = MutableSharedFlow<AddExpenseEvent>()
    val events: SharedFlow<AddExpenseEvent> = _events.asSharedFlow()

    fun onNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(name = name)
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
            if (state.name.isBlank() || state.amount <= 0) {
                _events.emit(AddExpenseEvent.ShowError("Please fill all fields"))
                return@launch
            }

            val expense = Expense(
                name = state.name,
                amount = state.amount,
                date = Date(state.date)
            )

            expenseRepository.addExpense(expense)
                .onSuccess {
                    _events.emit(AddExpenseEvent.NavigateBack)
                }
                .onFailure { error ->
                    _events.emit(AddExpenseEvent.ShowError(error.message ?: "Error adding expense"))
                }
        }
    }
}

data class AddExpenseUiState(
    val name: String = "",
    val amount: Double = 0.0,
    val amountText: String = "",
    val date: Long = System.currentTimeMillis()
)

sealed class AddExpenseEvent {
    data class ShowError(val message: String) : AddExpenseEvent()
    data object NavigateBack : AddExpenseEvent()
}
