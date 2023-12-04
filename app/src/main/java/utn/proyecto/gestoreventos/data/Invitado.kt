package utn.proyecto.gestoreventos.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import utn.proyecto.gestoreventos.R

@Entity(tableName = "invitados")
data class Invitado (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val eventoId: Int = 0,
    val imagen: String = "",
    val nombre: String = "",
    val genero: String = "Masculino",
    val telefono: String = "",
    val email: String = "",
    val notificado: Boolean = false,
)

/*val invitados = listOf<Invitado>(
    Invitado(0, R.drawable.icono_hombre, R.string.invitado_nombre_masculino, R.string.invitado_genero_masculino, R.string.invitado_telefono, R.string.invitado_email, true),
    Invitado(0, R.drawable.icono_mujer, R.string.invitado_nombre_femenino, R.string.invitado_nombre_femenino, R.string.invitado_telefono, R.string.invitado_email, false),
    Invitado(0, R.drawable.icono_hombre, R.string.invitado_nombre_masculino, R.string.invitado_genero_masculino, R.string.invitado_telefono, R.string.invitado_email, false),
    Invitado(0, R.drawable.icono_mujer, R.string.invitado_nombre_femenino, R.string.invitado_nombre_femenino, R.string.invitado_telefono, R.string.invitado_email, true),

)*/