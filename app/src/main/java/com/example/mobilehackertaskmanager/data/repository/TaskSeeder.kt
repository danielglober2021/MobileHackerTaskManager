package com.example.mobilehackertaskmanager.data.repository

import com.example.mobilehackertaskmanager.data.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TaskSeeder {
    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("tasks")

    fun seedInitialTasks() {
        val taskList = listOf(
            Task(
                id = "task_01",
                title = "Aprender fundamentos de redes móviles y protocolos (GSM, LTE, 5G)",
                description = "Comprender la arquitectura básica de las redes móviles y sus vulnerabilidades."
            ),
            Task(
                id = "task_02",
                title = "Estudiar sistemas operativos móviles (Android, iOS)",
                description = "Analizar cómo funciona la seguridad y estructura interna de Android y iOS."
            ),
            Task(
                id = "task_03",
                title = "Dominar herramientas de pentesting móviles",
                description = "Familiarizarse con MobSF, Frida, Burp Suite, ADB y otras herramientas comunes."
            ),
            Task(
                id = "task_04",
                title = "Aprender programación para móviles",
                description = "Dominar Kotlin o Java para Android, y Swift para iOS."
            ),
            Task(
                id = "task_05",
                title = "Comprender criptografía y seguridad en apps móviles",
                description = "Estudiar HTTPS, certificados, encriptación de datos y almacenamiento seguro."
            ),
            Task(
                id = "task_06",
                title = "Practicar con laboratorios de seguridad móvil y CTFs",
                description = "Usar plataformas como Hack The Box, TryHackMe o DVIA para entrenamiento práctico."
            ),
            Task(
                id = "task_07",
                title = "Estudiar vulnerabilidades comunes en apps móviles",
                description = "Aprender el OWASP Mobile Top 10 y cómo se explotan y mitigan."
            ),
            Task(
                id = "task_08",
                title = "Realizar análisis estático y dinámico de aplicaciones",
                description = "Revisar el código, descompilar APKs y observar el comportamiento en ejecución."
            ),
            Task(
                id = "task_09",
                title = "Aplicar ingeniería social ética en entorno móvil",
                description = "Comprender cómo funciona el phishing en móvil y cómo se detecta."
            ),
            Task(
                id = "task_10",
                title = "Obtener certificaciones en seguridad móvil",
                description = "Prepararse para certificaciones como OSCP Mobile o GIAC GMOB."
            )
        )

        CoroutineScope(Dispatchers.IO).launch {
            taskList.forEach { task ->
                tasksCollection
                    .document(task.id)
                    .set(task)
                    .addOnSuccessListener { println("Tarea '${task.id}' añadida con éxito.") }
                    .addOnFailureListener { e -> println("Error añadiendo '${task.id}': ${e.message}") }
            }
        }
    }

}