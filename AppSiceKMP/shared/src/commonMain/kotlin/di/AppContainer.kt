package di

import data.local.repository.*

/**
 * Este archivo sirve para crear dependencias
 */

interface AppContainer {

    //val snRepository: SNRepository

    val usuarioRepository: UsuarioRepository

    val cargaAcademicaRepository: CargaAcademicaRepository

    val cardexRepository: CardexRepository

    val calificacionUnidadRepository: CalificacionUnidadRepository

    val calificacionFinalRepository: CalificacionFinalRepository
}