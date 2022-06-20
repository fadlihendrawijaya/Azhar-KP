package com.malikaproject.ecommerceapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.malikaproject.ecommerceapp.model.DataPengguna
import com.malikaproject.ecommerceapp.model.ResponseData
import com.malikaproject.ecommerceapp.model.ResponseLogin
import com.malikaproject.ecommerceapp.network.RetrofitClient
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Profile : AppCompatActivity(){
    private var nm : String = ""
    private var alamat : String = ""
    private var tanggal : String = ""
    private var kelamin : String = ""

    private lateinit var nama : TextView
    private lateinit var user : TextView
    private lateinit var almt : TextView
    private lateinit var tgl : TextView
    private lateinit var gender : TextView

    lateinit var logot : View
    lateinit var hom : View
    lateinit var gbr : ImageView
    private var usrnm : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nama = findViewById(R.id.ed_nama)
        user = findViewById(R.id.etUsername)
        almt = findViewById(R.id.etalamat)
        tgl = findViewById(R.id.ed_tgllhr)
        gender = findViewById(R.id.ed_klm)
        gbr = findViewById(R.id.gambar1)

        val image = intArrayOf(
            R.drawable.laki,
            R.drawable.cewe
        )

        if(gender.equals("Laki-laki")){
            gbr.setImageResource(image[0])
        }else{
            gbr.setImageResource(image[1])
        }

        var id = intent.getStringExtra("username")

        usrnm = id.toString()



        hom = findViewById(R.id.gohom)
        hom.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
                .apply {
                    putExtra("username", id)
                }
            startActivity(i)
        }

        logot = findViewById(R.id.klikout)
        logot.setOnClickListener {
            val keluar = Intent(this@Profile, LoginActivity::class.java)
            startActivity(keluar)
        }
        getData()

    }

    private fun getData(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menampilkan data...")
        loading.show()

        AndroidNetworking.post(ApiEndPoint.RUSER)
            .addBodyParameter("id",usrnm)
            .addBodyParameter("nama", nm)
            .addBodyParameter("alamat", alamat)
            .addBodyParameter("tl", tanggal)
            .addBodyParameter("jns_klm", kelamin)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("hasil"),Toast.LENGTH_SHORT).show()

                    if(response?.getString("result")?.contains("Succesfully")!!){
                        this@Profile.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString().toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })
        user.setText(usrnm)
        nama.setText(nm)
        almt.setText(alamat)
        gender.setText(kelamin)
        tgl.setText(tanggal)

    }

}