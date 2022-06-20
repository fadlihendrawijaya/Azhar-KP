package com.malikaproject.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.malikaproject.ecommerceapp.databinding.ActivityLoginBinding
import com.malikaproject.ecommerceapp.model.ResponseLogin
import com.malikaproject.ecommerceapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var binding : ActivityLoginBinding? = null
    lateinit var btndaftar : Button
    lateinit var btnmasuk : Button
    private var id : String = ""
    private var pass : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        btnmasuk = findViewById(R.id.kliksuk)
        binding!!.kliksuk.setOnClickListener {
            id = binding!!.etUsername.text.toString()
            pass = binding!!.etPw.text.toString()

            when {
                id == "" ->{
                    binding!!.etUsername.error = "Username tidak boleh kosong!"
                }
                pass == "" ->{
                    binding!!.etPw.error = "Password tidak boleh kosong!"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
                    binding!!.kliksuk.visibility = View.GONE
                    getData()
                }
            }
        }

        btndaftar = findViewById(R.id.daftar)
        btndaftar.setOnClickListener(this)

    }

    override fun onClick(v: View){

        when(v.id){
            R.id.daftar ->{
                val kedaftar = Intent(this@LoginActivity, FormActivity::class.java)
                startActivity(kedaftar)
            }
        }
    }

    private fun getData() {
        val api = RetrofitClient().getInstance()
        api.login(id, pass).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    if (response.body()?.response == true) {
                        binding!!.loading.visibility = View.GONE
                        binding!!.kliksuk.visibility = View.VISIBLE
                        val i = Intent(this@LoginActivity, MainActivity::class.java)
                            .apply {
                                putExtra("username", id)
                            }
                        startActivity(i)
                        finish()
                    } else {
                        binding!!.loading.visibility = View.GONE
                        binding!!.kliksuk.visibility = View.VISIBLE
                        Toast.makeText(
                            this@LoginActivity,
                            "Login gagal. Periksa kembali username dan password",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }else{
                    binding!!.loading.visibility = View.GONE
                    binding!!.kliksuk.visibility = View.VISIBLE
                    Toast.makeText(
                        this@LoginActivity,
                        "Login gagal. Terjadi kesalahan",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("Pesan Error", "${t.message}")
            }

        })
    }
}