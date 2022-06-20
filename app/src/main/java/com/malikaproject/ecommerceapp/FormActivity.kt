package com.malikaproject.ecommerceapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.ImageReader
import android.media.ImageWriter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.android.material.datepicker.MaterialDatePicker
import com.malikaproject.ecommerceapp.databinding.ActivityMainBinding
import org.json.JSONObject
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker

import android.graphics.drawable.ColorDrawable




class FormActivity : AppCompatActivity(), View.OnClickListener {

    //edit text
    lateinit var edtNama : EditText
    lateinit var edtusr : EditText
    lateinit var etAlamat : EditText
    lateinit var edtPw : EditText
    lateinit var edtTgllhr : EditText

    //radio grup
    lateinit var rgJenisKelamin : RadioGroup
    lateinit var rbL : RadioButton
    lateinit var rbP : RadioButton

    //button
    lateinit var btnlogin : Button
    lateinit var btndaftar : Button

    private var mDateSetListener: OnDateSetListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        edtNama = findViewById(R.id.ed_nama)
        edtusr = findViewById(R.id.etUsername)
        etAlamat = findViewById(R.id.etalamat)
        edtPw = findViewById(R.id.etPw)
        edtTgllhr = findViewById(R.id.ed_tgllhr)

        rgJenisKelamin = findViewById(R.id.rb_jk)

        rbL = findViewById(R.id.l)
        rbP = findViewById(R.id.p)

        btndaftar = findViewById(R.id.kliktar)
        btndaftar.setOnClickListener {
            onCreatePengguna()
        }

        btnlogin = findViewById(R.id.login)
        btnlogin.setOnClickListener(this)

        edtTgllhr.setOnClickListener {
            val cal = Calendar.getInstance()
            val year = cal[Calendar.YEAR]
            val month = cal[Calendar.MONTH]
            val day = cal[Calendar.DAY_OF_MONTH]
            val dialog = DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }

        mDateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                Log.d(TAG, "onDateSet: mm/dd/yyy: $month/$day/$year")
                val date = "$day/$month/$year"
                edtTgllhr.setText(date)
            }

    }

    override fun onClick(v: View){

        when(v.id){
            R.id.login ->{
                val kelogin = Intent(this@FormActivity, LoginActivity::class.java)
                startActivity(kelogin)
            }
        }

    }

    private fun onCreatePengguna() {
        val loading = ProgressDialog(this)
        loading.setMessage("Adding Data...")
        loading.show()
        val username : String = edtusr.text.toString()
        val intSelectedRb  = rgJenisKelamin.checkedRadioButtonId
        val radiobutton = findViewById<RadioButton>(intSelectedRb)
        var jenisKelamin = radiobutton.text.toString()


        AndroidNetworking.post(ApiEndPoint.CUSER)
            .addBodyParameter("id",edtusr.text.toString())
            .addBodyParameter("nama", edtNama.text.toString())
            .addBodyParameter("alamat", etAlamat.text.toString())
            .addBodyParameter("tl", edtTgllhr.text.toString())
            .addBodyParameter("pass", edtPw.text.toString())
            .addBodyParameter("jns_klm", jenisKelamin)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()
                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@FormActivity.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ON ERROR", anError?.errorDetail?.toString().toString())
                    Toast.makeText(applicationContext, "FAILURE check log", Toast.LENGTH_SHORT).show()
                }

            })
        val i = Intent(this, MainActivity::class.java)
            .apply {
                putExtra("username", username)
            }
        startActivity(i)
    }

}