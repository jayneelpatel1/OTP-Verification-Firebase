package com.jayneel.otp_verification_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_dashbord.*

class Dashbord : AppCompatActivity() {
private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord)
        auth= FirebaseAuth.getInstance()
        textView.text=auth.currentUser?.phoneNumber
        btnlogout.setOnClickListener {

            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }

}