package com.example.nuevo.modelo.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TABLE_PHONE_DETAILS")
data class DetailsPhoneEntity (
    @PrimaryKey//(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "phoneName")
    val phoneName: String,
    @ColumnInfo(name = "phonePrice")
    val phonePrice:String,
    @ColumnInfo(name = "PhoneImage")
    val phoneImage: String,
    @ColumnInfo(name = "PhoneDescription")
    val phoneDescription: String,
    @ColumnInfo(name = "PhoneLastPrice")
    val phoneLastPrice:String,
    @ColumnInfo(name = "PhoneCredit")
    val phoneCredit: Boolean
        )
