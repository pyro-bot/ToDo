package bugfix.todo

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import bugfix.todo.data.TaskContract
import kotlinx.android.synthetic.main.activity_create_task.*

/**
 * Created by a_shicelov on 14.12.2017.
 */
class CreateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_create_task)

        val saveBtn = findViewById<Button>(R.id.save_task_btn)

        saveBtn.setOnClickListener { view ->
            val taskTitle = task_title.text.toString()
            val taskDiscription = task_description.text.toString()

            if (taskTitle.isEmpty() or taskDiscription.isEmpty() ) {
                Toast.makeText(applicationContext, getString(R.string.error_input_empty), Toast.LENGTH_LONG).show()
            } else {
                val values = ContentValues()
                values.put(TaskContract.TaskEntry.COL_TITLE, taskTitle)
                values.put(TaskContract.TaskEntry.COL_DESCRIPTION, taskDiscription)

                var inserted = contentResolver.insert(TaskContract.TaskEntry.CONTENT_URI, values)

                startActivity(Intent(this, MainActivity::class.java))

                Log.d("New Task", "Inserted: $inserted")
            }
        }
    }
}