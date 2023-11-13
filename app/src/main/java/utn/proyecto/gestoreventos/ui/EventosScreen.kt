package utn.proyecto.gestoreventos.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.data.eventos

@Composable
fun EventosScreen(
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        items(eventos) {
            EventoItem(
                evento = it,
                modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun EventoIcon(
    @DrawableRes imagen: Int,
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(imagen),
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
    @StringRes titulo: Int,
    @StringRes descripcion: Int,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(titulo),
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = stringResource(descripcion),
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun EventoFecha(@StringRes fecha: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(fecha),
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun EventoItem(evento: Evento, modifier: Modifier = Modifier) {
    Card (modifier = modifier) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row {
                EventoIcon(imagen = evento.imagen)
                EventoInfo(titulo = evento.titulo, descripcion = evento.descripcion)
                Spacer(modifier = Modifier.weight(1f))
                EventoFecha(fecha = evento.fecha, modifier)
            }
        }
    }
}

@Preview
@Composable
fun EventosScreenPreview() {
    EventosScreen()
}
