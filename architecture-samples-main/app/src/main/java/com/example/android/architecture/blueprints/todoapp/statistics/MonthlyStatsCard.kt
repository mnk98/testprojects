package com.example.android.architecture.blueprints.todoapp.statistics

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.android.architecture.blueprints.todoapp.statistics.data.MonthlyStats
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.text.NumberFormat
import java.util.*

@Composable
fun MonthlyStatsCard(stats: MonthlyStats, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${stats.month} ${stats.year}",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Pie Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                val context = LocalContext.current
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        PieChart(context).apply {
                            description.isEnabled = false
                            isDrawHoleEnabled = true
                            setHoleColor(android.graphics.Color.TRANSPARENT)
                            legend.isEnabled = true
                            setEntryLabelColor(android.graphics.Color.WHITE)
                            setEntryLabelTextSize(12f)
                        }
                    },
                    update = { chart ->
                        val entries = listOf(
                            PieEntry(stats.totalContributions.toFloat(), "Contributions"),
                            PieEntry(stats.totalExpenses.toFloat(), "Expenses")
                        )

                        val dataSet = PieDataSet(entries, "").apply {
                            colors = listOf(
                                Color.rgb(76, 175, 80), // Green for contributions
                                Color.rgb(244, 67, 54)  // Red for expenses
                            )
                            valueTextSize = 14f
                            valueTextColor = Color.WHITE
                        }

                        chart.data = PieData(dataSet)
                        chart.invalidate()
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Summary
            val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale.getDefault()) }
            
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Total Contributions: ${currencyFormat.format(stats.totalContributions)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Total Expenses: ${currencyFormat.format(stats.totalExpenses)}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = "Balance: ${currencyFormat.format(stats.balance)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = if (stats.balance >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
