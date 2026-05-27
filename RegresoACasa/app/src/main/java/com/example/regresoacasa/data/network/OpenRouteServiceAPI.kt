package com.example.regresoacasa.data.network

import com.example.regresoacasa.data.model.GeoJSONDirection
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.openrouteservice.org/"

private const val API_KEY =
    "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6IjUxOGRlMmUwNmFmNjQwYWY4MTc1NzQ3ODcwYWMyNzdmIiwiaCI6Im11cm11cjY0In0="

private val retrofit =
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(
            Json {
                ignoreUnknownKeys =
                    true
                isLenient =
                    true
            }
                .asConverterFactory(
                    "application/json"
                        .toMediaType()
                )
        )
        .build()

interface OpenRouteServiceApi {
    @GET("v2/directions/{profile}")
    suspend fun getDirections(
        @Path("profile")
        profile: String,

        @Query("api_key")
        apiKey: String = API_KEY,

        @Query("start")
        start: String,

        @Query("end")
        end: String

    ): GeoJSONDirection

}

object RouteService {
    val api: OpenRouteServiceApi by lazy {
                retrofit.create(OpenRouteServiceApi::class.java)
            }
}