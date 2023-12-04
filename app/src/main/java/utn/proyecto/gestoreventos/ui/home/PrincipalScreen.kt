package utn.proyecto.gestoreventos.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import utn.proyecto.gestoreventos.GestorEventosTopAppBar
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.ui.navigation.GestorEventosScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalScreen(
    onNextButtonClicked: () -> Unit,
    cerrarSesionClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            GestorEventosTopAppBar(
                title = "Inicio",
                canNavigateBack = false,
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Center
        ) {
            PrincipalImage(onNextButtonClicked, cerrarSesionClicked, modifier)
        }
    }

}

@Composable
fun EventosButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp).padding(top = 240.dp)
    ) {
        Text(stringResource(R.string.eventos))
    }
}

@Composable
fun CerrarSesionButton(
    cerrarSesionClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedButton(
        onClick = {
            cerrarSesionClicked()
        },
        modifier = modifier.widthIn(min = 250.dp).padding(bottom = 40.dp)
    ) {
        Text(stringResource(R.string.Logout))
    }
}

@Composable
fun PrincipalImage(
    onNextButtonClicked: () -> Unit,
    cerrarSesionClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Create a box to overlap image and texts
    Box(modifier) {
        Image(
            painter = painterResource(id = R.drawable.androidparty),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.3F
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EventosButton(onClick = onNextButtonClicked)
            Spacer(modifier = modifier.weight(1f))
            CerrarSesionButton(cerrarSesionClicked = cerrarSesionClicked)
        }
    }
}