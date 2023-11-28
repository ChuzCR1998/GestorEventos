package utn.proyecto.gestoreventos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import utn.proyecto.gestoreventos.ui.InicioSecion
import utn.proyecto.gestoreventos.ui.UserForm
import com.example.compose.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            AppTheme {
                GestorApp()
            }
        }
        
        
    }


}