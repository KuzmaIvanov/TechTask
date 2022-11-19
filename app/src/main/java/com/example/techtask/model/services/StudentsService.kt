package com.example.techtask.model.services

import android.util.Log
import com.example.techtask.model.Student
import com.github.javafaker.Faker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class StudentsService(
    private val database: FirebaseDatabase
) {
    init {
        val studentsReference = database.reference.child("Students")
        studentsReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()) {
                    val faker = Faker.instance()
                    val generatedStudents: List<Student> = (1..20).map { Student(
                        id = it.toLong(),
                        name = faker.name().name(),
                        address = faker.address().fullAddress(),
                        idGroup = Random.nextLong(1,20),
                        idScientificAdvisor = Random.nextLong(1,20),
                        idDorm = Random.nextLong(1,20)
                    ) }
                    generatedStudents.forEach {
                        val id: String? = studentsReference.push().key
                        studentsReference.child(id!!).setValue(it)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w("FAIL", "Failed to read value.", error.toException())
            }

        })
    }
}