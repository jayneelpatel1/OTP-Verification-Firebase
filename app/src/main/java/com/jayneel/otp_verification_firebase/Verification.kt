package com.jayneel.otp_verification_firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verification.*
import java.util.concurrent.TimeUnit

class Verification : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private var verificationInProgress = false
    private var storedVerificationId: String? = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        var no=intent.getStringExtra("num")

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            no, // Phone number to verify
            60, // Timeout duration
            TimeUnit.SECONDS, // Unit of timeout
            this, // Activity (for callback binding)
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                    // This callback is invoked in an invalid request for verification is made,
                    // for instance if the the phone number format is not valid.
                    Log.d("dag", "onVerificationFailed", e)

                    if (e is FirebaseAuthInvalidCredentialsException) {
                        Log.d("dag", "onInvidif", e)

                        // Invalid request
                        // ...
                    } else if (e is FirebaseTooManyRequestsException) {
                        // The SMS quota for the project has been exceeded
                        // ...
                        Log.d("dag", "onmoney", e)
                    }

                    // Show a message and update the UI
                    // ...
                }

                override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken
                ) {
                    // The SMS verification code has been sent to the provided phone number, we
                    // now need to ask the user to enter the code and then construct a credential
                    // by combining the code with a verification ID.
                    Log.d("dag", "onCodeSent:$verificationId")
                    Toast.makeText(this@Verification,"faild",Toast.LENGTH_LONG).show()


                    // Save verification ID and resending token so we can use them later
                    storedVerificationId = verificationId
                    resendToken = token

                    // ...
                }
            }) // OnVerificationStateChangedCallbacks
        btnsignin.setOnClickListener {

                // [START verify_with_code]
                val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, editTextCode.text.toString())
                // [END verify_with_code]
                signInWithPhoneAuthCredential(credential)
        }



    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth=FirebaseAuth.getInstance()
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("dag", "signInWithCredential:success")
                    Toast.makeText(this,"Verify",Toast.LENGTH_LONG).show()
                    val user = task.result?.user
                    // [START_EXCLUDE]
                    startActivity(Intent(this,Dashbord::class.java))
                    //updateUI(STATE_SIGNIN_SUCCESS, user)
                    // [END_EXCLUDE]
                } else {
                    // Sign in failed, display a message and update the UI
                   // Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        // [START_EXCLUDE silent]
                     //   binding.fieldVerificationCode.error = "Invalid code."
                        // [END_EXCLUDE]
                    }
                    // [START_EXCLUDE silent]
                    // Update UI
                   // updateUI(STATE_SIGNIN_FAILED)
                    // [END_EXCLUDE]
                }
            }

    }
}

