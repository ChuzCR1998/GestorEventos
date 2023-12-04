package utn.proyecto.gestoreventos.ui.invitados

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.AppTheme
import kotlinx.coroutines.launch
import utn.proyecto.gestoreventos.GestorEventosTopAppBar
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.ui.AppViewModelProvider
import utn.proyecto.gestoreventos.ui.navigation.NavigationDestination
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

object NuevoInvitadoDestination : NavigationDestination {
    override val route = "nuevo_invitado"
    override val titleRes = R.string.nuevo_invitado
    const val eventoIdArg = "eventoId"
    val routeWithArgs = "$route/{$eventoIdArg}"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoInvitadoScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InvitadosViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val context: Context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    var eventoId by remember { mutableIntStateOf(0) }

    if (eventoId == 0) {
        eventoId = viewModel.invitadoUiState.eventoId
    }

    // ID del evento asociado
    viewModel.updateInvitadoUiState(viewModel.invitadoUiState.invitado.copy(eventoId = eventoId))

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GestorEventosTopAppBar(
                title = "Agregar invitado",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        },
    ) {
        innerPadding ->
        Column (
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small))
            ) {

                Image(
                    painter = painterResource(id = R.drawable.icono_mensaje),
                    contentDescription = null,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )

                OutlinedTextField(
                    value = viewModel.invitadoUiState.invitado.nombre,
                    onValueChange = { viewModel.updateInvitadoUiState(viewModel.invitadoUiState.invitado.copy(nombre = it)) },
                    label = { Text(stringResource(R.string.nombre)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                )

                OutlinedTextField(
                    value = viewModel.invitadoUiState.invitado.email,
                    onValueChange = { viewModel.updateInvitadoUiState(viewModel.invitadoUiState.invitado.copy(email = it)) },
                    label = { Text(stringResource(R.string.correo)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                )

                OutlinedTextField(
                    value = viewModel.invitadoUiState.invitado.telefono,
                    onValueChange = { viewModel.updateInvitadoUiState(viewModel.invitadoUiState.invitado.copy(telefono = it)) },
                    label = { Text(stringResource(R.string.telefono)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                )

                val generos = listOf(
                    stringResource(R.string.masculino),
                    stringResource(R.string.femenino)
                )

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                SegmentedControl(
                    items = generos,
                    defaultSelectedItemIndex = 0
                ) {
                    viewModel.updateInvitadoUiState(viewModel.invitadoUiState.invitado.copy(genero = generos[it]))
                }

                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(R.dimen.padding_medium))
                )

                Button(
                    onClick = {
                        coroutineScope.launch {
                            val pudoGuardar: Boolean = viewModel.saveInvitado()

                            var mensaje = "Invitado agregado correctamente"

                            if (!pudoGuardar) {
                                mensaje = "Todos los campos son obligatorios"
                            } else {
                                viewModel.sendEmail(viewModel.invitadoUiState.invitado)
                                navigateBack()
                            }

                            Toast.makeText(
                                context,
                                mensaje,
                                Toast.LENGTH_LONG
                            ).show()


                            Toast.makeText(
                                context,
                                "Enviar corrreo",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium)
                        )
                        .height(48.dp)
                ) {
                    Text(stringResource(R.string.anadir_invitado))
                }

            }
        }
    }



}

@Composable
fun SegmentedControl(
    items: List<String>,
    defaultSelectedItemIndex: Int = 0,
    cornerRadius : Int = 10,
    onItemSelection: (selectedItemIndex: Int) -> Unit
) {
    val selectedIndex = remember { mutableStateOf(defaultSelectedItemIndex) }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEachIndexed { index, item ->
            OutlinedButton(
                modifier = when (index) {
                    0 -> {
                        Modifier
                            .weight(0.5f)
                            .fillMaxWidth()
                            .padding(
                                start = dimensionResource(R.dimen.padding_medium)
                            )
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedIndex.value == index) 1f else 0f)

                    } else -> {
                        Modifier
                            .weight(0.5f)
                            .fillMaxWidth()
                            .padding(end = dimensionResource(R.dimen.padding_medium))
                            .offset(0.dp, 0.dp)
                            .zIndex(if (selectedIndex.value == index) 1f else 0f)
                    }
                },
                onClick = {
                    selectedIndex.value = index
                    onItemSelection(selectedIndex.value)
                },
                shape = when (index) {

                    0 -> RoundedCornerShape(
                        topStartPercent = cornerRadius,
                        topEndPercent = 0,
                        bottomStartPercent = cornerRadius,
                        bottomEndPercent = 0
                    )

                    items.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = cornerRadius,
                        bottomStartPercent = 0,
                        bottomEndPercent = cornerRadius
                    )
                    else -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = 0,
                        bottomStartPercent = 0,
                        bottomEndPercent = 0
                    )
                },
                border = BorderStroke(
                    1.dp, if (selectedIndex.value == index) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.primary
                    }
                ),
                colors = if (selectedIndex.value == index) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
                },
            ) {
                Text(
                    text = item,
                    fontWeight = FontWeight.Normal,
                    color = if (selectedIndex.value == index) {
                        Color.White
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
                )
            }
        }
    }
}

@Composable
fun GuardarInvitadoButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp)
    ) {
        Text(stringResource(R.string.eventos))
    }
}

@Preview
@Composable
fun NuevoInvitadoScreenPreview() {
    AppTheme {
        //NuevoInvitadoScreen({})
    }
}
