package bugfix.todo

import android.content.CursorLoader
import android.content.Intent
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import bugfix.todo.data.TaskContract

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), android.app.LoaderManager.LoaderCallbacks<Cursor> {

    companion object {
        val TASK_LOADER = 0
        var taskAdapter: TaskAdapter? = null
        var listView: ListView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        loaderManager.initLoader(TASK_LOADER, null, this)

        taskAdapter = TaskAdapter(applicationContext, null, 0)

        listView = task_listview

        listView?.adapter = taskAdapter



        fab.setOnClickListener { view ->
            startActivity(Intent(applicationContext, AddTask::class.java))
        }

        listView?.setOnItemClickListener { parent, view, position, id ->
            val currentTask: Cursor? = parent.getItemAtPosition(position) as Cursor

            var detailIntent = Intent(this, detail::class.java)
            val TASK_ID_COL = currentTask?.getColumnIndex(TaskContract.TaskEntry._ID) as Int
            val _id = currentTask?.getLong(TASK_ID_COL)
            val taskUri = TaskContract.TaskEntry.buildWithId(_id)

            detailIntent.setData(taskUri)
            startActivity(detailIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        taskAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(p0: Loader<Cursor>?) {
        taskAdapter?.swapCursor(null)
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        return CursorLoader(applicationContext, TaskContract.TaskEntry.CONTENT_URI, null, null, null, null)
    }
}
