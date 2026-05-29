package ui.navigation

import appsicekmp.shared.generated.resources.Res
import appsicekmp.shared.generated.resources.*
import org.jetbrains.compose.resources.StringResource

enum class SICEScreen(val title: StringResource) {
    LogIn(Res.string.sicedroid),
    Menu(Res.string.menu),
    Profile(Res.string.profile),
    Carga(Res.string.carga),
    Cardex(Res.string.cardex),
    CalificacionUnidad(Res.string.calificacionUnidad),
    CalificacionFinal(Res.string.calificacionFinal)
}