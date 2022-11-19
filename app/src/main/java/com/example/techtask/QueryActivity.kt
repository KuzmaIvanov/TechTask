package com.example.techtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.techtask.databinding.ActivityQueryBinding
import com.example.techtask.model.Dorm
import com.example.techtask.model.Student
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class QueryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQueryBinding
    private val database = Firebase.database("https://techtask-3941c-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQueryBinding.inflate(layoutInflater)

        when(intent.getStringExtra("query")) {
            "First Query" -> {
                binding.descriptionTextView.text = getString(R.string.first_query_description)
                database.reference.child("Students").get().addOnSuccessListener {
                    val tempList = mutableListOf<Student>()
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val address = data.child("address").value as String
                        val idGroup = data.child("idGroup").value as Long
                        val idScientificAdvisor = data.child("idScientificAdvisor").value as Long
                        val idDorm = data.child("idDorm").value as Long
                        if(idScientificAdvisor>5 && idDorm<10)
                            tempList.add(Student(id,name, address, idGroup, idScientificAdvisor, idDorm))
                    }
                    val gson = Gson()
                    val element = gson.toJson(tempList)
                    binding.queryResultTextView.text = element.toString()
                }

            }
            "Second Query" -> {
                binding.descriptionTextView.text = getString(R.string.second_query_description)
                database.reference.child("Dorms").get().addOnSuccessListener {
                    val tempList = mutableListOf<Dorm>()
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val timeToUni = data.child("timeToUniHour").value as Double
                        if(timeToUni<1)
                            tempList.add(Dorm(id, name, timeToUni))
                    }
                    val gson = Gson()
                    val element = gson.toJson(tempList)
                    binding.queryResultTextView.text = element.toString()
                }
            }
        }

        setContentView(binding.root)
    }
}