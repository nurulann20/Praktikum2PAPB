package com.example.praktikum1n

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface matkulDAO {

    @Upsert
    suspend fun addMatkuls(matkuls: Matkuls)

    @Query("SELECT * FROM matkuls")
    fun getMatkuls(): Flow<List<Matkuls>>

    @Update
    suspend fun updateMatkuls(matkuls: Matkuls)

    @Delete
    suspend fun deleteMatkuls(matkuls: Matkuls)
}