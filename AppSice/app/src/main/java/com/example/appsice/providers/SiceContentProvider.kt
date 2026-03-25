package com.example.appsice.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.example.appsice.data.local.database.SiceDatabase
import com.example.appsice.data.local.entity.CardexEntity
import kotlinx.coroutines.runBlocking

//Implementar el URIMatcher
//Se utiliza * para cualquier texto
//Se utiliza # para numeros
private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    //Carga academica
    addURI(AppSiceContract.AUTHORITY, AppSiceContract.CargaAcademicaContract.CONTENT_PATH, AppSiceContract.CODE_CARGA_ACADEMICA)
    //Carga individual
    addURI(AppSiceContract.AUTHORITY, "${AppSiceContract.CargaAcademicaContract.CONTENT_PATH}/*", AppSiceContract.CODE_CARGA_ITEM)

    //Cardex
    addURI(AppSiceContract.AUTHORITY, AppSiceContract.CardexContract.CONTENT_PATH, AppSiceContract.CODE_CARDEX)
    //Cardex Individual
    addURI(AppSiceContract.AUTHORITY, "${AppSiceContract.CardexContract.CONTENT_PATH}/*", AppSiceContract.CODE_CARDEX_ITEM)
}


class SiceContentProvider: ContentProvider() {
    private lateinit var db: SiceDatabase


    override fun onCreate(): Boolean {
        //Implementar la base de datos
        db = SiceDatabase.getDatabase(context!!)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String?>?,
        p2: String?,
        p3: Array<out String?>?,
        p4: String?
    ): Cursor? {

        //Obteniendo la URI
        val URICode = sUriMatcher.match(p0)

        val code = when(URICode) {
            //Lista de cardex
            AppSiceContract.CODE_CARDEX -> {
                db.cardexDao().getAllCardexCursor()
            }

            //Solo uno
            AppSiceContract.CODE_CARDEX_ITEM -> {
                val id = p0.lastPathSegment ?: return null
                db.cardexDao().getCardexCursor(id)
            }

            //Lista de carga
            AppSiceContract.CODE_CARGA_ACADEMICA -> {
                db.cargaDao().getAllCargaCursor()
            }

            //Solo uno
            AppSiceContract.CODE_CARGA_ITEM -> {
                val id = p0.lastPathSegment ?: return null
                db.cargaDao().getCargaCursor(id)
            }

            else -> null

        }
        return code

    }

    override fun getType(p0: Uri): String? {
        return when (sUriMatcher.match(p0)) {

            AppSiceContract.CODE_CARDEX ->
                AppSiceContract.CardexContract.MIME_DIR

            AppSiceContract.CODE_CARDEX_ITEM ->
                AppSiceContract.CardexContract.MIME_ITEM

            AppSiceContract.CODE_CARGA_ACADEMICA ->
                AppSiceContract.CargaAcademicaContract.MIME_DIR

            AppSiceContract.CODE_CARGA_ITEM ->
                AppSiceContract.CargaAcademicaContract.MIME_ITEM

            else -> throw IllegalArgumentException("URI no soportada")
        }
    }

    override fun insert(p0: Uri, values: ContentValues?): Uri? {
        //val context = context ?: return null
        val code = sUriMatcher.match(p0)
        return when (code) {
            AppSiceContract.CODE_CARDEX -> {
                //Validar si son valores nulos
                if (values == null) return null

                //Convertir a Entity de cardex
                val cardex = CardexEntity(
                    fecEsp = values.getAsString("fecha_esperada") ?: "",
                    clvMat = values.getAsString("claveMateria") ?: return null,
                    clvOfiMat = values.getAsString("clvOfiMat") ?: "",
                    materia = values.getAsString("materia") ?: "",
                    cdts = values.getAsInteger("creditos") ?: 0,
                    calif = values.getAsInteger("calificacion") ?: 0,
                    acred = values.getAsString("acred") ?: "",
                    s1 = values.getAsString("semestre1") ?: "",
                    p1 = values.getAsString("periodo1") ?: "",
                    a1 = values.getAsString("a1") ?: "",
                    s2 = values.getAsString("semestre2") ?: "",
                    p2 = values.getAsString("periodo2") ?: "",
                    a2 = values.getAsString("a2") ?: ""
                )

                Log.d("INSERT", "Values: $values")

                //Insertar con corrutina
                runBlocking {
                    db.cardexDao().insert(cardex)
                }

                //Regresaar la URI y construir una URI con la PK
                Uri.withAppendedPath(
                    AppSiceContract.CardexContract.CONTENT_URI_CARDEX,
                    cardex.clvMat
                )
            }
            else -> throw IllegalArgumentException("URI no soportada")
        }
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