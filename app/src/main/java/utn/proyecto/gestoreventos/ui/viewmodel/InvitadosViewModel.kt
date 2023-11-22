package utn.proyecto.gestoreventos.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class InvitadosViewModel : ViewModel() {
    var showDialog by mutableStateOf(false)
        private set
    fun onDialogConfirm() {
        showDialog = false
    }

    fun onDialogDismiss() {
        showDialog = false
    }

    fun openDialog() {
        showDialog = true
    }
}