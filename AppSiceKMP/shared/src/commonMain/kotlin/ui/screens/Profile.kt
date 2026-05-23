package ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import coil3.compose.AsyncImage
import data.remote.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi

@OptIn(InternalSerializationApi::class)
@Composable
fun ScreenProfile(
    profile: ProfileStudent
) {

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),

        verticalArrangement =
            Arrangement.spacedBy(
                12.dp
            )
    ) {
        //Foto alumno.
        AsyncImage(
            model = "https://sicenet.surguanajuato.tecnm.mx/fotos/${profile.urlFoto}",
            contentDescription = null,
            modifier =
                Modifier.size(140.dp)
        )
        //Nombre.
        Text(
            text = profile.nombre ?: "",
            style = MaterialTheme
                    .typography
                    .headlineSmall
        )

        ProfileItem(
            "Matrícula",
            profile.matricula
        )

        ProfileItem(
            "Carrera",
            profile.carrera
        )

        ProfileItem(
            "Especialidad",
            profile.especialidad
        )

        ProfileItem(
            "Semestre",
            profile.semActual
        )

        ProfileItem(
            "Créditos acumulados",
            profile.cdtosAcumulados
        )

        ProfileItem(
            "Créditos actuales",
            profile.cdtosActuales
        )

        ProfileItem(
            "Estatus",
            profile.estatus
        )

    }

}


@Composable
private fun ProfileItem(
    title: String,
    value: Any?
) {
    Text(text = "$title: ${value ?: "-"}")
}