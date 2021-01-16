package com.example.shaadimatchesmodule.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.shaadimatchesmodule.R
import com.example.shaadimatchesmodule.adapter.ShadiMatchAdapter
import com.example.shaadimatchesmodule.database.ShadiMatchDatabase
import com.example.shaadimatchesmodule.database.ShadiMatchEntity
import com.example.shaadimatchesmodule.interactions.ItemInteractor
import com.example.shaadimatchesmodule.model.ShaadiViewModel
import com.example.shaadimatchesmodule.util.AppAndroidUtils
import com.example.shaadimatchesmodule.views.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), ItemInteractor {

    private val TAG = this.javaClass.simpleName
    var requestQueue: RequestQueue? = null
    private val shaadiMatchList: ArrayList<ShadiMatchEntity> = ArrayList<ShadiMatchEntity>()
    lateinit var mAdapter: ShadiMatchAdapter
    private val APIURL="https://randomuser.me/api/?results=10"
    val scope = CoroutineScope(Dispatchers.IO)
    lateinit var shaadiViewModel: ShaadiViewModel
    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this@MainActivity
        //TODO:VIEWMODEL DECLARATION
        shaadiViewModel = ViewModelProvider(this).get(ShaadiViewModel::class.java)
        shaadiMatchList.clear()
        if(AppAndroidUtils.isNetWorkAvailable()) {
            fetchShaadiMates()
        }
        else
        {
            refreshList();
        }
    }

    //TODO:API CALL TO GET MATCHES & JSON PARSING TO SAVE DATA IN DB
    fun fetchShaadiMates() {
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET,
            APIURL,
            Response.Listener { response ->
                VolleyLog.wtf(response, "utf-8")
                val json_contact: JSONObject = JSONObject(response.toString())
                var jsonarray_info: JSONArray = json_contact.getJSONArray("results")
                for (i in 0 until jsonarray_info.length()) {
                    //creating json object
                    var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(i)
                    shaadiViewModel.insertData(
                                context,
                         json_objectdetail.getJSONObject("name").optString("title") + "" +
                                json_objectdetail.getJSONObject("name").optString("first") + "" +
                                json_objectdetail.getJSONObject("name").optString("last")
                                +" ("+   json_objectdetail.optString("gender")+") ",
                                json_objectdetail.optString("email"),
                                json_objectdetail.getJSONObject("location").optString("country"),
                                json_objectdetail.getJSONObject("location").optString("state"),
                                json_objectdetail.getJSONObject("location").optString("city"),
                                json_objectdetail.getJSONObject("picture").optString("large"),
                        "")
                }
                refreshList();

            },
            Response.ErrorListener { error ->
            })

        requestQueue?.add(stringRequest)
    }

    //TODO:REFRESH UI FROM ROOM DATABASE WITH RESPECT TO API CALL REESPONSE
    fun refreshList()
    {
        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        shaadiViewModel.getShadiMatchDetails(context)!!.observe(this, Observer {

            if (it == null) {
                //NO DATA
            } else {
                //DATA FOUND
                scope.launch {
                    Dispatchers.IO
                    val shadiMatchDao =
                        ShadiMatchDatabase.getInstance(applicationContext).shadiMatchDao()
                    scope.launch {
                        for (i in shadiMatchDao.getAll()) {
                            if(!shaadiMatchList.contains(i)) {
                                shaadiMatchList.add(i);
                            }
                        }

                    }
                }
                mAdapter = ShadiMatchAdapter(
                    shaadiMatchList,
                    this@MainActivity,
                    applicationContext
                )

                val itemDecoration = DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
                shaadi_match_recyclerView.setHasFixedSize(true)
                shaadi_match_recyclerView.addItemDecoration(itemDecoration)
                shaadi_match_recyclerView.addItemDecoration(
                    SpacesItemDecoration(
                        1,
                        16,
                        true
                    )
                )
                shaadi_match_recyclerView.setLayoutManager(
                    LinearLayoutManager(
                        this
                    )
                );
                shaadi_match_recyclerView.adapter = mAdapter
                progressBar.visibility = View.GONE
                mAdapter.notifyDataSetChanged()

            }
        })
    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll(TAG)
    }

    //TODO:DECLINE BUTTON CLICK EVENT TO UPDATE DB
    override fun onDeclineImageClick(shadiMatchPojo: ShadiMatchEntity,position:Int) {
        Toast.makeText(context, context.resources.getString(R.string.hint_declined) +shadiMatchPojo.name, Toast.LENGTH_LONG).show()
        scope.launch {
            Dispatchers.IO
            val shadiMatchDao = ShadiMatchDatabase.getInstance(applicationContext).shadiMatchDao()
            shadiMatchPojo.status = "0"
            shadiMatchDao.update(shadiMatchPojo)
        }
        mAdapter.notifyDataSetChanged()
    }

    //TODO:ACCEPT BUTTON CLICK EVENT TO UPDATE DB
    override fun onAcceptImageClick(shadiMatchPojo: ShadiMatchEntity,position:Int) {
        Toast.makeText(context, context.resources.getString(R.string.hint_accepeted)  +shadiMatchPojo.name, Toast.LENGTH_LONG).show()
        scope.launch {
            Dispatchers.IO
            val shadiMatchDao = ShadiMatchDatabase.getInstance(applicationContext).shadiMatchDao()
            shadiMatchPojo.status = "1"
            shadiMatchDao.update(shadiMatchPojo)
        }
        mAdapter.notifyDataSetChanged()
    }

}