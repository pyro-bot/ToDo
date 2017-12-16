package bugfix.todo

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import bugfix.todo.data.TaskContract

import kotlinx.android.synthetic.main.activity_add_task.*
import kotlinx.android.synthetic.main.activity_add_task.view.*

//import kotlinx.android.synthetic.main.activity_create_task.*

class AddTask : AppCompatActivity() {

    var clearTitle = false
    var clearDisc = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        setSupportActionBar(toolbar)

        btnAdd.setOnClickListener { view ->
            val taskTitle = subTitle.text.toString()
            val taskDiscription = TaskDiscription.text.toString()

            if (taskText.text.isEmpty() or TaskDiscription.text.isEmpty()) {
                Toast.makeText(applicationContext, getString(R.string.error_input_empty), Toast.LENGTH_LONG).show()
            } else {
                val values = ContentValues()
                values.put(TaskContract.TaskEntry.COL_TITLE, taskText.text.toString())
                values.put(TaskContract.TaskEntry.COL_DESCRIPTION, TaskDiscription.text.toString())

                var inserted = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, values)

                startActivity(Intent(this, MainActivity::class.java))

                Log.d("New Task", "Inserted: $inserted")
            }
        }

        taskText.setOnFocusChangeListener { view, b ->
            if (b and !clearTitle) {
                view.taskText.text.clear()
                clearTitle = true
            }
        }

        TaskDiscription.setOnFocusChangeListener{view, b ->
            if (b and !clearDisc) {
                view.TaskDiscription.text.clear()
                clearDisc = true
            }
        }

    }
}
