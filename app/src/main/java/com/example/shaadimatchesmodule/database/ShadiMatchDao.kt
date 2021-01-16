package com.example.shaadimatchesmodule.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShadiMatchDao {

    @Insert
    fun insert(shadiMatch: ShadiMatchEntity)

    @Update
    fun update(shadiMatch: ShadiMatchEntity)

    @Delete
    fun delete(shadiMatch: ShadiMatchEntity)

    @Query("delete from shaadi_match_table")
    fun deleteAllShadiMatches()

    @Query("SELECT * FROM shaadi_match_table")
    fun getAll(): List<ShadiMatchEntity>

    @Query("SELECT * FROM shaadi_match_table")
    fun getShaadiMatches() : LiveData<ShadiMatchEntity>


}