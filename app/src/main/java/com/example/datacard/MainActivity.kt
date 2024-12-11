package com.example.datacard

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val GALLERY_REQUEST = 302
    var photoUri: Uri? = null
    private val calendar = Calendar.getInstance()

    private lateinit var editImageViewIV: ImageView
    private lateinit var personNameET: EditText
    private lateinit var personSurnameET: EditText
    private lateinit var personDateTV: TextView
    private lateinit var datePickerBTN: Button
    private lateinit var personPhoneET: EditText
    private lateinit var saveBTN: Button

    @SuppressLint("MissingInflatedId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        editImageViewIV = findViewById(R.id.editImageViewIV)
        personNameET = findViewById(R.id.personNameET)
        personSurnameET = findViewById(R.id.personSurnameET)
        personDateTV = findViewById(R.id.personDateTV)
        datePickerBTN = findViewById(R.id.datePickerBTN)
        personPhoneET = findViewById(R.id.personPhoneET)
        saveBTN = findViewById(R.id.saveBTN)

        datePickerBTN.setOnClickListener {
            showDatePicker()
        }

        editImageViewIV.setOnClickListener{
            val imagePickerIntent = Intent(Intent.ACTION_PICK)
            imagePickerIntent.type = "image/*"
            startActivityForResult(imagePickerIntent, GALLERY_REQUEST)
        }

        saveBTN.setOnClickListener {
            if (personNameET.text.isEmpty() || personSurnameET.text.isEmpty() || personDateTV.text.isEmpty() || personPhoneET.text.isEmpty()) return@setOnClickListener
            val name = personNameET.text.toString()
            val surname = personSurnameET.text.toString()
            val dateText = personDateTV.text.toString().removePrefix("Дата рождения: ").trim()
            val date = LocalDate.parse(dateText, DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            val phone = personPhoneET.text.toString()
            val image = photoUri.toString()
            val person = Person(name, surname, date, phone, image)
            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra("person", person)
            startActivity(intent)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageViewIV = findViewById(R.id.editImageViewIV)
        when (requestCode) {
            GALLERY_REQUEST -> if (resultCode === RESULT_OK) {
                photoUri = data?.data
                editImageViewIV.setImageURI(photoUri)
            }
        }
    }
    @SuppressLint("SetTextI18n")
    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this, {DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                personDateTV.text = "Дата рождения: $formattedDate"
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}