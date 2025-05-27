package com.example.android.architecture.blueprints.todoapp.expense.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Expense(
    val id: String = "",
    val name: String = "",
    @ServerTimestamp
    val date: Date? = null,
    val amount: Double = 0.0
)
