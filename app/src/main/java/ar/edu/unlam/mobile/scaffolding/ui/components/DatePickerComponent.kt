package ar.edu.unlam.mobile.scaffolding.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.onPrimaryDark
import com.example.compose.primaryDark
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    initialDate: String? = null,
    onDateSelected: (String) -> Unit,
) {
    val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val initialDateMillis =
        initialDate?.let { date ->
            try {
                dateFormatter.parse(date)?.time
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }
        }

    val datePickerState =
        rememberDatePickerState(
            initialSelectedDateMillis = initialDateMillis,
            selectableDates =
                object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean = utcTimeMillis <= System.currentTimeMillis()
                },
        )

    val selectedDate = datePickerState.selectedDateMillis?.let { dateFormatter.format(Date(it)) }
    var showDialog by remember { mutableStateOf(false) }

    Column {
        TextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .border(3.dp, primaryDark, RoundedCornerShape(10.dp)),
            value = selectedDate ?: initialDate ?: "",
            placeholder = { Text("Ingrese la fecha") },
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
                    tint = onPrimaryDark,
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors =
                TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                ),
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
                    val selectedDate =
                        datePickerState.selectedDateMillis?.let { millis ->
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
                        } ?: ""
                    onDateSelected(selectedDate)
                    onDismissRequest()
                },
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 3.dp),
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = primaryDark,
                        contentColor = onPrimaryDark,
                    ),
            ) {
                Text("Confirmar")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@Preview(showBackground = true)
@Composable
fun DatePickerComponentPreview() {
    DatePickerComponent(
        initialDate = "01/01/2023",
        onDateSelected = { /* No hacer nada */ },
    )
}
