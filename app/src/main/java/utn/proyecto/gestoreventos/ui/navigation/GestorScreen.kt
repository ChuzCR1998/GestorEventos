package utn.proyecto.gestoreventos.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.ui.eventos.EventosScreen
import utn.proyecto.gestoreventos.ui.login.InicioSecion
import utn.proyecto.gestoreventos.ui.invitados.InvitadosDestination
import utn.proyecto.gestoreventos.ui.invitados.InvitadosScreen
import utn.proyecto.gestoreventos.ui.invitados.NuevoInvitadoDestination
import utn.proyecto.gestoreventos.ui.invitados.NuevoInvitadoScreen
import utn.proyecto.gestoreventos.ui.home.PrincipalScreen

enum class GestorScreen(@StringRes val title: Int) {
    Login(title = R.string.Login),
    Start(title = R.string.app_name),
    Eventos(title = R.string.eventos),
    Invitados(title = R.string.invitados),
    NuevoInvitado(title = R.string.nuevo_invitado)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestorAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text("") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.atras_button)
                    )
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.Q)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestorApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen


    Scaffold(
        topBar = {
            GestorAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = GestorScreen.Login.name,
            modifier = modifier.padding(innerPadding)
        ) {

            composable(route = GestorScreen.Login.name) {
                InicioSecion(
                    navigateToHome = {
                        navController.navigate(GestorScreen.Start.name)
                    }
                )
            }
            composable(route = GestorScreen.Start.name) {
                PrincipalScreen(
                    onNextButtonClicked = {
                        navController.navigate(GestorScreen.Eventos.name)
                    }
                )
            }

            composable(route = GestorScreen.Eventos.name) {
                val context = LocalContext.current
                EventosScreen(
                    onNextButtonClicked = { navController.navigate("${InvitadosDestination.route}/$it") },
                    navigateBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(
                route = InvitadosDestination.routeWithArgs,
                arguments = listOf(navArgument(InvitadosDestination.eventoIdArg) {
                    type = NavType.IntType
                })
                ) {
                val context = LocalContext.current
                InvitadosScreen(
                    onNextButtonClicked = { navController.navigate("${NuevoInvitadoDestination.route}/$it") },
                    navigateBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(
                route = NuevoInvitadoDestination.routeWithArgs,
                arguments = listOf(navArgument(NuevoInvitadoDestination.eventoIdArg) {
                    type = NavType.IntType
                })
            ) {
                val context = LocalContext.current
                NuevoInvitadoScreen(
                    navigateBack = { navController.popBackStack() },
                    modifier = Modifier.fillMaxHeight()
                )
            }

        }
    }
}

@Composable
fun InicioSesionApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){

}