package data.remote.util

import kotlinx.serialization.json.Json

/**
 * Este archivo nos permite convertir el XML a JSON y despues a un objeto
 */

object SoapParser {
    //Nos permite extraer el JSON del XML
    fun extractJson(
        xml: String,
        tag: String
    ): String {
        return xml
            .substringAfter("<${tag}>")
            .substringBefore("</${tag}>")
            .trim()
    }

    //
    inline fun <reified T>
    //Convierte JSON a objeto
            parseJson(
        json: String
    ): T {

        //Nos regresa un objeto de tipo T
        return Json {
            ignoreUnknownKeys = true
        }.decodeFromString<T>(json)
    }
}