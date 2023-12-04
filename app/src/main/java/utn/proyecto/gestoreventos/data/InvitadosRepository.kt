package utn.proyecto.gestoreventos.data

import kotlinx.coroutines.flow.Flow

interface InvitadosRepository {
    /**
     * Retrieve all the items from the the given data source.
     */

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getInvitadoStream(id: Int): Flow<Invitado?>

    /**
     * Retrieve an item from the given data source that matches with the [eventoId].
     */
    fun getAllInvitadosByEvento(eventoId: Int): Flow<List<Invitado>>

    /**
     * Insert item in the data source
     */
    suspend fun insertInvitado(invitado: Invitado)

    /**
     * Delete item from the data source
     */
    suspend fun deleteInvitado(invitado: Invitado)

    /**
     * Update item in the data source
     */
    suspend fun updateInvitado(invitado: Invitado)
}