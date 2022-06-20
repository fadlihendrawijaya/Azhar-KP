package com.malikaproject.ecommerceapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RVAdapterBarang (private val context: Context, private val arrayList: ArrayList<Barang>)
    : RecyclerView.Adapter<RVAdapterBarang.ViewHolder>(){
    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val tvId : TextView
        val tvNama : TextView
        val tvHarga : TextView
        val tvKategori : TextView
        val tvDetail : TextView
        val chkbx : CheckBox
        val btnedt : View
        val jmlb1 : EditText
        val spinn : Spinner

        init {
            tvId = view.findViewById(R.id.id_brg)
            tvNama = view.findViewById(R.id.namabrg)
            tvHarga = view.findViewById(R.id.hargabrg)
            tvKategori = view.findViewById(R.id.kategori)
            tvDetail = view.findViewById(R.id.detail)
            chkbx = view.findViewById(R.id.check)
            btnedt = view.findViewById(R.id.edit)
            jmlb1 = view.findViewById(R.id.jml)
            spinn = view.findViewById(R.id.spin)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvId.text = this.arrayList?.get(position)?.id.toString()
        holder.tvNama.text = this.arrayList?.get(position)?.nama.toString()
        holder.tvHarga.text = this.arrayList?.get(position)?.harga.toString()
        holder.tvKategori.text = this.arrayList?.get(position)?.kategori.toString()
        holder.tvDetail.text = this.arrayList?.get(position)?.detail.toString()

        val i = Intent()
        if(i.hasExtra("username")){

            if(i.getStringExtra("username").equals("Admin")){

                holder.chkbx.visibility = View.GONE
                holder.jmlb1.visibility = View.GONE
                holder.btnedt.visibility = View.VISIBLE

            }

        }


        holder.btnedt.setOnClickListener{
            val itemName = arrayList?.get(position)?.nama.toString()
            val i = Intent(context,ManageActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.putExtra("edit","1")
            i.putExtra("id",arrayList?.get(position)?.id)
            i.putExtra("nama",arrayList?.get(position)?.nama)
            i.putExtra("harga",arrayList?.get(position)?.harga)
            i.putExtra("kategori",arrayList?.get(position)?.kategori)
            i.putExtra("detail",arrayList?.get(position)?.detail)
            context.startActivity(i)
            Toast.makeText(context, "Anda memilih : $itemName", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = arrayList.size
}