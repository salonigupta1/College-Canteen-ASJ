package com.example.collegecanteen.activities

//import android.app.AlertDialog
//import android.app.ProgressDialog
//import android.app.ActionBar
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.collegecanteen.R
import com.example.collegecanteen.databinding.ActivityPhoneAuthBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseTooManyRequestsException

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException




class PhoneAuth : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    private lateinit var progressDialog:ProgressDialog
//    private lateinit var actionBar:ActionBar
    private lateinit var binding:ActivityPhoneAuthBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding.ActivityPhoneAuthBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_phone_auth)

//        actionBar = supportActionBar!!
//        actionBar.title = "login"


//        set up progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("please wait")
        progressDialog.setMessage("sending OTP...")
        progressDialog.setCanceledOnTouchOutside(false)


//        init firebase AUTH
        auth=FirebaseAuth.getInstance()
//        checkUser()


//        if user already logged in then directly move to category activity
        val login=findViewById<Button>(R.id.loginBtn)
        val currentUser = auth.currentUser
        if(currentUser != null) {
            startActivity(Intent(applicationContext, CategoryActivity::class.java))
            finish()
        }

//     on clicking login button , run function login()
        login.setOnClickListener{
            login()
        }

        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, CategoryActivity::class.java))
                finish()
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                progressDialog.dismiss()
                Log.d("TAG","onCodeSent:$verificationId")
                storedVerificationId = verificationId
                resendToken = token

                val intent = Intent(applicationContext, PhoneVerify::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Failed", Toast.LENGTH_LONG).show()
            }

        }

    }

//    login function
    public fun login() {
        val mobileNumber=findViewById<EditText>(R.id.phoneNumber)
        var number=mobileNumber.text.toString().trim()
            progressDialog.show()
        if(number.isNotEmpty()){
           number= "+91$number"
//            run the function sendVerificationcode(number)
            sendVerificationcode(number)
        }else{
            progressDialog.dismiss()
            Toast.makeText(this,"Enter mobile number",Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendVerificationcode(number: String) {
        progressDialog.show()
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}