package utn.proyecto.gestoreventos.data

import kotlinx.coroutines.flow.Flow

class OfflineInvitadosRepository(private val invitadoDao: InvitadoDao) : InvitadosRepository {
    override fun getInvitadoStream(id: Int): Flow<Invitado?> = invitadoDao.getItem(id)

    override suspend fun insertInvitado(invitado: Invitado) = invitadoDao.insert(invitado)

    override suspend fun deleteInvitado(invitado: Invitado) = invitadoDao.delete(invitado)

    override suspend fun updateInvitado(invitado: Invitado) = invitadoDao.update(invitado)

    override fun getAllInvitadosByEvento(eventoId: Int): Flow<List<Invitado>> = invitadoDao.getAllInvidatosByEvento(eventoId)
}