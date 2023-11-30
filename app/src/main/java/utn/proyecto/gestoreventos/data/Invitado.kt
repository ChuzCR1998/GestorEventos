package utn.proyecto.gestoreventos.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import utn.proyecto.gestoreventos.R

data class Invitado (
    @DrawableRes val imagen: Int,
    @StringRes val nombre: Int,
    @StringRes val genero: Int,
    @StringRes val telefono: Int,
    @StringRes val email: Int = R.string.hora,
    val notificado: Boolean,
)

val invitados = listOf<Invitado>(
    Invitado(R.drawable.icono_hombre, R.string.invitado_nombre_masculino, R.string.invitado_genero_masculino, R.string.invitado_telefono, R.string.invitado_email, true),
    Invitado(R.drawable.icono_mujer, R.string.invitado_nombre_femenino, R.string.invitado_nombre_femenino, R.string.invitado_telefono, R.string.invitado_email, false),
    Invitado(R.drawable.icono_hombre, R.string.invitado_nombre_masculino, R.string.invitado_genero_masculino, R.string.invitado_telefono, R.string.invitado_email, false),
    Invitado(R.drawable.icono_mujer, R.string.invitado_nombre_femenino, R.string.invitado_nombre_femenino, R.string.invitado_telefono, R.string.invitado_email, true),

)