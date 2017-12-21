package bugfix.todo

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import bugfix.todo.data.TaskContract
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import org.jetbrains.annotations.NotNull
import java.net.URI
import java.util.logging.Logger

class detail : AppCompatActivity() {

    companion object {
        var taskid: Long? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        var taskUri = intent?.data as Uri
        taskid = TaskContract.TaskEntry.getIdFromUri(taskUri)

        val cursor = contentResolver.query(taskUri, null, null, null)

        cursor.moveToFirst()

        val taskTitle = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TITLE))
        val taskDescr = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_DESCRIPTION))

        detailTitle.setText(taskTitle)
        detailDiscription.setText(taskDescr)

        btnDetailSave.setOnClickListener { view ->
            val strWhere = "${TaskContract.TaskEntry._ID} = ?"
            val content = ContentValues()
            var updated = 0
            content.put(TaskContract.TaskEntry.COL_TITLE, detailTitle.text.toString())
            content.put(TaskContract.TaskEntry.COL_DESCRIPTION, detailDiscription.text.toString())

            if (taskid!! >= 0L) {
                updated = contentResolver.update(TaskContract.TaskEntry.CONTENT_URI, content, strWhere, arrayOf(taskid.toString()))

            }

            if (updated == 0) {
//                Snackbar.make(findViewById(idDetail), "", Snackbar.LENGTH_SHORT).show()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when(id) {
            R.id.action_delete -> {
                Log.d("Delete item", "Delete item is id: ${taskid.toString()}")
                val deleted = contentResolver.delete(
                        TaskContract.TaskEntry.CONTENT_URI,
                        "${TaskContract.TaskEntry._ID} = ?",
                        arrayOf(taskid?.toString())
                )

                if (deleted == 1) {
                    startActivity((Intent(this, MainActivity::class.java)))
                }else{
                    Snackbar.make(findViewById(R.id.idDetail), "Не удалось удалить запись, код ошибки ${deleted}", Snackbar.LENGTH_SHORT).show()
                }

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
