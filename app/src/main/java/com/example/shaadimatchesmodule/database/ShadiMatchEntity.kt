package com.example.shaadimatchesmodule.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shaadi_match_table")
data class ShadiMatchEntity(val name: String,
                val email: String,
                val country: String,
                val state: String,
                val city: String,
                val imageUrl: String,
                var status: String,
                @PrimaryKey(autoGenerate = true) val id: Int? = null)

