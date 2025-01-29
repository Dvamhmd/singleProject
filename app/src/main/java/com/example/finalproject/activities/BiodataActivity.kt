package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject.adapter.BiodataModel
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BiodataActivity : AppCompatActivity() {

    private lateinit var namaDepanInput: EditText
    private lateinit var namaLengkapInput: EditText
    private lateinit var nomorTeleponInput: EditText
    private lateinit var buttonMulai: Button

    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_biodata)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.biodata)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        //inisialisasi firebase
        dbRef = FirebaseDatabase.getInstance().getReference("Biodata")
        auth = FirebaseAuth.getInstance()


        namaDepanInput = findViewById(R.id.nama_depan)
        namaLengkapInput = findViewById(R.id.nama_lengkap)
        nomorTeleponInput = findViewById(R.id.nomor_telepon)
        buttonMulai = findViewById(R.id.button_mulai)



        //melakukan saveBiodata dan navigasi ke halaman Home
        buttonMulai.setOnClickListener {
            saveBiodataToDatabase()
        }

    }

    //fungsi untuk mengirim nilai variabel ke database
    private fun saveBiodataToDatabase(){

        //transformasi
        val namaDepan = namaDepanInput.text.toString().trim()
        val namaLengkap = namaLengkapInput.text.toString().trim()
        val nomorTelepon = nomorTeleponInput.text.toString().trim()

        if (namaDepan.isEmpty()){
            namaDepanInput.error = "Isi dengan benar!"
            return
        }
        if (namaLengkap.isEmpty()){
            namaLengkapInput.error = "Isi dengan benar!"
            return
        }
        if (nomorTelepon.isEmpty()){
            nomorTeleponInput.error = "Isi dengan benar!"
            return
        }

        val userId = auth.currentUser?.uid



        if (userId != null) {
            val biodata = BiodataModel(namaDepan, namaLengkap, nomorTelepon)


            //simpan data berdasarkan user id
            dbRef.child(userId).setValue(biodata)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data berhasil ditambah", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }


        }

    }
}

