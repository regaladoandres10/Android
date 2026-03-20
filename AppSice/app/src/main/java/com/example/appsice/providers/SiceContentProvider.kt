package com.example.appsice.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

//Implementar el URIMatcher
val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    //Carga academica
    addURI(AppSiceContract.AUTHORITY, AppSiceContract.CargaAcademicaContract.CONTENT_PATH,
        AppSiceContract.CODE_CARGA_ACADEMICA)
    //Cardex
    addURI(AppSiceContract.AUTHORITY, AppSiceContract.CardexContract.CONTENT_PATH, AppSiceContract.CODE_CARDEX)
}


class SiceContentProvider: ContentProvider() {

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String?>?,
        p2: String?,
        p3: Array<out String?>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(
        p0: Uri,
        p1: String?,
        p2: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }

    override fun update(
        p0: Uri,
        p1: ContentValues?,
        p2: String?,
        p3: Array<out String?>?
    ): Int {
        TODO("Not yet implemented")
    }
}