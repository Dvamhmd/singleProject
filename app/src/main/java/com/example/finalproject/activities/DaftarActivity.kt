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
import com.example.finalproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class DaftarActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.daftar_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val buttonDaftar = findViewById<Button>(R.id.button_daftar)
        val buttonMasuk = findViewById<Button>(R.id.button_masuk)

        //logika pendaftaran
        buttonDaftar.setOnClickListener {

            //transformasi ke string
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            //handle error jika input kosong
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //mendaftarkan akun dengan firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "Akun berhasil didaftarkan!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, BiodataActivity::class.java))
                        finish()

                    }else{
                        if (task.exception is FirebaseAuthUserCollisionException){
                            Toast.makeText(this, "Gagal! Email sudah terdaftar", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Email tidak valid!", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

        }

        //navigasi ke halaman masuk
        buttonMasuk.setOnClickListener {
            val intent = Intent(this, MasukActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}