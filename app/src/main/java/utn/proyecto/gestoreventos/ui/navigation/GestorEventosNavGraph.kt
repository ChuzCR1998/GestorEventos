package utn.proyecto.gestoreventos.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import utn.proyecto.gestoreventos.R
import utn.proyecto.gestoreventos.ui.eventos.EventosScreen
import utn.proyecto.gestoreventos.ui.home.PrincipalScreen
import utn.proyecto.gestoreventos.ui.invitados.InvitadosDestination
import utn.proyecto.gestoreventos.ui.invitados.InvitadosScreen
import utn.proyecto.gestoreventos.ui.invitados.NuevoInvitadoDestination
import utn.proyecto.gestoreventos.ui.invitados.NuevoInvitadoScreen
import utn.proyecto.gestoreventos.ui.login.InicioSecion

enum class GestorEventosScreen(@StringRes val title: Int) {
    Login(title = R.string.Login),
    Start(title = R.string.app_name),
    Eventos(title = R.string.eventos),
    Invitados(title = R.string.invitados),
    NuevoInvitado(title = R.string.nuevo_invitado)
}

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun GestorEventosNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = GestorEventosScreen.Login.name,
        modifier = modifier
    ) {
        composable(route = GestorEventosScreen.Login.name) {
            val context = LocalContext.current
            InicioSecion(
                navigateToHome = {
                    navController.navigate(GestorEventosScreen.Start.name)
                },
                context
            )
        }
        composable(route = GestorEventosScreen.Start.name) {
            PrincipalScreen(
                onNextButtonClicked = {
                    navController.navigate(GestorEventosScreen.Eventos.name)
                },
                cerrarSesionClicked = {
                    navController.navigate(GestorEventosScreen.Login.name)
                }
            )
        }

        composable(route = GestorEventosScreen.Eventos.name) {
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