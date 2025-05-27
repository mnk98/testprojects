package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.todoapp.statistics.data.MonthlyStats
import com.example.android.architecture.blueprints.todoapp.statistics.data.MonthlyStatsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyStatsViewModel @Inject constructor(
    private val repository: MonthlyStatsRepository
) : ViewModel() {

    private val _stats = MutableStateFlow<MonthlyStats?>(null)
    val stats: StateFlow<MonthlyStats?> = _stats.asStateFlow()

    init {
        loadStats()
    }

    fun loadStats() {
        viewModelScope.launch {
            try {
                _stats.value = repository.getCurrentMonthStats()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
