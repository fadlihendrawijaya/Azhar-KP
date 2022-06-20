package com.malikaproject.ecommerceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Checkout : AppCompatActivity() {

    private lateinit var nama : TextView
    private lateinit var harga : TextView
    private lateinit var diskon : TextView
    private lateinit var pajak : TextView
    private lateinit var jumlah : TextView

    private lateinit var hom : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        nama = findViewById(R.id.jns)
        harga = findViewById(R.id.hrg)
        diskon = findViewById(R.id.dscnt)
        pajak = findViewById(R.id.ppn)
        jumlah = findViewById(R.id.jml)

        val i = intent
        var nmnya = intent.getStringExtra("namadaftar")
        val hrgnya = intent.getStringExtra("hargadaftar")
        val dscnya = intent.getStringExtra("diskoun")
        val ppnnya = intent.getStringExtra("pajaknya")
        val jmlnya = intent.getStringExtra("jumlahdaftar")

        var hello = intent.getStringExtra("Hai")
        var datanya = intent.getStringExtra("Profil")
        var usernih = intent.getStringExtra("Usrnya")
        var pwnya = intent.getStringExtra("Pwnya")
        var gender = intent.getStringExtra("gender")

        nama.text = nmnya
        harga.text = hrgnya
        diskon.text = dscnya
        pajak.text = ppnnya
        jumlah.text = jmlnya

        hom = findViewById(R.id.btm)
        hom.setOnClickListener {
            val masuk = Intent(this@Checkout, MainActivity::class.java)
                .apply {
                    putExtra("Profil", datanya)
                    putExtra("Hai", hello)
                    putExtra("Usrnya", usernih)
                    putExtra("Pwnya", pwnya)
                    putExtra("gender", gender)
                }
            startActivity(masuk)
        }

    }
}