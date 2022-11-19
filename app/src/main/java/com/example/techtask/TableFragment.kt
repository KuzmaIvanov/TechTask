package com.example.techtask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.techtask.databinding.FragmentTableBinding
import com.example.techtask.model.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TableFragment : Fragment() {

    private var _binding: FragmentTableBinding? = null
    private val binding get() = _binding!!
    private lateinit var tableRecyclerViewAdapter: TableRecyclerViewAdapter
    private val database = Firebase.database("https://techtask-3941c-default-rtdb.europe-west1.firebasedatabase.app/")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTableBinding.inflate(inflater, container, false)
        val tableName = arguments?.getString("table_name", "")
        val layoutManager = LinearLayoutManager(requireContext())
        binding.tableRecyclerView.layoutManager = layoutManager
        val items = mutableListOf<Any>()
        tableRecyclerViewAdapter = TableRecyclerViewAdapter(tableName!!,items, requireContext())
        binding.tableRecyclerView.adapter = tableRecyclerViewAdapter
        when(tableName) {
            "Students" -> {
                items.clear()
                val studentsReference = database.reference.child("Students")
                studentsReference.get().addOnSuccessListener {
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val address = data.child("address").value as String
                        val idGroup = data.child("idGroup").value as Long
                        val idScientificAdvisor = data.child("idScientificAdvisor").value as Long
                        val idDorm = data.child("idDorm").value as Long
                        items.add(Student(id,name, address, idGroup, idScientificAdvisor, idDorm))
                    }
                    tableRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
            "Lecturers" -> {
                items.clear()
                val lecturersReference = database.reference.child("Lecturers")
                lecturersReference.get().addOnSuccessListener {
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val address = data.child("address").value as String
                        val salary = data.child("salary").value as Long
                        val idDepartment = data.child("idDepartment").value as Long
                        items.add(Lecturer(id, name, address, salary, idDepartment))
                    }
                    tableRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
            "Groups" -> {
                items.clear()
                val groupsReference = database.reference.child("Groups")
                groupsReference.get().addOnSuccessListener {
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val number = data.child("number").value as String
                        val quantityStudents = data.child("quantity").value as Long
                        val idTutor = data.child("idTutor").value as Long
                        items.add(Group(id, number, quantityStudents, idTutor))
                    }
                    tableRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
            "Departments" -> {
                items.clear()
                val departmentsReference = database.reference.child("Departments")
                departmentsReference.get().addOnSuccessListener {
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val idInstitute = data.child("idInstitute").value as Long
                        items.add(Department(id, name, idInstitute))
                    }
                    tableRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
            "Dorms" -> {
                items.clear()
                val dormsReference = database.reference.child("Dorms")
                dormsReference.get().addOnSuccessListener {
                    it.children.forEach{ data ->
                        val id = data.child("id").value as Long
                        val name = data.child("name").value as String
                        val timeToUni = data.child("timeToUniHour").value as Double
                        items.add(Dorm(id, name, timeToUni))
                    }
                    tableRecyclerViewAdapter.notifyDataSetChanged()
                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}