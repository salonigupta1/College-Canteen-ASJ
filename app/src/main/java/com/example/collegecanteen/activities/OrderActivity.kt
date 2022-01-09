package com.example.collegecanteen.activities

//import android.content.Intent
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.collegecanteen.R
import com.google.firebase.firestore.FirebaseFirestore
//import com.google.firebase.firestore.ktx.firestore
//import com.google.firebase.ktx.Firebase

class OrderActivity : AppCompatActivity() {

    private  lateinit var actionBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        actionBar = supportActionBar!!
        actionBar.title = "Place Your Order"

//        order button
        val orderFinal = findViewById<View>(R.id.orderFinal) as Button
        orderFinal.setOnClickListener{
            val nameOrder = findViewById<View>(R.id.nameOrder) as EditText
            val addressOrder = findViewById<View>(R.id.addressOrder) as EditText
            val phoneOrder = findViewById<EditText>(R.id.phoneOrder)
            val name = nameOrder.text.toString()
            val address = addressOrder.text.toString()
            val phone = phoneOrder.text.toString()

            saveFirestore(name , address, phone)
        }

    }

    private fun saveFirestore(name: String, address: String, phone:String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = name
        user["address"] = address
        user["phone"] = phone

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@OrderActivity, "Order Placed Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, CategoryActivity::class.java))
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this@OrderActivity, "Order failed", Toast.LENGTH_SHORT).show()
            }
    }
}