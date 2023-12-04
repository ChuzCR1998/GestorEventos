package utn.proyecto.gestoreventos.data

import kotlinx.coroutines.flow.Flow

interface EventosRepository {
    /**
     * Retrieve all the items from the the given data source.
     */
    fun getAllItemsStream(): Flow<List<Evento>>

    /**
     * Retrieve an item from the given data source that matches with the [id].
     */
    fun getItemStream(id: Int): Flow<Evento?>

    /**
     * Insert item in the data source
     */
    suspend fun insertItem(evento: Evento)

    /**
     * Delete item from the data source
     */
    suspend fun deleteItem(evento: Evento)

    /**
     * Update item in the data source
     */
    suspend fun updateItem(evento: Evento)
}