package utn.proyecto.gestoreventos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Evento::class, Invitado::class], version = 5, exportSchema = false)
abstract class GestorEventosDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDao
    abstract fun invitadoDao(): InvitadoDao

    companion object {
        @Volatile
        private var Instance: GestorEventosDatabase? = null

        fun getDatabase(context: Context): GestorEventosDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GestorEventosDatabase::class.java, "eventos_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}