package utn.proyecto.gestoreventos

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import utn.proyecto.gestoreventos.ui.EventosScreen
import utn.proyecto.gestoreventos.ui.InicioSecion
import utn.proyecto.gestoreventos.ui.PrincipalScreen

enum class GestorScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Eventos(title = R.string.eventos)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestorAppBar(
    currentScreen: GestorScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestorApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = GestorScreen.valueOf(
        backStackEntry?.destination?.route ?: GestorScreen.Start.name
    )

    Scaffold(
        topBar = {
            GestorAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = GestorScreen.Start.name,
            modifier = modifier.padding(innerPadding)
        ) {
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
                    onNextButtonClicked = { navController.navigate(GestorScreen.Eventos.name) },
                    onCancelButtonClicked = {
                        //cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSelectionChanged = { },
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