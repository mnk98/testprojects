package com.example.android.architecture.blueprints.todoapp.contributor.data

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Contributor(
    val id: String = "",
    val name: String = "",
    val address: String = "",
    @ServerTimestamp
    val date: Date? = null,
    val amount: Double = 0.0
)
