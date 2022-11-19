package com.example.techtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.techtask.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.rootForTableFragment, TableFragment().also{
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.students))
                it.arguments = bundle
            })
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var fragment: Fragment? = null
        var intent: Intent? = null
        when(item.itemId) {
            R.id.students -> {
                fragment = TableFragment()
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.students))
                fragment.arguments = bundle
            }
            R.id.lecturers -> {
                fragment = TableFragment()
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.lecturers))
                fragment.arguments = bundle
            }
            R.id.departments -> {
                fragment = TableFragment()
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.departments))
                fragment.arguments = bundle
            }
            R.id.groups -> {
                fragment = TableFragment()
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.groups))
                fragment.arguments = bundle
            }
            R.id.dorms -> {
                fragment = TableFragment()
                val bundle = Bundle()
                bundle.putString("table_name", getString(R.string.dorms))
                fragment.arguments = bundle
            }
            R.id.firstQuery -> {
                intent = Intent(this, QueryActivity::class.java)
                intent.putExtra("query", getString(R.string.first_query))
            }
            R.id.secondQuery -> {
                intent = Intent(this, QueryActivity::class.java)
                intent.putExtra("query", getString(R.string.second_query))
            }
        }
        return if(fragment!=null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.rootForTableFragment, fragment)
                .commit()
            true
        } else if(intent!=null) {
            startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}