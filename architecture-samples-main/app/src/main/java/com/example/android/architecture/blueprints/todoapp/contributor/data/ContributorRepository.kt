package com.example.android.architecture.blueprints.todoapp.contributor.data

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ContributorRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val contributorsCollection = firestore.collection("contributors")

    suspend fun addContributor(contributor: Contributor): Result<Unit> = runCatching {
        val documentRef = contributorsCollection.document()
        val contributorWithId = contributor.copy(id = documentRef.id)
        documentRef.set(contributorWithId).await()
    }
}
