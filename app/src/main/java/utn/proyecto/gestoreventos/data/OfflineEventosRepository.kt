package utn.proyecto.gestoreventos.data

import kotlinx.coroutines.flow.Flow

class OfflineEventosRepository(private val eventoDao: EventoDao) : EventosRepository {
    override fun getAllItemsStream(): Flow<List<Evento>> = eventoDao.getAllItems()

    override fun getItemStream(id: Int): Flow<Evento?> = eventoDao.getItem(id)

    override suspend fun insertItem(evento: Evento) = eventoDao.insert(evento)

    override suspend fun deleteItem(evento: Evento) = eventoDao.delete(evento)

    override suspend fun updateItem(evento: Evento) = eventoDao.update(evento)
}