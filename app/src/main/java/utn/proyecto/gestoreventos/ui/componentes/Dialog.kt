package utn.proyecto.gestoreventos.ui.componentes

import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.ui.viewmodel.EventoUiState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun NuevoEventoDialog(
    eventoUiState: EventoUiState,
    onEventoValueChange: (Evento) -> Unit,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val density = displayMetrics.density
    val maxWidth = (displayMetrics.widthPixels / density).toInt()

    val dialogWidth = (maxWidth * 0.95f).coerceIn(0f, 600f)
    if (showDialog) {
        Dialog(
            onDismissRequest = {
                onDismiss()
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
        ) {
            Surface(
                modifier = Modifier
                    .widthIn(min = 0.dp, max = dialogWidth.dp)
                    .heightIn(min = 0.dp, max = 500.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    NuevoEventoForm(eventoUiState.evento, onEventoValueChange, onConfirm)
                }

            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoEventoForm(
    evento: Evento,
    onValueChange: (Evento) -> Unit = {},
    onConfirm: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }

    val context: Context = LocalContext.current
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = "Medal icon",
            modifier = Modifier
                .size(200.dp)
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop
        )

        OutlinedTextField(
            value = evento.titulo,
            onValueChange = { onValueChange(evento.copy(titulo = it)) },
            label = { Text(stringResource(R.string.titulo)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        )

        OutlinedTextField(
            value = evento.ubicacion,
            onValueChange = { onValueChange(evento.copy(ubicacion = it)) },
            label = { Text(stringResource(R.string.ubicacion)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = dimensionResource(R.dimen.padding_medium),
                    end = dimensionResource(R.dimen.padding_medium)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(R.dimen.padding_medium)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = evento.fecha,
                onValueChange = { onValueChange(evento.copy(fecha = it)) },
                label = { Text(stringResource(R.string.fecha)) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            dateDialogState.show()
                        },
                        modifier = Modifier.clip(MaterialTheme.shapes.small)
                    ) {
                        Icon(
                            painterResource(R.drawable.icono_calendario),
                            contentDescription = "Calendar Icon",
                            modifier = Modifier.size(dimensionResource(R.dimen.icono_pequeno))
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_small)
                    )
            )
            OutlinedTextField(
                value = evento.hora,
                onValueChange = { hora = it },
                label = { Text(stringResource(R.string.hora)) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            timeDialogState.show()
                        },
                        modifier = Modifier.clip(MaterialTheme.shapes.small)
                    ) {
                        Icon(
                            painterResource(R.drawable.icono_reloj),
                            contentDescription = "Ícono Reloj",
                            modifier = Modifier.size(dimensionResource(R.dimen.icono_pequeno))
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth()
                    .padding(end = dimensionResource(R.dimen.padding_medium))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = dimensionResource(R.dimen.padding_medium)),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    onConfirm()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
            ) {
                Text(stringResource(R.string.crear_evento))
            }

        }
    }

    fecha = DatePickerDialogComponent(context = context, dateDialogState = dateDialogState, evento, onValueChange)
    hora = TimePickerDialogComponent(context = context, timeDialogState = timeDialogState, evento, onValueChange)

    onValueChange(evento.copy(fecha = fecha))
    onValueChange(evento.copy(hora = hora))
}
