package com.example.appsice.data.local.entity

import com.example.appsice.data.remote.model.CalificacionFinal
import com.example.appsice.data.remote.model.CalificacionUnidad
import com.example.appsice.data.remote.model.Cardex
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

@OptIn(InternalSerializationApi::class)
fun Cardex.toEntity(): CardexEntity {
    return CardexEntity(
        fecEsp = this.fecEsp ?: "",
        clvMat = this.clvMat ?: "",
        clvOfiMat = this.clvOfiMat ?: "",
        materia = this.materia ?: "",
        cdts = this.cdts ?: 0,
        calif = this.calif ?: 0,
        acred = this.acred ?: "",
        s1 = this.s1 ?: "",
        p1 = this.p1 ?: "",
        a1 = this.a1 ?: "",
        s2 = this.s2 ?: "",
        p2 = this.p2 ?: "",
        a2 = this.a2 ?: ""
    )
}

@OptIn(InternalSerializationApi::class)
fun CalificacionUnidad.toEntity(): CalificacionUnidadEntity {
    return CalificacionUnidadEntity(
        observaciones = observaciones ?: "",
        c13 = c13 ?: "",
        c12 = c12 ?: "",
        c11 = c11 ?: "",
        c10 = c10 ?: "",
        c9 = c9 ?: "",
        c8 = c8 ?: "",
        c7 = c7 ?: "",
        c6 = c6 ?: "",
        c5 = c5 ?: "",
        c4 = c4 ?: "",
        c3 = c3 ?: "",
        c2 = c2 ?: "",
        c1 = c1 ?: "",
        unidadesActivas = unidadesActivas ?: "",
        materia = materia ?: "",
        grupo = grupo ?: ""
    )
}

@OptIn(InternalSerializationApi::class)
fun CalificacionFinal.toEntity(): CalificacionFinalEntity {
    return CalificacionFinalEntity(
        calif = this.calif ?: 0,
        acred = this.acred ?: "",
        grupo = this.grupo ?: "",
        materia = this.materia ?: "",
        observaciones = this.observaciones ?: ""
    )
}