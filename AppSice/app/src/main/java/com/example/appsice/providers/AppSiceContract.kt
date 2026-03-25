package com.example.appsice.providers

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

object AppSiceContract {
    const val AUTHORITY = "com.example.appsice.provider"

    //Asignamos codigos para el URIMatcher de listas
    val CODE_CARDEX = 1
    val CODE_CARGA_ACADEMICA = 2
    //Individual
    val CODE_CARDEX_ITEM = 3
    val CODE_CARGA_ITEM = 4

    object CargaAcademicaContract {
        val CONTENT_PATH = "carga_academica"
        val CONTENT_URI_CARGA_ACADEMICA = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")

        //MIME
        val MIME_DIR = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTENT_PATH"
        val MIME_ITEM = "vnd.android.cursor.item/vnd.$AUTHORITY.$CONTENT_PATH"

        //COLUMNS
    }



    object CardexContract {
        val CONTENT_PATH = "cardex"
        val CONTENT_URI_CARDEX = Uri.parse("content://$AUTHORITY/$CONTENT_PATH")

        //MIME
        val MIME_DIR = "vnd.android.cursor.dir/vnd.$AUTHORITY.$CONTENT_PATH"
        val MIME_ITEM = "vnd.android.cursor.item/vnd.$AUTHORITY.$CONTENT_PATH"

        //COLUMNS
    }
}