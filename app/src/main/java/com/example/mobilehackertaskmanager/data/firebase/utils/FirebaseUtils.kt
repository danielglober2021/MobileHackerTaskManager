package com.example.mobilehackertaskmanager.data.firebase.utils

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

inline fun <reified T> QuerySnapshot.toObjectsList(): List<T> {
    return documents.mapNotNull { it.toObject(T::class.java) }
}

inline fun <reified T> DocumentSnapshot.safeTo(): T? = try {
    toObject(T::class.java)
} catch (e: Exception) {
    null
}
