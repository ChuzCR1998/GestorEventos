package utn.proyecto.gestoreventos.data

import android.content.Context

/**
 * App container for Dependency injection.
 */
interface AppContainer {
    val eventosRepository: EventosRepository
}

/**
 * [AppContainer] implementation that provides instance of [OfflineEventosRepository]
 */
class AppDataContainer(private val context: Context) : AppContainer {
    override val eventosRepository: EventosRepository by lazy {
        OfflineEventosRepository(GestorEventosDatabase.getDatabase(context).eventoDao())
    }
}