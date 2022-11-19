package com.example.techtask.model.services

import android.util.Log
import com.example.techtask.model.Group
import com.github.javafaker.Faker
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random

class GroupsService(
    private val database: FirebaseDatabase
) {
    init {
        val groupsReference = database.reference.child("Groups")
        groupsReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.hasChildren()) {
                    val faker = Faker.instance()
                    val generatedGroups: List<Group> = (1..20).map { Group(
                        id = it.toLong(),
                        number = "09-"+Random.nextInt(100, 999).toString(),
                        quantity = Random.nextLong(15, 30),
                        idTutor = Random.nextLong(1,20)
                    ) }
                    generatedGroups.forEach {
                        val id: String? = groupsReference.push().key
                        groupsReference.child(id!!).setValue(it)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("FAIL", "Failed to read value.", error.toException())
            }

        })
    }
}