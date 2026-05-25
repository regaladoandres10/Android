package data.local.database.data.local.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import data.local.dao.CalificacionFinalDao
import data.local.dao.CalificacionUnidadDao
import data.local.dao.CardexDao
import data.local.dao.CargaAcademicaDao
import data.local.dao.UsuarioDao
import data.local.entity.CalificacionFinalEntity
import data.local.entity.CalificacionUnidadEntity
import data.local.entity.CardexEntity
import data.local.entity.CargaAcademicaEntity
import data.local.entity.UsuarioEntity

@Database(
    entities =
        [   UsuarioEntity::class,
            CargaAcademicaEntity::class,
            CardexEntity::class,
            CalificacionUnidadEntity::class,
            CalificacionFinalEntity::class
        ],
    version = 1,
    exportSchema = true
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun cargaDao(): CargaAcademicaDao
    abstract fun cardexDao(): CardexDao
    abstract fun calificacionUnidadDao() : CalificacionUnidadDao
    abstract fun calificacionFinalDao(): CalificacionFinalDao
}
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>
