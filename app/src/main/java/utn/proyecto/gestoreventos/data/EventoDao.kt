package utn.proyecto.gestoreventos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EventoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(evento: Evento)

    @Update
    suspend fun update(evento: Evento)

    @Delete
    suspend fun delete(evento: Evento)

    @Query("SELECT * from eventos WHERE id = :id")
    fun getItem(id: Int): Flow<Evento>

    @Query("SELECT * from eventos ORDER BY fecha ASC")
    fun getAllItems(): Flow<List<Evento>>
}