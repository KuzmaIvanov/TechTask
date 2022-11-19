package com.example.techtask.model.services

import android.util.Log
import com.example.techtask.model.Department
import com.github.javafaker.Faker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class DepartmentsService(
    private val database: FirebaseDatabase
) {
    init {
        val departmentsReference = database.reference.child("Departments")
        departmentsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()) {
                    val faker = Faker.instance()
                    val generatedDepartments: List<Department> = (1..20).map { Department(
                        id = it.toLong(),
                        name = faker.witcher().school(),
                        idInstitute = Random.nextLong(1,20)
                    ) }
                    generatedDepartments.forEach {
                        val id: String? = departmentsReference.push().key
                        departmentsReference.child(id!!).setValue(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FAIL", "Failed to read value.", error.toException())
            }

        })
    }
}