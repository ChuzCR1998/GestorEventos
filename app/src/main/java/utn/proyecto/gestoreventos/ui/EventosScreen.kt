package utn.proyecto.gestoreventos.ui

import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import kotlinx.coroutines.launch
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.ui.componentes.NuevoEventoDialog
import utn.proyecto.gestoreventos.ui.viewmodel.EventosViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventosScreen(
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: EventosViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val eventosUIState by viewModel.eventosUIState.collectAsState()
    Scaffold (
        floatingActionButton = {
            NuevoEventoButton(onClick = viewModel::openDialog)
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        it ->
        LazyColumn(contentPadding = it) {
            items(eventosUIState.eventosList) {
                EventoItem(
                    evento = it,
                    onClick = onNextButtonClicked,
                    modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }

        NuevoEventoDialog(
            eventoUiState = viewModel.eventoUiState,
            onEventoValueChange = viewModel::updateEventoUiState,
            showDialog = viewModel.showDialog,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = {
                coroutineScope.launch {
                    viewModel.saveEvento()
                }
            }
        )
    }
    
}

@Composable
fun EventoIcon(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(R.drawable.icono_evento),
        contentDescription = null,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun EventoInfo(
    titulo: String,
    ubicacion: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = ubicacion,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun EventoFecha(fecha: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = fecha,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventoItem(evento: Evento, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card (modifier = modifier, onClick = onClick) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row {
                EventoIcon()
                EventoInfo(titulo = evento.titulo, ubicacion = evento.ubicacion)
                Spacer(modifier = Modifier.weight(1f))
                EventoFecha(fecha = evento.fecha, modifier)
            }
        }
    }
}

@Composable
fun NuevoEventoButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.nuevo_evento)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview
@Composable
fun EventosScreenPreview() {
    AppTheme {
        EventosScreen()
    }
}
