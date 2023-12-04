package utn.proyecto.gestoreventos.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import utn.proyecto.gestoreventos.ui.LoginScreenViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun UserForm(isCreateAccount: Boolean = false ,
             onDone: (String,String)-> Unit = {correo,contrasenna ->}
) {
val correo = rememberSaveable{
    mutableStateOf("")
}
    val contrasenna = rememberSaveable{
        mutableStateOf("")
    }
    val  contrasennaVisible = rememberSaveable() {
        mutableStateOf(false)
    }
    val valido = remember(correo.value, contrasenna.value){
        correo.value.trim().isNotBlank() && contrasenna.value.trim().isNotBlank()
    }

    val keyboartController = LocalSoftwareKeyboardController.current
 Column (horizontalAlignment = Alignment.CenterHorizontally) {

     
     CorreoImput(
            correoState = correo
        )
        ContrasennaInput(
            contrasennaState = contrasenna,
            labelId = "Contraseña",
            contrasennaVisible = contrasennaVisible
        )
        SubmitButton(
            textId = if (isCreateAccount) "Crear Cuenta" else "Iniciar Sesión",
            inputValido = valido
        ){
            onDone(correo.value.trim(), contrasenna.value.trim())
        keyboartController?.hide()
        }
 }
}

@Composable
fun SubmitButton(
    textId: String,
    inputValido: Boolean,
    onClic: () ->Unit
) {
    Button(onClick = onClic,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape,
        enabled = inputValido

    ) {
        Text(text = textId,
        modifier = Modifier.padding(5.dp)
        )
    }

}

@Composable
fun CorreoImput(correoState: MutableState<String>,
                labelId: String= "Correo"
){
 InputFiel(
     valueState = correoState,
     labelId = labelId,
     keyboarTypes = KeyboardType.Email
 )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContrasennaInput(
    contrasennaState: MutableState<String>,
    labelId: String,
    contrasennaVisible: MutableState<Boolean>
) {
    val visualTransformation = if (contrasennaVisible.value)
        VisualTransformation.None
    else
        PasswordVisualTransformation()

    OutlinedTextField(
        value = contrasennaState.value ,
        onValueChange = {contrasennaState.value = it},
        label = { Text (text = labelId)},
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth(),

        visualTransformation = visualTransformation,
        trailingIcon = {
            if (contrasennaState.value.isNotBlank()){
                MostrarContrasenna(contrasennaVisible)
            }
            else null
        }
    )
}

@Composable
fun MostrarContrasenna(contrasennaVisible: MutableState<Boolean>) {

    val image =
        if (contrasennaVisible.value)
            Icons.Default.VisibilityOff
    else
            Icons.Default.Visibility
    IconButton(onClick = { contrasennaVisible.value = !contrasennaVisible.value    }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputFiel(valueState: MutableState<String>,
              labelId: String,
              isSingleLine: Boolean = true,
              keyboarTypes: KeyboardType
) {
    OutlinedTextField(
    value = valueState.value,
    onValueChange = {valueState.value = it },
    label = { Text(text = labelId)},
    singleLine = isSingleLine,
    modifier = Modifier
        .padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 250.dp)
        .fillMaxWidth(),
    keyboardOptions = KeyboardOptions(
        keyboardType = keyboarTypes
    )
)

}

@Composable
fun InicioSecion(
    navigateToHome: () -> Unit,
    context: Context,
    viewModel: LoginScreenViewModel = viewModel()):Unit
{
    val mostraLogin = rememberSaveable {
        mutableStateOf(true)
    }
    Surface (modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally )
        {
            if (mostraLogin.value){
                Text(text = "Iniciar Sesión")
                UserForm(
                    isCreateAccount = false
                ){

                        correo, contrasenna ->
                    Log.d("GestorEventos","Logueado con exito $correo")
                    viewModel.signInWithEmailAndPassword(correo,contrasenna,context,navigateToHome)

                }

            }
            else{
                Text(text = "Crear Cuenta")
                UserForm(
                    isCreateAccount = true
                ){

                    correo, contrasenna ->
                    Log.d("GestorEventos","Se creo la cuenta con exito $correo")

                    viewModel.createUserWithEmailAndPassword(correo,contrasenna,navigateToHome)
                }

            }

            Spacer(modifier = Modifier.height(15.dp))
            Row (
                horizontalArrangement =  Arrangement.Center,
                verticalAlignment =  Alignment.CenterVertically
            ){
                val tex1 =
                    if(mostraLogin.value) "No tienes Cuenta?"
                    else "Ya tienes Cuenta?"
                val tex2 =
                    if(mostraLogin.value) "Regístrate"
                    else "inicia Sesión"
                Text(text = tex1)
                Text(text =tex2,
                modifier = Modifier
                    .clickable { mostraLogin.value = !mostraLogin.value }
                    .padding(start = 5.dp),
                    color = Color.Blue
                )

            }

        }
    }



}