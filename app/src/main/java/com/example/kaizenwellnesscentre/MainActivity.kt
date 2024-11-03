package com.example.kaizenwellnesscentre
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var patientName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var doctorName: AutoCompleteTextView
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        patientName = findViewById(R.id.patientsName)
        phoneNumber = findViewById(R.id.phoneNumber)
        doctorName = findViewById(R.id.doctorName)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)
        submitButton = findViewById(R.id.submitButton)

        // Sample list of doctors for AutoCompleteTextView
        val doctors = listOf("Dr. Al-Fayeed","Dr.Ashleigh","Dr.Snow")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, doctors)
        doctorName.setAdapter(adapter)

        submitButton.setOnClickListener {
            // Retrieve values
            val name = patientName.text.toString()
            val phone = phoneNumber.text.toString()
            val doctor = doctorName.text.toString()
            val day = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val time = "${timePicker.hour}:${timePicker.minute}"

            // Process or save the information
            Toast.makeText(this, "Booking Confirmed for $name phone number $phone with Dr. $doctor on $day at $time ", Toast.LENGTH_LONG).show()
        }
    }
}
