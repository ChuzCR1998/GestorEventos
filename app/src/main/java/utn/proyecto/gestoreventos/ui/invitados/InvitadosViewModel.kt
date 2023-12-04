package utn.proyecto.gestoreventos.ui.invitados

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.data.Invitado
import utn.proyecto.gestoreventos.data.InvitadosRepository
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class InvitadosViewModel(
    savedStateHandle: SavedStateHandle,
    private val invitadosRepository: InvitadosRepository) : ViewModel() {

    private val eventoId: Int = checkNotNull(savedStateHandle[InvitadosDestination.eventoIdArg])
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    var invitadoUiState by mutableStateOf(InvitadoUiState(eventoId = eventoId))
        private set

    val invitadosUIState: StateFlow<InvitadosUiState> =
        invitadosRepository.getAllInvitadosByEvento(eventoId).map { InvitadosUiState(invitadosList = it, eventoId = eventoId) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = InvitadosUiState()
            )

    var showDialog by mutableStateOf(false)
        private set
    suspend fun onDialogConfirm() {
        showDialog = false
        saveInvitado()
    }

    fun onDialogDismiss() {
        showDialog = false
    }

    fun openDialog() {
        showDialog = true
    }

    suspend fun saveInvitado(): Boolean {
        if (validateInput()) {
            invitadosRepository.insertInvitado(invitadoUiState.invitado)
            showDialog = false
            return true;
        }
        return false;
    }

    fun updateInvitadoUiState(invitado: Invitado) {
        invitadoUiState =
            InvitadoUiState(invitado = invitado)
    }

    private fun validateInput(uiState: Invitado = invitadoUiState.invitado): Boolean {
        return with(uiState) {
            nombre.isNotBlank() && email.isNotBlank() && telefono.isNotBlank()
        }
    }


    fun sendEmail(invitado: Invitado) {
        runBlocking {
            GlobalScope.launch(Dispatchers.IO) {
                val senderEmail = "gestioneventos23@gmail.com" // Reemplaza con tu dirección de correo
                val senderPassword = "yqzwlrxqhlywmrnb" // Reemplaza con tu contraseña

                val properties = Properties().apply {
                    put("mail.smtp.host", "smtp.gmail.com") // Reemplaza con tu servidor SMTP
                    put("mail.smtp.port", "587") // Reemplaza con el puerto del servidor SMTP
                    put("mail.smtp.auth", "true")
                    put("mail.smtp.starttls.enable", "true")
                    //put("mail.smtp.socketFactory.port", "465")
                    //put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
                }

                val session = Session.getInstance(properties, object : Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(senderEmail, senderPassword)
                    }
                })

                try {
                    val message = MimeMessage(session)

                    message.setFrom(InternetAddress(senderEmail))
                    message.setRecipients(
                            Message.RecipientType.TO,
                            InternetAddress.parse(invitado.email)
                        ) // Reemplaza con la dirección de correo del destinatario
                    message.subject = "Notificación de Invitado"
                    message.setText(invitado.nombre + "¡Haz sido invitado a un nuevo Evento! Te vamos a contactar al siguiente número " + invitado.telefono)

                    Transport.send(message)
                } catch (e: MessagingException) {
                    e.printStackTrace()
                }
            }
        }
    }
}
data class InvitadosUiState(
    val invitadosList: List<Invitado> = listOf(),
    val eventoId: Int = 0
)

data class InvitadoUiState(
    val invitado: Invitado = Invitado(),
    val eventoId: Int = 0
    //val isEntryValid: Boolean = false
)
