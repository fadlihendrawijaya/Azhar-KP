package com.malikaproject.ecommerceapp

class ApiEndPoint {
    companion object{
        val SERVER = "http://192.168.7.120/coffeeshop/"
        val CUSER = SERVER+"create.php"
        val RUSER = SERVER+"read.php"
        val UUSER = SERVER+"update.php"
        val DUSER = SERVER+"delete.php"
        val CBARANG = SERVER+"barang/create.php"
        val REARANG = SERVER+"barang/read.php"
        val UBARANG = SERVER+"barang/update.php"
        val DBARANG = SERVER+"barang/delete.php"
    }
}