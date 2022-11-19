package com.example.techtask.model.services

import android.util.Log
import com.example.techtask.model.Lecturer
import com.github.javafaker.Faker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class LecturersService(
    private val database: FirebaseDatabase
) {
    init {
        val lecturersReference = database.reference.child("Lecturers")
        lecturersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()) {
                    val faker = Faker.instance()
                    val generatedLecturers: List<Lecturer> = (1..20).map { Lecturer(
                        id = it.toLong(),
                        name = faker.name().name(),
                        address = faker.address().fullAddress(),
                        salary = Random.nextLong(30000,100000),
                        idDepartment = Random.nextLong(1,20)
                    ) }
                    generatedLecturers.forEach {
                        val id: String? = lecturersReference.push().key
                        lecturersReference.child(id!!).setValue(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FAIL", "Failed to read value.", error.toException())
            }

        })
    }
}