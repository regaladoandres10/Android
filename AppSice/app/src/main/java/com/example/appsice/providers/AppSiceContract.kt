package com.example.appsice.providers

import android.net.Uri

object AppSiceContract {
    const val AUTHORITY = "com.example.appsice.provider"

    //Asignamos codigos para el URIMatcher
    val CODE_CARDEX = 1
    val CODE_CARGA_ACADEMICA = 2

    object CargaAcademicaContract {
        val CONTENT_PATH = "carga_academica"
        val CONTENT_URI_CARGA_ACADEMICA = Uri.parse("content://$AUTHORITY/carga_academica")

        //MIME

        //COLUMNS
    }



    object CardexContract {
        val CONTENT_PATH = "cardex"
        val CONTENT_URI_CARDEX = Uri.parse("content://$AUTHORITY/cardex")

        //MIME

        //COLUMNS
    }
}