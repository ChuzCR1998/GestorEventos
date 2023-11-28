package utn.proyecto.gestoreventos.ui.componentes

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.compose.md_theme_light_primaryContainer
import com.example.compose.md_theme_light_tertiaryContainer
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import utn.proyecto.gestoreventos.R
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePickerDialogComponent(
    context: Context,
    timeDialogState: MaterialDialogState
): String {
    var pickedTime by remember {
        mutableStateOf(LocalTime.NOON)
    }

    val horaSeleccionadaMsj = stringResource(id = R.string.hora_seleccionada_ok)

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(
                text = stringResource(id = R.string.aceptar),
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.primary
                )
            ) {
                Toast.makeText(
                    context,
                    horaSeleccionadaMsj,
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
        timepicker(
            initialTime = LocalTime.NOON,
            title = stringResource(id = R.string.seleccione_hora),
            colors = TimePickerDefaults.colors(
                headerTextColor = MaterialTheme.colorScheme.primary,
                activeBackgroundColor = MaterialTheme.colorScheme.primary,
                inactiveBackgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                selectorColor = MaterialTheme.colorScheme.primary,
                borderColor = MaterialTheme.colorScheme.primary
            )
        ) {
            pickedTime = it
        }
    }

    return pickedTime.toString()
}