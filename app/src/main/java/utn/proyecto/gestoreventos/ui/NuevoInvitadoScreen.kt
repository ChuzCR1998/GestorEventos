package utn.proyecto.gestoreventos.ui

import android.os.Build
import android.util.Log
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.compose.AppTheme
import utn.proyecto.gestoreventos.R
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoInvitadoScreen(
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column (
        modifier = modifier,
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
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text(stringResource(R.string.nombre)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.correo)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = dimensionResource(R.dimen.padding_medium),
                        end = dimensionResource(R.dimen.padding_medium)
                    )
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
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
                Log.e("CustomToggle", "Selected item : ${generos[it]}")
            }

            Spacer(
                modifier = Modifier
                .height(dimensionResource(R.dimen.padding_medium))
                )

            Button(
                onClick = { sendEmail(email ,nombre,telefono)},
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
Button(onClick = {
    sendEmail(email,nombre,telefono)
}) {

}
        }
    }

}
fun sendEmail(correo: String,nombre: String,telfono: String) {
    val senderEmail = "gestioneventos23@gmail.com" // Reemplaza con tu dirección de correo
    val senderPassword = "yqzwlrxqhlywmrnb" // Reemplaza con tu contraseña

    val properties = Properties().apply {
        put("mail.smtp.host", "smtp.gmail.com") // Reemplaza con tu servidor SMTP
        put("mail.smtp.port", "587") // Reemplaza con el puerto del servidor SMTP
        put("mail.smtp.auth", "true")
        put("mail.smtp.socketFactory.port", "465")
        put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
    }

    val session = Session.getInstance(properties, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(senderEmail, senderPassword)
        }
    })

    try {
        val message = MimeMessage(session)
        message.setFrom(InternetAddress(senderEmail))
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correo)) // Reemplaza con la dirección de correo del destinatario
        message.subject = "Notificación de Invitado"
        message.setText("$nombre Has sido invitado a un nuevo Evento, te vamos a contactar al siguiente número $telfono")

        Transport.send(message)
    } catch (e: MessagingException) {
        e.printStackTrace()
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
        NuevoInvitadoScreen({})
    }
}