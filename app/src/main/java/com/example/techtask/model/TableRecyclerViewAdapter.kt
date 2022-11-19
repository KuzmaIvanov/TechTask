package com.example.techtask.model

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.techtask.databinding.ItemTableBinding
import com.google.gson.Gson

class TableRecyclerViewAdapter(
    private val tableName: String,
    private val items: List<Any>,
    private val context: Context
): RecyclerView.Adapter<TableRecyclerViewAdapter.TableItemViewHolder>(), View.OnClickListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTableBinding.inflate(inflater, parent, false)
        binding.root.setOnClickListener(this)
        return TableRecyclerViewAdapter.TableItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TableItemViewHolder, position: Int) {
        with(holder.binding) {
            when(tableName) {
                "Students" -> {
                    val item = items[position] as Student
                    holder.itemView.tag = item
                    firstTextView.text = item.name
                    secondTextView.text = item.address
                }
                "Lecturers" -> {
                    val item = items[position] as Lecturer
                    holder.itemView.tag = item
                    firstTextView.text = item.name
                    secondTextView.text = item.address
                }
                "Groups" -> {
                    val item = items[position] as Group
                    holder.itemView.tag = item
                    firstTextView.text = item.number
                    secondTextView.text = item.quantity.toString()
                }
                "Departments" -> {
                    val item = items[position] as Department
                    holder.itemView.tag = item
                    firstTextView.text = item.name
                    secondTextView.text = "Institute id: "+item.idInstitute.toString()
                }
                "Dorms" -> {
                    val item = items[position] as Dorm
                    holder.itemView.tag = item
                    firstTextView.text = item.name
                    secondTextView.text = "Time to uni is "+String.format("%.2f", item.timeToUniHour)+" hours"
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class TableItemViewHolder(
        val binding: ItemTableBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(p0: View?) {
        //showPopupMenu(p0!!)
        var item: Any? = null
        when(tableName) {
            "Students" -> {
                item = p0?.tag as Student
            }
            "Lecturers" -> {
                item = p0?.tag as Lecturer
            }
            "Groups" -> {
                item = p0?.tag as Group
            }
            "Departments" -> {
                item = p0?.tag as Department
            }
            "Dorms" -> {
                item = p0?.tag as Dorm
            }
        }
        val gson = Gson()
        val jsonString = gson.toJson(item)
        Toast.makeText(context, jsonString, Toast.LENGTH_SHORT).show()
    }
}