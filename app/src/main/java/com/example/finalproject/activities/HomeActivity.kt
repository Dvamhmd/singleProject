package com.example.finalproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var namaDepanInput: TextView
    private lateinit var namaLengkapInput: TextView
    private lateinit var nomorHpInput: TextView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //inisialisasi Firebase Auth dan Database Reference
        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("Biodata")

        namaDepanInput = findViewById(R.id.nama_depan_input)
        namaLengkapInput = findViewById(R.id.nama_lengkap_input)
        nomorHpInput = findViewById(R.id.nomor_hp_input)

        getDataFromFirebase()

        //log out
        findViewById<Button>(R.id.kembali).setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MasukActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun getDataFromFirebase() {

        val userId = auth.currentUser?.uid

        if (userId != null) {
            dbRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {

                        val namaDepan = snapshot.child("namaDepan").getValue(String::class.java) ?: "Data kosong"
                        val namaLengkap = snapshot.child("namaLengkap").getValue(String::class.java) ?: "Data kosong"
                        val nomorHp = snapshot.child("nomorTelepon").getValue(String::class.java) ?: "Data kosong"

                        namaDepanInput.text = namaDepan
                        namaLengkapInput.text = namaLengkap
                        nomorHpInput.text = nomorHp

                    } else {
                        Toast.makeText(this@HomeActivity, "Data tidak tersedia", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@HomeActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }



}