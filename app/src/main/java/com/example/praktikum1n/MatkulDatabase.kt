package com.example.praktikum1n

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Matkuls::class],
    version = 1,
    exportSchema = true
)
abstract class MatkulDatabase : RoomDatabase() {
    abstract fun matkulsDao(): matkulDAO

    companion object{

        @Volatile
        private var INSTANCE: MatkulDatabase? = null

        fun getDatabase(context: Context): MatkulDatabase {
            if(INSTANCE == null){
                synchronized(this){
                    INSTANCE = buildDatabase(context)
                }
            }

            return INSTANCE!!
        }

        private fun buildDatabase(context: Context): MatkulDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MatkulDatabase::class.java,
                "matkul_database"
            )
                .build()
        }
    }
}