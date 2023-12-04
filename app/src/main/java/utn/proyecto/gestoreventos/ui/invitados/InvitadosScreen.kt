package utn.proyecto.gestoreventos.ui.invitados

import utn.proyecto.gestoreventos.data.Invitado
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import utn.proyecto.gestoreventos.GestorEventosTopAppBar
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.ui.AppViewModelProvider
import utn.proyecto.gestoreventos.ui.navigation.NavigationDestination

object InvitadosDestination : NavigationDestination {
    override val route = "invitados"
    override val titleRes = R.string.invitados
    const val eventoIdArg = "eventoId"
    val routeWithArgs = "$route/{$eventoIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitadosScreen(
    onNextButtonClicked: (Int) -> Unit = {},
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InvitadosViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val invitadosUIState by viewModel.invitadosUIState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GestorEventosTopAppBar(
                title = "Invitados",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            NuevoInvitadoButton(
                onClick = onNextButtonClicked,
                eventoId = invitadosUIState.eventoId
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
            it ->
        LazyColumn(contentPadding = it) {
            items(invitadosUIState.invitadosList) {
                InvitadoItem(
                    invitado = it,
                    onClick = { },
                    modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
                )
            }
        }

        /*NuevoEventoDialog(
            showDialog = viewModel.showDialog,
            onDismiss = viewModel::onDialogDismiss,
            onConfirm = viewModel::onDialogConfirm
        )*/
    }

}

@Composable
fun InvitadoIcon(
    genero: String,
    modifier: Modifier = Modifier
){
    var imagen: Painter = painterResource(R.drawable.icono_mujer)

    if (genero == stringResource(R.string.masculino)) {
        imagen = painterResource(R.drawable.icono_hombre)
    }
    Image(
        painter = imagen,
        contentDescription = null,
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@Composable
fun InvitadoInfo(
    nombre: String,
    email: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = nombre,
            style = MaterialTheme.typography.titleSmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
        Text(
            text = email,
            style = MaterialTheme.typography.bodySmall,
            modifier = modifier.padding(dimensionResource(R.dimen.padding_small))
        )
    }
}

@Composable
fun InvitadoNotificadoIcon(notificado: Boolean, modifier: Modifier = Modifier) {
    Image(
        painter = if (notificado) painterResource(R.drawable.icono_mensaje_enviado)
                    else painterResource(R.drawable.icono_mensaje_sin_enviar),
        contentDescription = null,
        modifier = modifier
            .size(50.dp)
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitadoItem(invitado: Invitado, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
    Card (modifier = modifier, onClick = { onClick(invitado.eventoId) }) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Row {
                InvitadoIcon(genero = invitado.genero)
                InvitadoInfo(nombre = invitado.nombre, email = invitado.email)
                Spacer(modifier = Modifier.weight(1f))
                InvitadoNotificadoIcon(invitado.notificado)
            }
        }
    }
}

@Composable
fun NuevoInvitadoButton(
    onClick: (Int) -> Unit,
    eventoId: Int
) {
    FloatingActionButton(
        onClick = { onClick(eventoId) },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.nuevo_invitado)
        )
    }
}

@Preview
@Composable
fun InvitadosScreenPreview() {
    AppTheme {
        //InvitadosScreen()
    }
}
