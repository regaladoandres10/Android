package com.example.appsice.ui.navigation

import androidx.annotation.StringRes
import com.example.appsice.R

enum class SICEScreen(@StringRes val title: Int) {
    LogIn(title = R.string.sicedroid),
    Menu(title = R.string.menu),
    Profile(title = R.string.profile),
    Carga(title = R.string.carga),
    Cardex(title = R.string.cardex),
    CalificacionUnidad(title = R.string.calificacionUnidad),
    CalificacionFinal(title = R.string.calificacionFinal)
}