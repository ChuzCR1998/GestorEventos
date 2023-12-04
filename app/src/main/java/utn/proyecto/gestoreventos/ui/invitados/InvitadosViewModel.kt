package utn.proyecto.gestoreventos.ui.invitados

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.data.Invitado
import utn.proyecto.gestoreventos.data.InvitadosRepository

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
