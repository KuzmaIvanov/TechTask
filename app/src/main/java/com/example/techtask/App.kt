package com.example.techtask

import android.app.Application
import com.example.techtask.model.services.*
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class App: Application() {

    lateinit var studentsService: StudentsService
    lateinit var lecturersService: LecturersService
    lateinit var groupsService: GroupsService
    lateinit var dormsService: DormsService
    lateinit var departmentsService: DepartmentsService

    override fun onCreate() {
        super.onCreate()
        val database = Firebase.database("https://techtask-3941c-default-rtdb.europe-west1.firebasedatabase.app/")
        studentsService = StudentsService(database)
        lecturersService = LecturersService(database)
        groupsService = GroupsService(database)
        dormsService = DormsService(database)
        departmentsService = DepartmentsService(database)
    }
}