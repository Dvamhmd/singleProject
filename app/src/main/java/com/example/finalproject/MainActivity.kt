package com.example.finalproject


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //variable validasi
        val username = "dvamhmd"
        val password = "12345678"

        //variable
        val usernameInput = findViewById<EditText>(R.id.username)
        val passwordInput = findViewById<EditText>(R.id.password)
        val buttonMasuk = findViewById<Button>(R.id.button_masuk)


        //logika untuk masuk
        buttonMasuk.setOnClickListener {

            //transformasi ke string
            val inputUser = usernameInput.text.toString()
            val inputPassword = passwordInput.text.toString()

            if (inputUser == username && inputPassword == password){

                //pesan berhasil
                Toast.makeText(this, "Berhasil masuk", Toast.LENGTH_SHORT).show()

                //navigasi ke home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            }else{

                //pesan error
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show()
            }

        }


    }
}