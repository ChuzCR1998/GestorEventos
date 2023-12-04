package utn.proyecto.gestoreventos.ui.login

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.launch


class LoginScreenViewModel: ViewModel( ) {
    //private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    val auth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(
        email: String, password: String, context: Context,home: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        home()
                        Log.d("Gestor Eventos", "signInWithEmailAndPassword logueado")
                    }
                }
                .addOnFailureListener{ exception ->
                    when (exception) {
                        is FirebaseAuthInvalidCredentialsException -> {
                            mensajeAlerta("Datos incorrectos", context)
                        }
                        else -> {
                            mensajeAlerta(exception.message ?: "Error:", context)
                        }
                    }
                }
        } catch (ex: Exception) {
            Log.d("Gestor Eventos", "signInWithEmailAndPassword: ${ex.message}")
        }
    }
    private fun mensajeAlerta(mensaje: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Gestor Eventos.")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        home: () -> Unit
    ) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        home()
                    } else {
                        Log.d(
                            "Inventario",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }

        }
    }
}