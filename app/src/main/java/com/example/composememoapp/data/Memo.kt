package com.example.composememoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "memo")
data class Memo(
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "lock") var lock : Boolean
) {
    @PrimaryKey
    @ColumnInfo(name = "id") var id : String = UUID.randomUUID().toString()
    @ColumnInfo(name = "password") var password : String = ""
}