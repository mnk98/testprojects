package com.example.android.architecture.blueprints.todoapp.expense.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ExpenseRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val expensesCollection = firestore.collection("expenses")

    suspend fun addExpense(expense: Expense): Result<Unit> = runCatching {
        val documentRef = expensesCollection.document()
        val expenseWithId = expense.copy(id = documentRef.id)
        documentRef.set(expenseWithId).await()
    }
}
