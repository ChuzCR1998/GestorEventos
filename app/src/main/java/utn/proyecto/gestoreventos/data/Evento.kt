package utn.proyecto.gestoreventos.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import utn.proyecto.gestoreventos.R

data class Evento (
    @DrawableRes val imagen: Int,
    @StringRes val titulo: Int,
    @StringRes val descripcion: Int,
    @StringRes val fecha: Int
)

val eventos = listOf<Evento>(
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha),
    Evento(R.drawable.icono_evento, R.string.evento_titulo, R.string.evento_descripcion, R.string.evento_fecha)
)