package com.example.collegecanteen.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.collegecanteen.R
import com.google.android.material.navigation.NavigationView
import android.content.Intent
//import android.view.Menu
//import android.view.View
//import android.view.Menu
//import android.view.Menu
//import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class SnacksProductActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snacks_product)

        auth= FirebaseAuth.getInstance()
        val currentUser=auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this@SnacksProductActivity,PhoneAuth::class.java))
            finish()
        }

//        val varLogout = findViewById<Menu>(R.id.nav_logout)
//        varLogout.OnOptionsItemSelectedListener{
//            val intent = Intent(this@FastFoodProductActivity, PhoneAuth::class.java)
//            startActivity(intent)
//        }



//       fun onNavigationItemSelected(menuItem: MenuItem):Boolean{
//            when(menuItem.itemId){
//                R.id.nav_logout ->{
//                    val intent = Intent(this@FastFoodProductActivity, PhoneAuth::class.java)
//                     startActivity(intent)
//                }
//            }
//           return true
//        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(applicationContext, "Clicked Home", LENGTH_SHORT)
                    .show()
                R.id.order -> Toast.makeText(applicationContext, "Clicked Order", LENGTH_SHORT)
                    .show()
                R.id.setting -> Toast.makeText(applicationContext, "Clicked setting", LENGTH_SHORT)
                    .show()
//                R.id.nav_logout -> Toast.makeText(applicationContext, "Clicked Logout", LENGTH_SHORT)
//                    .show()
                R.id.nav_share -> Toast.makeText(applicationContext, "Clicked Share", LENGTH_SHORT)
                    .show()
                R.id.nav_rate_us -> Toast.makeText(
                    applicationContext,
                    "Clicked Rate Us",
                    LENGTH_SHORT
                ).show()
            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}