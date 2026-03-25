package com.example.sicecustomer.ui.navigation

import androidx.annotation.StringRes
import com.example.sicecustomer.R

enum class SiceClientScreen(@StringRes val title: Int) {
    Menu(title = R.string.menu),
    Carga(title = R.string.carga),
    Cardex(title = R.string.cardex)
}