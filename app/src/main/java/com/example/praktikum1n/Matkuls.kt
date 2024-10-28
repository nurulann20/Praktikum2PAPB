package com.example.praktikum1n

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "matkuls")
data class Matkuls(
    @PrimaryKey
    @ColumnInfo(name = "namaMatkul")
    val namaMatkul: String,

    @ColumnInfo(name = "tugas")
    val tugas: String,

    @ColumnInfo(name = "isdone")
    val isDone: Boolean
)
