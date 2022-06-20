package com.malikaproject.ecommerceapp

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

class ItemList : AppCompatActivity() {
    var baranglist = ArrayList<Barang>()

    private lateinit var brg1 : TextView

    private lateinit var jmlb1 : EditText

    private lateinit var hrg1 : CheckBox

    private lateinit var hpdk1 : TextView

    private lateinit var sp1 : Spinner

    private lateinit var plus : View
    private lateinit var yar : Button
    private lateinit var hom : Button

    private lateinit var i : Intent
    private lateinit var rvBarang : RecyclerView
    private lateinit var fab : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        plus = findViewById(R.id.btn_fab)
        yar = findViewById(R.id.bayar)

        i = Intent()
        rvBarang = findViewById(R.id.rv_barang)

        rvBarang.setHasFixedSize(true)
        rvBarang.layoutManager = LinearLayoutManager(this)

        var user = intent.getStringExtra("username")

            if(user == "Admin"){

                onEditBarang()

            }

        hom = findViewById(R.id.btm)
        hom.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
                .apply {
                    putExtra("username", user)
                }
            startActivity(i)
        }

        plus.setOnClickListener {
            val i = Intent(this, ManageActivity::class.java)
                .apply {
                    putExtra("username", user)
                }
            startActivity(i)
        }

        loadAllBarang()
    }

    private fun onEditBarang() {
        plus.visibility = View.VISIBLE
        yar.visibility = View.GONE
    }

    private fun loadAllBarang() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data....")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.REARANG)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    baranglist.clear()
                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, "Data Empty", Toast.LENGTH_SHORT).show()
                    } else {
                        for (i in 0 until jsonArray?.length()!!) {
                            val jsonObject = jsonArray?.optJSONObject(i)
                            baranglist.add(
                                Barang(jsonObject.getString("id"),
                                    jsonObject.getString("nama"),
                                    jsonObject.getString("harga"),
                                    jsonObject.getString("kategori"),
                                    jsonObject.getString("detail"))
                            )

                            if (jsonArray?.length()-1 == i){
                                loading.dismiss()
                            }
                        }

                        val adapter = RVAdapterBarang(applicationContext, baranglist);
                        adapter.notifyDataSetChanged()
                        rvBarang.adapter = adapter
                        Log.i("Recyclerview Barang" , baranglist.size.toString())
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, "Failure, check log", Toast.LENGTH_SHORT).show()
                    anError?.errorDetail?.toString()?.let { Log.d("ON ERROR ", it) }
                }
            })
    }

    override fun onResume() {
        super.onResume()
        loadAllBarang()
    }


}

