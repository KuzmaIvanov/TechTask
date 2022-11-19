package com.example.techtask.model.services

import android.util.Log
import com.example.techtask.model.Dorm
import com.github.javafaker.Faker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class DormsService(
    private val database: FirebaseDatabase
) {
    init {
        val dormsReference = database.reference.child("Dorms")
        dormsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()) {
                    val faker = Faker.instance()
                    val generatedDorms: List<Dorm> = (1..20).map { Dorm(
                        id = it.toLong(),
                        name = "Dorm of "+faker.university().name(),
                        timeToUniHour = Random.nextDouble(0.5, 1.5)
                    ) }
                    generatedDorms.forEach {
                        val id: String? = dormsReference.push().key
                        dormsReference.child(id!!).setValue(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FAIL", "Failed to read value.", error.toException())
            }

        })
    }

}