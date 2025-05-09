package com.example.todolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val taskInput = findViewById<EditText>(R.id.editTextTask)

        taskInput.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN)) {

                val taskText = taskInput.text.toString().trim()
                if (taskText.isNotEmpty()) {
                    taskList.add(Task(taskText)) // Assuming you have a Task class
                    taskAdapter.notifyItemInserted(taskList.size - 1)
                    taskInput.text.clear()
                }
                true // consume the event
            } else {
                false // don't consume
            }
        }

        val editTextTask = findViewById<EditText>(R.id.editTextTask)
        val buttonAdd = findViewById<Button>(R.id.buttonAdd)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)

        taskAdapter = TaskAdapter(taskList) { position ->
            taskList.removeAt(position)
            taskAdapter.notifyItemRemoved(position)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = taskAdapter

        buttonAdd.setOnClickListener {
            val taskTitle = editTextTask.text.toString()
            if (taskTitle.isNotBlank()) {
                taskList.add(Task(taskTitle))
                taskAdapter.notifyItemInserted(taskList.size - 1)
                editTextTask.text.clear()
            }
        }
    }
}