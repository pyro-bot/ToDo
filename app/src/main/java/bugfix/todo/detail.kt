package bugfix.todo

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import bugfix.todo.data.TaskContract

import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.*
import java.net.URI

class detail : AppCompatActivity() {

    companion object {
        var taskId = 0L
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        var taskUri = intent?.data as Uri
        detail.taskId = TaskContract.TaskEntry.getIdFromUri(taskUri)

        val cursor = contentResolver.query(taskUri, null, null, null)

        cursor.moveToFirst()

        val taskTitle = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TITLE))
        val taskDescr = cursor.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_DESCRIPTION))

        detailTitle.text = taskTitle
        detailDiscription.text = taskDescr

    }

}
