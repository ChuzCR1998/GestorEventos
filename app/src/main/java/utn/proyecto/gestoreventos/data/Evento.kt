package utn.proyecto.gestoreventos.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import utn.proyecto.gestoreventos.R

@Entity(tableName = "eventos")
data class Evento (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val imagen: Int = 0,
    val titulo: String = "",
    val ubicacion: String = "",
    val fecha: String = "",
    val hora: String = ""
)

/*val eventos = listOf<Evento>(
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0,R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha)
)*/