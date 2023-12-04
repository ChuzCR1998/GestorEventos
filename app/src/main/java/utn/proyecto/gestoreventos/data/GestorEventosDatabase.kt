package utn.proyecto.gestoreventos.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Evento::class], version = 2, exportSchema = false)
abstract class GestorEventosDatabase : RoomDatabase() {
    abstract fun eventoDao(): EventoDao

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