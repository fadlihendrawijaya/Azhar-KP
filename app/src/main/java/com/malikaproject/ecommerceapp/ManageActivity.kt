package com.malikaproject.ecommerceapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
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
import org.json.JSONObject

class ManageActivity : AppCompatActivity() {
    private lateinit var edharga : EditText
    private lateinit var edkategori : Spinner
    private lateinit var eddetail : EditText
    private lateinit var ednama : EditText
    private lateinit var edid : TextView
    private lateinit var btnmng : Button
    private lateinit var btnhps : Button
    private lateinit var btnupd : Button
    private lateinit var i: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage)

        edkategori = findViewById(R.id.ed_kategori)
        eddetail = findViewById(R.id.ed_detail)
        edharga = findViewById(R.id.ed_harga)
        ednama = findViewById(R.id.ed_nama)
        edid = findViewById(R.id.ed_id)
        btnmng = findViewById(R.id.btn_manage)
        btnhps = findViewById(R.id.btnDelete)
        btnupd = findViewById(R.id.btnUpdate)


        i = intent

        if(i.hasExtra("edit")){

            if(i.getStringExtra("edit").equals("1")){

                onEditBarang()

            }

        }

        btnmng.setOnClickListener {
            onCreateBarang()
        }
        btnupd.setOnClickListener {
            onUpdateBarang()
        }

        btnhps.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage("Hapus data ini ?")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface, i ->
                    onDeleteBarang()
                })
                .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }
    }

    private fun onEditBarang() {
        edid.setText(i.getStringExtra("id"))
        ednama.setText(i.getStringExtra("nama"))
        edharga.setText(i.getStringExtra("harga"))
        eddetail.setText(i.getStringExtra("detail"))

        btnmng.visibility = View.GONE
        btnupd.visibility = View.VISIBLE
        btnhps.visibility = View.VISIBLE
        edid.visibility = View.VISIBLE
    }

    private fun onUpdateBarang(){

        val loading = ProgressDialog(this)
        loading.setMessage("Mengubah data...")
        loading.show()
        val kategorinya : String = edkategori.selectedItem.toString()

        AndroidNetworking.post(ApiEndPoint.UBARANG)
            .addBodyParameter("id",edid.text.toString())
            .addBodyParameter("nama", ednama.text.toString())
            .addBodyParameter("harga", edharga.text.toString())
            .addBodyParameter("kategori", kategorinya)
            .addBodyParameter("detail", eddetail.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString().toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }

    private fun onDeleteBarang(){

        val loading = ProgressDialog(this)
        loading.setMessage("Menghapus data...")
        loading.show()
        val kategorinya : String = edkategori.selectedItem.toString()

        AndroidNetworking.post(ApiEndPoint.DBARANG)
            .addBodyParameter("id",edid.text.toString())
            .addBodyParameter("nama", ednama.text.toString())
            .addBodyParameter("harga", edharga.text.toString())
            .addBodyParameter("kategori", kategorinya)
            .addBodyParameter("detail", eddetail.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext,response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if(response?.getString("message")?.contains("successfully")!!){
                        this@ManageActivity.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR", anError?.errorDetail?.toString().toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()                    }


            })

    }

    private fun onCreateBarang() {
        val loading = ProgressDialog(this)
        loading.setMessage("Adding Data...")
        loading.show()
        val kategorinya : String = edkategori.selectedItem.toString()

        AndroidNetworking.post(ApiEndPoint.CBARANG)
            .addBodyParameter("id",edid.text.toString())
            .addBodyParameter("nama", ednama.text.toString())
            .addBodyParameter("harga", edharga.text.toString())
            .addBodyParameter("kategori", kategorinya)
            .addBodyParameter("detail", eddetail.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@ManageActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ON ERROR", anError?.errorDetail?.toString().toString())
                    Toast.makeText(applicationContext, "FAILURE check log", Toast.LENGTH_SHORT).show()
                }

            })
        var username = intent.getStringExtra("username")
        val i = Intent(this, ItemList::class.java)
            .apply {
                putExtra("username", username)
            }
        startActivity(i)
    }
}