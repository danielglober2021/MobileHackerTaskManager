package com.tuapp.data.repository

import com.example.mobilehackertaskmanager.data.ERROR_ADDING_NEW_TASK
import com.example.mobilehackertaskmanager.data.TASKS_NAME
import com.example.mobilehackertaskmanager.data.firebase.utils.safeTo
import com.example.mobilehackertaskmanager.data.firebase.utils.toObjectsList
import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class TaskRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val logger: Logger,
): Logger by logger {

    private val taskCollection = firestore.collection(TASKS_NAME)

    open fun getTasks(): Flow<List<Task>> = callbackFlow {
        val listenerRegistration: ListenerRegistration = taskCollection
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                 snapshot?.toObjectsList<Task>()?.let { tasks ->
                    trySend(tasks).isSuccess
                } ?: trySend(emptyList())

            }

        awaitClose { listenerRegistration.remove() }
    }

    open suspend fun getTaskById(id: String): Task? = try {
        val snapshot = taskCollection.document(id).get().await()
        if (snapshot.exists()) {
            snapshot.safeTo<Task>()
        } else {
            null
        }
    } catch (e: Exception) {
        null
    }

    open suspend fun addTask(task: Task) {
        try {
            val newDoc = taskCollection.document()
            val taskWithId = task.copy(id = newDoc.id)
            newDoc.set(taskWithId).await()
        } catch (e: Exception) {
            log(ERROR_ADDING_NEW_TASK)
        }
    }

    open suspend fun deleteTask(id: String) {
        taskCollection.document(id).delete().await()
    }

    open suspend fun updateTask(task: Task) {
        task.id.let {
            taskCollection.document(it).set(task).await()
        }
    }
}
