package com.malikaproject.ecommerceapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var nyapaan : TextView
    lateinit var logot : Button
    lateinit var hom : Button
    lateinit var pfl : View
    lateinit var itm : View
    lateinit var ctc : View
    lateinit var infa : View
    lateinit var tampilan1 : LinearLayout
    lateinit var tampilan2 : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nyapaan = findViewById(R.id.nyapa)

        var username = intent.getStringExtra("username")

        var hello : String = "Selamat Datang "+username+"!"

        //set /tampilkan ke komponen view
        nyapaan.text = hello

        pfl = findViewById(R.id.gofil)
        pfl.setOnClickListener {
            val i = Intent(this@MainActivity, Profile::class.java)
                .apply {
                    putExtra("username", username)
                }
            startActivity(i)
        }
        itm = findViewById(R.id.dafit)
        itm.setOnClickListener {
            val i = Intent(this@MainActivity, ItemList::class.java)
                .apply {
                    putExtra("username", username)
                }
            startActivity(i)
        }
        infa = findViewById(R.id.infoapp)
        infa.setOnClickListener {
            onInfo()
        }
        hom = findViewById(R.id.gohom)
        hom.setOnClickListener {
            onHome()
        }

        logot = findViewById(R.id.klikout)
        logot.setOnClickListener(this)

        ctc = findViewById(R.id.ctcsrvc)
        ctc.setOnClickListener(this)

        tampilan1 = findViewById(R.id.grup1)
        tampilan2 = findViewById(R.id.grup2)

    }

    private fun onHome() {
        tampilan1.visibility = View.VISIBLE
        tampilan2.visibility = View.GONE
    }

    private fun onInfo() {
        tampilan1.visibility = View.GONE
        tampilan2.visibility = View.VISIBLE
    }

    override fun onClick(v: View){

        when(v.id){
            R.id.klikout ->{
                val keluar = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(keluar)
            }
        }

        when(v.id){
            R.id.ctcsrvc ->{
                val kontak : Intent = Uri.parse("https://www.instagram.com/historia.id").let { webpage ->
                    Intent(Intent.ACTION_VIEW, webpage)
                }
                startActivity(kontak)
            }
        }

    }
}