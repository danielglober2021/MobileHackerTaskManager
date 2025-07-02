package com.example.mobilehackertaskmanager.data.repository

import com.example.mobilehackertaskmanager.data.model.Task
import com.example.mobilehackertaskmanager.utils.logger.Logger
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.tuapp.data.repository.TaskRepository
import junit.framework.Assert.assertNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TaskRepositoryTest {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var collection: CollectionReference
    private lateinit var document: DocumentReference
    private lateinit var logger: Logger
    private lateinit var repository: TaskRepository

    @Before
    fun setup() {
        firestore = mock()
        collection = mock()
        document = mock()
        logger = mock()

        whenever(firestore.collection(any())).thenReturn(collection)
        whenever(collection.document(any())).thenReturn(document)
        // whenever(collection.document("abc123")).thenReturn(document)
        whenever(document.set(any())).thenReturn(Tasks.forResult(null))
        whenever(document.delete()).thenReturn(Tasks.forResult(null))

        repository = TaskRepository(firestore, logger)
    }

    @Test
    fun `getTaskById returns null when document does not exist`() = runTest {
        val mockSnapshot: DocumentSnapshot = mock()
        val mockTask = Tasks.forResult(mockSnapshot)

        whenever(document.get()).thenReturn(mockTask)
        whenever(mockSnapshot.exists()).thenReturn(false)

        val result = repository.getTaskById("123")

        assertNull(result)
    }

    @Test
    fun `getTaskById returns task when document exists`() = runTest {
        val mockSnapshot: DocumentSnapshot = mock()
        val task = Task("123", "Title", "Desc", false)

        whenever(collection.document("123")).thenReturn(document)
        whenever(document.get()).thenReturn(Tasks.forResult(mockSnapshot))
        whenever(mockSnapshot.exists()).thenReturn(true)
        whenever(mockSnapshot.toObject(Task::class.java)).thenReturn(task)

        val result = repository.getTaskById("123")

        assertEquals(task, result)
    }

    @Test
    fun `addTask adds task to Firestore`() = runTest {
        val task = Task("", "Nuevo", "Desc")
        val newDoc = mock<DocumentReference>()
        whenever(collection.document()).thenReturn(newDoc)
        whenever(newDoc.id).thenReturn("generatedId")

        repository.addTask(task)

        verify(newDoc).set(task.copy(id = "generatedId"))
    }

    @Test
    fun `deleteTask calls Firestore delete`() = runTest {
        repository.deleteTask("abc123")
        verify(collection.document("abc123")).delete()
    }

    @Test
    fun `updateTask calls Firestore set`() = runTest {
        val task = Task("id1", "Update", "Desc", completed = true)

        repository.updateTask(task)

        verify(document).set(task)
    }
}
