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

class MasukActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_masuk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.masuk_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = FirebaseAuth.getInstance()


        //variable
        val emailInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val buttonMasuk = findViewById<Button>(R.id.button_masuk)
        val buttonDaftar = findViewById<Button>(R.id.button_daftar)


        //logika untuk masuk
        buttonMasuk.setOnClickListener {

            //transformasi ke string
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            //handle error jika input kosong
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //authentikasi dengan firebase
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login Berhasill!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Email atau Password salah!", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        //navigasi ke halaman pendaftaran
        buttonDaftar.setOnClickListener {
            val intent = Intent(this, DaftarActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}