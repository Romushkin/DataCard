package com.example.datacard

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.format.DateTimeFormatter

class DetailsActivity : AppCompatActivity() {
    private lateinit var toolbarMain: Toolbar
    private lateinit var imageViewIV: ImageView
    private lateinit var personNameTV: TextView
    private lateinit var personSurnameTV: TextView
    private lateinit var personDateTV: TextView
    private lateinit var personPhoneTV: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        toolbarMain = findViewById(R.id.toolbarMain)
        imageViewIV = findViewById(R.id.imageViewIV)
        personNameTV = findViewById(R.id.personNameTV)
        personSurnameTV = findViewById(R.id.personSurnameTV)
        personDateTV = findViewById(R.id.personDateTV)
        personPhoneTV = findViewById(R.id.personPhoneTV)

        setSupportActionBar(toolbarMain)

        val person = intent.extras?.getSerializable("person") as Person
        val image: Uri? = Uri.parse(person.photoUri)
        val age = person.getAge()
        val monthsAndDays = person.getMonthsAndDaysToNextBirthday()

        imageViewIV.setImageURI(image)
        personNameTV.text = person.name
        personSurnameTV.text = person.surname
        personDateTV.text = "Возраст: $age лет, до следующего дня рождения: $monthsAndDays"
        personPhoneTV.text = person.phone
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exitMenu) finishAffinity()
        return super.onOptionsItemSelected(item)
    }
}