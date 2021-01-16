package com.example.shaadimatchesmodule.model

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.shaadimatchesmodule.database.ShadiMatchDatabase
import com.example.shaadimatchesmodule.database.ShadiMatchEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

//TODO:REPOSITORY
class ShadiRepository {

    companion object {

        var shaadiDatabase: ShadiMatchDatabase? = null

        var shaadiTableModel: LiveData<ShadiMatchEntity>? = null

        fun initializeDB(context: Context) : ShadiMatchDatabase {
            return ShadiMatchDatabase.getInstance(context)
        }

        fun insertData(context: Context, name: String, email: String,country: String,
                       state: String,city: String,imageUrl: String,status: String) {

            shaadiDatabase = initializeDB(context)
            CoroutineScope(IO).launch {
                val loginDetails = ShadiMatchEntity(name, email,country,state,city,imageUrl,status)
                shaadiDatabase!!.shadiMatchDao().insert(loginDetails)
            }

        }

        fun getLoginDetails(context: Context) : LiveData<ShadiMatchEntity>? {
            shaadiDatabase = initializeDB(context)
            shaadiTableModel = shaadiDatabase!!.shadiMatchDao().getShaadiMatches()
            return shaadiTableModel
        }

    }
}