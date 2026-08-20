package com.example.swimtrack.repository

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class LocationRepository(
    context: Context
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(): Pair<Double, Double>? {

        return suspendCancellableCoroutine { continuation ->

            val cancellationTokenSource =
                CancellationTokenSource()

            fusedLocationClient
                .getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    cancellationTokenSource.token
                )
                .addOnSuccessListener { location ->

                    if (location != null) {

                        if (continuation.isActive) {
                            continuation.resume(
                                Pair(
                                    location.latitude,
                                    location.longitude
                                )
                            )
                        }

                    } else {

                        /*
                         * Si por alguna razón no puede obtener
                         * una ubicación nueva, intentamos usar
                         * la última ubicación conocida.
                         */

                        fusedLocationClient
                            .lastLocation
                            .addOnSuccessListener { lastLocation ->

                                if (continuation.isActive) {

                                    if (lastLocation != null) {

                                        continuation.resume(
                                            Pair(
                                                lastLocation.latitude,
                                                lastLocation.longitude
                                            )
                                        )

                                    } else {

                                        continuation.resume(null)
                                    }
                                }
                            }
                            .addOnFailureListener {

                                if (continuation.isActive) {
                                    continuation.resume(null)
                                }
                            }
                    }
                }
                .addOnFailureListener {

                    if (continuation.isActive) {
                        continuation.resume(null)
                    }
                }

            continuation.invokeOnCancellation {
                cancellationTokenSource.cancel()
            }
        }
    }
}