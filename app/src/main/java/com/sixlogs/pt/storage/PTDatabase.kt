package com.sixlogs.pt.storage

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sixlogs.pt.storage.dao.QazaPrayerDao
import com.sixlogs.pt.storage.entities.QazaPrayerEntities


@Database(entities = [QazaPrayerEntities::class], version = 1, exportSchema = false)
@Entity(tableName = "qazaPrayers")
//@TypeConverters(TypeConvertors::class)
abstract class PTDatabase : RoomDatabase() {
    abstract fun qazaPrayerDao(): QazaPrayerDao


    companion object {
        @Volatile
        private var INSTANCE: PTDatabase? = null

        private val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN price TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN flag TEXT DEFAULT ''")
            }
        }

        fun getAppDatabase(context: Context): PTDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder<PTDatabase>(
                        context.applicationContext, PTDatabase::class.java, "PrayerDB"
                    )
                        .addMigrations(migration_1_2)
                        .allowMainThreadQueries().build()
                }

            }
            return INSTANCE as PTDatabase
        }

        fun destroyInstance() {
            INSTANCE = null
        }


    }


}