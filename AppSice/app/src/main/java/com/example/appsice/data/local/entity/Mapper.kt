package com.example.appsice.data.local.entity

import com.example.appsice.data.remote.model.CargaAcademica
import com.example.appsice.data.remote.model.ProfileStudent
import kotlinx.serialization.InternalSerializationApi


@OptIn(InternalSerializationApi::class)
fun ProfileStudent.toEntity(): UsuarioEntity {
    return UsuarioEntity(
        fechaReins = this.fechaReins ?: "",
        modEducativo = this.modEducativo ?: 0,
        adeudo = this.adeudo ?: false,
        urlFoto = this.urlFoto ?: "",
        adeudoDescripcion = this.adeudoDescripcion ?: "",
        inscrito = this.inscrito ?: false,
        estatus = this.estatus ?: "",
        semActual = this.semActual ?: 0,
        cdtosAcumulados = this.cdtosAcumulados ?: 0,
        cdtosActuales = this.cdtosActuales ?: 0,
        especialidad = this.especialidad ?: "",
        carrera = this.carrera ?: "",
        lineamiento = this.lineamiento ?: 0,
        nombre = this.nombre ?: "",
        matricula = this.matricula ?: ""
    )
}

@OptIn(InternalSerializationApi::class)
fun CargaAcademica.toEntity(): CargaAcademicaEntity {
    return CargaAcademicaEntity(
        semipresencial = this.semipresencial ?: "",
        observaciones = this.observaciones ?: "",
        docente = this.docente ?: "",
        clvOficial = this.clv ?: "",
        sabado = this.sabado ?: "",
        viernes = this.viernes ?: "",
        jueves = this.jueves ?: "",
        miercoles = this.miercoles ?: "",
        martes = this.martes ?: "",
        lunes = this.lunes ?: "",
        estadoMateria = this.estadoMateria ?: "",
        creditosMateria = this.creditosMateria ?: 0,
        materia = this.materia ?: "",
        grupo = this.grupo ?: ""
    )
}