package com.example.swimtrack.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.swimtrack.data.local.TrainingEntity
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun AddTrainingScreen(
    onSaveTraining: (TrainingEntity) -> Unit,
    onBack: () -> Unit
) {

    var style by remember { mutableStateOf("") }
    var distance by remember { mutableStateOf("") }

    // Aquí guardamos solamente números.
    // Ejemplo: 11840 -> 1:18.40
    var timeDigits by remember { mutableStateOf("") }

    var date by remember { mutableStateOf("") }
    var observation by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    val fieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = Color.Black,
        unfocusedTextColor = Color.Black,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = Color.DarkGray,
        cursorColor = MaterialTheme.colorScheme.primary
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Registrar entrenamiento",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = style,
            onValueChange = {
                style = it
            },
            label = {
                Text("Estilo")
            },
            placeholder = {
                Text("Ej. Libre")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = distance,
            onValueChange = { newValue ->

                // Solo permite números.
                distance = newValue.filter { it.isDigit() }
            },
            label = {
                Text("Distancia")
            },
            suffix = {
                Text("m")
            },
            placeholder = {
                Text("Ej. 100")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = formatTime(timeDigits),
            onValueChange = { newValue ->

                /*
                 * Eliminamos automáticamente ":" y "."
                 * y conservamos únicamente números.
                 *
                 * Máximo:
                 * 999:59.99
                 */
                timeDigits = newValue
                    .filter { it.isDigit() }
                    .take(7)
            },
            label = {
                Text("Tiempo")
            },
            placeholder = {
                Text("Ej. escribe 11840 → 1:18.40")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = date,
            onValueChange = {},
            readOnly = true,
            label = {
                Text("Fecha")
            },
            placeholder = {
                Text("Seleccionar fecha")
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    focusManager.clearFocus()
                    showDatePicker = true
                },
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = observation,
            onValueChange = {
                observation = it
            },
            label = {
                Text("Observación")
            },
            placeholder = {
                Text("Opcional")
            },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 4,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val distanceNumber = distance.toIntOrNull()
                val formattedTime = formatTime(timeDigits)

                if (
                    style.isNotBlank() &&
                    distanceNumber != null &&
                    isValidTime(timeDigits) &&
                    date.isNotBlank()
                ) {

                    val training = TrainingEntity(
                        style = style.trim(),
                        distance = distanceNumber,
                        time = formattedTime,
                        date = date,
                        observation = observation.trim()
                    )

                    onSaveTraining(training)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar entrenamiento")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }

    if (showDatePicker) {

        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {

                TextButton(
                    onClick = {

                        datePickerState.selectedDateMillis?.let { millis ->

                            val selectedDate = Instant
                                .ofEpochMilli(millis)
                                .atZone(ZoneId.of("UTC"))
                                .toLocalDate()

                            val formatter = DateTimeFormatter
                                .ofPattern("dd/MM/yyyy")

                            date = selectedDate.format(formatter)
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar")
                }
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancelar")
                }
            }
        ) {

            DatePicker(
                state = datePickerState
            )
        }
    }
}

/**
 * Convierte una entrada únicamente numérica
 * en un tiempo de natación.
 *
 * Ejemplos:
 *
 * 4       -> 0.04
 * 45      -> 0.45
 * 452     -> 4.52
 * 4520    -> 45.20
 * 11840   -> 1:18.40
 * 20315   -> 2:03.15
 */
private fun formatTime(digits: String): String {

    if (digits.isEmpty()) {
        return ""
    }

    val clean = digits.filter { it.isDigit() }

    return when {

        clean.length == 1 -> {
            "0.0$clean"
        }

        clean.length == 2 -> {
            "0.$clean"
        }

        clean.length == 3 -> {
            "${clean.substring(0, 1)}.${clean.substring(1)}"
        }

        clean.length == 4 -> {
            "${clean.substring(0, 2)}.${clean.substring(2)}"
        }

        else -> {

            val hundredths = clean.takeLast(2)

            val seconds = clean
                .dropLast(2)
                .takeLast(2)

            val minutes = clean.dropLast(4)

            "$minutes:$seconds.$hundredths"
        }
    }
}

private fun isValidTime(digits: String): Boolean {

    val clean = digits.filter { it.isDigit() }

    if (clean.length < 3) {
        return false
    }

    if (clean.length > 4) {

        val seconds = clean
            .dropLast(2)
            .takeLast(2)
            .toIntOrNull()
            ?: return false

        if (seconds > 59) {
            return false
        }
    }

    return true
}