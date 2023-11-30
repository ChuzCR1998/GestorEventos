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
    @DrawableRes val imagen: Int,
    @StringRes val titulo: Int,
    @StringRes val descripcion: Int,
    @StringRes val fecha: Int,
    @StringRes val hora: Int = R.string.hora
)

val eventos = listOf<Evento>(
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0,R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(0, R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha)
)