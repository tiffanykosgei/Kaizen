package com.example.kaizenwellnesscentre
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import android.util.Log
import android.content.ContentValues.TAG
import com.google.firebase.database.DatabaseError


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

        val firebase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value: String? = dataSnapshot.getValue(String::class.java)
                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


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

        data class Booking(
            val patientName: String,
            val phoneNumber: String,
            val doctorName: String,
            val date: String,
            val time: String
        )


        submitButton.setOnClickListener {
            // Retrieve values from the form
            val name = patientName.text.toString()
            val phone = phoneNumber.text.toString()
            val doctor = doctorName.text.toString()
            val day = "${datePicker.dayOfMonth}-${datePicker.month + 1}-${datePicker.year}"
            val time = "${timePicker.hour}:${timePicker.minute}"

            // Check if any field is empty
            if (name.isEmpty() || phone.isEmpty() || doctor.isEmpty()) {
                Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create a Booking object
            val booking = Booking(name, phone, doctor, day, time)

            // Reference to the "bookings" node in Firebase Realtime Database
            val bookingsRef = database.getReference("bookings").push() // Use push() to create a unique ID

            // Save the booking data to Firebase
            bookingsRef.setValue(booking)
                .addOnSuccessListener {
                    Toast.makeText(this, "Booking saved successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.e(TAG, "Failed to save booking", e)
                    Toast.makeText(this, "Failed to save booking", Toast.LENGTH_SHORT).show()
                }

        Toast.makeText(this, "Booking Confirmed for $name phone number $phone with Dr. $doctor on $day at $time ", Toast.LENGTH_LONG).show()
        }
    }
}
