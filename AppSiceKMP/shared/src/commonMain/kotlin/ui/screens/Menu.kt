package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Button
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuScreen(
    onPerfilClick: () -> Unit,
    onCargaClick: () -> Unit,
    onCardexClick: () -> Unit,
    onCaliUnidadClick: () -> Unit,
    onCaliFinalClick: () -> Unit
) {
    //Contenedor principal.
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),

        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        //PERFIL
        MenuButton(
            text = "PERFIL",
            onClick = onPerfilClick
        )
        //CARGA
        MenuButton(
            text = "CARGA ACADÉMICA",
            onClick = onCargaClick
        )

        //CARDEX
        MenuButton(
            text = "CARDEX",
            onClick = onCardexClick
        )

        //CALIFICACIONES UNIDAD
        MenuButton(
            text = "CALIFICACIONES UNIDAD",
            onClick = onCaliUnidadClick
        )

        //CALIFICACIONES FINAL
        MenuButton(
            text = "CALIFICACIONES FINALES",
            onClick = onCaliFinalClick
        )

    }

}


/*
 Botón reutilizable.
*/
@Composable
private fun MenuButton(
    text: String,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier =
            Modifier
                .fillMaxWidth()
    ) {
        Text(text = text)
    }
}