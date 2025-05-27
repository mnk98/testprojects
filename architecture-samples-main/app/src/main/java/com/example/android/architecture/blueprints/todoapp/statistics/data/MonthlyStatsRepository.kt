package com.example.android.architecture.blueprints.todoapp.statistics.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class MonthlyStatsRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    suspend fun getCurrentMonthStats(): MonthlyStats {
        val calendar = Calendar.getInstance()
        val currentMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())
        val currentYear = calendar.get(Calendar.YEAR)

        // Get start and end of current month
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val startOfMonth = calendar.time

        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.SECOND, -1)
        val endOfMonth = calendar.time

        // Get contributions for current month
        val contributions = firestore.collection("contributors")
            .whereGreaterThanOrEqualTo("date", startOfMonth)
            .whereLessThanOrEqualTo("date", endOfMonth)
            .get()
            .await()

        val totalContributions = contributions.documents.sumOf { 
            it.getDouble("amount") ?: 0.0 
        }

        // Get expenses for current month
        val expenses = firestore.collection("expenses")
            .whereGreaterThanOrEqualTo("date", startOfMonth)
            .whereLessThanOrEqualTo("date", endOfMonth)
            .get()
            .await()

        val totalExpenses = expenses.documents.sumOf { 
            it.getDouble("amount") ?: 0.0 
        }

        return MonthlyStats(
            totalContributions = totalContributions,
            totalExpenses = totalExpenses,
            balance = totalContributions - totalExpenses,
            month = currentMonth,
            year = currentYear
        )
    }
}
