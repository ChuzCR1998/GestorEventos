package utn.proyecto.gestoreventos.ui

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch


class LoginScreenViewModel: ViewModel() {
    //private val auth: FirebaseAuth = Firebase.auth
    private val _loading = MutableLiveData(false)

    val auth = FirebaseAuth.getInstance()
    fun signInWithEmailAndPassword(
        email: String, password: String, home: () -> Unit
    ) = viewModelScope.launch {
        try {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        home()
                        Log.d("Gestor Eventos", "signInWithEmailAndPassword logueado")
                    } else {
                        if (task.exception?.message?.contains("no user") == true) {
                            // Muestra un mensaje al usuario informando que no existe el usuario
                            //showToast("El usuario no existe")
                        } else {
                            // Otra condición de error
                            // Muestra otro mensaje de error o maneja la situación según tu lógica
                            //showToast("Error al autenticar: ${task.exception?.message}")
                        }

                        Log.d("Gestor Eventos", "HOLA MUNDO")
                    }
                }
        } catch (ex: Exception) {
            Log.d("Gestor Eventos", "signInWithEmailAndPassword: ${ex.message}")
        }
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