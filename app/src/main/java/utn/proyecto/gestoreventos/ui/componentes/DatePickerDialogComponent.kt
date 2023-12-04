package utn.proyecto.gestoreventos.ui.componentes

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerColors
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.data.Evento
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialogComponent(
    context: Context,
    dateDialogState: MaterialDialogState,
    evento: Evento,
    onValueChange: (Evento) -> Unit = {}
): String {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val fechaSeleccionadaMsj = stringResource(id = R.string.fecha_seleccionada_ok)

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(
                text = stringResource(id = R.string.aceptar),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Toast.makeText(
                    context,
                    fechaSeleccionadaMsj,
                    Toast.LENGTH_LONG
                ).show()
            }
            negativeButton(
                text = stringResource(id = R.string.cancelar),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = stringResource(id = R.string.seleccione_fecha),
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = MaterialTheme.colorScheme.primary,
                dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                calendarHeaderTextColor = MaterialTheme.colorScheme.primary
            )
        ) {
            pickedDate = it
            onValueChange(evento.copy(fecha = pickedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))))
        }
    }
    return pickedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
}