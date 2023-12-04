package utn.proyecto.gestoreventos.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import utn.proyecto.gestoreventos.data.Evento
import utn.proyecto.gestoreventos.data.EventosRepository
import utn.proyecto.gestoreventos.data.OfflineEventosRepository

class EventosViewModel(private val eventosRepository: EventosRepository) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    var eventoUiState by mutableStateOf(EventoUiState())
        private set

    val eventosUIState: StateFlow<EventosUiState> =
        eventosRepository.getAllItemsStream().map { EventosUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = EventosUiState()
            )

    var showDialog by mutableStateOf(false)
        private set
    suspend fun onDialogConfirm() {
        showDialog = false
        saveEvento()
    }

    fun onDialogDismiss() {
        showDialog = false
    }

    fun openDialog() {
        showDialog = true
    }

    suspend fun saveEvento() {
        eventosRepository.insertItem(eventoUiState.evento)
        showDialog = false
    }

    fun updateEventoUiState(evento: Evento) {
        eventoUiState =
            EventoUiState(evento = evento)
    }
}

data class EventosUiState(val eventosList: List<Evento> = listOf())

data class EventoUiState(
    val evento: Evento = Evento(),
    //val isEntryValid: Boolean = false
)
