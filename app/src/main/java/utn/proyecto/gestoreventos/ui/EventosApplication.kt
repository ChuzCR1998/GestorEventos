package utn.proyecto.gestoreventos.ui

import android.app.Application
import utn.proyecto.gestoreventos.data.AppContainer
import utn.proyecto.gestoreventos.data.AppDataContainer

class EventosApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}