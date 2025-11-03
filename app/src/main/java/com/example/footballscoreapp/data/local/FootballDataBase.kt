package com.example.footballscoreapp.data.local

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.footballscoreapp.data.local.dbModels.MatchDbModel

@Database(
    entities = [MatchDbModel::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
abstract class FootballDataBase : RoomDatabase() {

    abstract fun getMatchesDao(): MatchesDao

    companion object {

        private const val DB_NAME = "Football_Database"
        private var INSTANCE: FootballDataBase? = null
        private val LOCK = Any()

        fun getInstance(application: Application): FootballDataBase {
            INSTANCE?.let { return it }
            synchronized(LOCK) {
                INSTANCE?.let { return it }
                val footballDataBase = Room.databaseBuilder(
                    context = application,
                    klass = FootballDataBase::class.java,
                    name = DB_NAME
                ).build()
                INSTANCE = footballDataBase
                return footballDataBase
            }
        }
    }
}