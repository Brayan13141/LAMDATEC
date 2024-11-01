package com.example.lamdatec.features.pPrincipal.data.Worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

class ActualizarHoraWorker(context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        // Actualizar la hora actual
        val fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val horaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        // Guardar en Firebase
        val database = FirebaseDatabase.getInstance().reference
        database.child("LAMDATEC").child("Sensores/VAR_CONTROL").child("FECHA_ACTUAL").setValue(fechaActual).addOnSuccessListener {
            database.child("LAMDATEC").child("Sensores/VAR_CONTROL").child("HORA_ACTUAL").setValue(horaActual).addOnSuccessListener {

            }
        }



        return Result.success()
    }
}

fun ActualizarHora(context: Context) {
    val updateTimeRequest = PeriodicWorkRequestBuilder<ActualizarHoraWorker>(   30, TimeUnit.SECONDS)
        .build()

    WorkManager.getInstance(context)
        .enqueueUniquePeriodicWork(
            "ActualizarHoraWork",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            updateTimeRequest)
}