package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(onDateSelected: (String) -> Unit) {
    val datePickerState =
        rememberDatePickerState(
            selectableDates =
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis <= System.currentTimeMillis()
                }
            },
        )
    val selectedDate = datePickerState.selectedDateMillis?.let { convertMillisToDate(it) }
    var showDialog by remember { mutableStateOf(false) }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedDate ?: "",
            label = { Text("dd/mm/yyyy") },
            onValueChange = { /* No hacer nada, ya que es de solo lectura */ },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DateRange,
                    contentDescription = null,
                    modifier =
                    Modifier.clickable {
                        showDialog = true
                    },
                )
            },
        )

        if (showDialog) {
            MinimalDialog(
                datePickerState = datePickerState,
                onDismissRequest = { showDialog = false },
                onDateSelected = onDateSelected,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MinimalDialog(
    datePickerState: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected((datePickerState.selectedDateMillis?.let { convertMillisToDate(it) } ?: "") as String)
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 3.dp),
            ) {
                Text("Confirmar")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}
