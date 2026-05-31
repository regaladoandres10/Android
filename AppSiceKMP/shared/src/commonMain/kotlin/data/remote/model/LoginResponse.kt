package data.local.database.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val acceso: Boolean
)
