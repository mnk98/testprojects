package com.example.android.architecture.blueprints.todoapp.statistics.data

import com.example.android.architecture.blueprints.todoapp.contributor.data.Contributor
import com.example.android.architecture.blueprints.todoapp.expense.data.Expense

data class MonthlyStats(
    val totalContributions: Double = 0.0,
    val totalExpenses: Double = 0.0,
    val balance: Double = 0.0,
    val month: String = "",
    val year: Int = 0,
    val contributors: List<Contributor>,
    val expenses: List<Expense>
)
