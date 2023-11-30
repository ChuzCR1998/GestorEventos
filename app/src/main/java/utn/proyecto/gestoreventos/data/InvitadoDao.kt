package utn.proyecto.gestoreventos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface InvitadoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(invitado: Invitado)

    @Update
    suspend fun update(invitado: Invitado)

    @Delete
    suspend fun delete(invitado: Invitado)

    @Query("SELECT * from invitados WHERE id = :id")
    fun getItem(id: Int): Flow<Invitado>

    @Query("SELECT * from invitados ORDER BY nombre ASC")
    fun getAllItems(): Flow<List<Invitado>>
}