package com.jayneel.otp_verification_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            startActivity(Intent(this,Dashbord::class.java))
            finish()
        }
        btnsendotp.setOnClickListener {
            var int1=Intent(this,Verification::class.java)
            int1.putExtra("num",edtphone.text.toString())
            startActivity(int1)
            finish()
        }
    }
}