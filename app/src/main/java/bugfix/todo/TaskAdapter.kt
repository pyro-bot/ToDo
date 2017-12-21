package bugfix.todo

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView
import bugfix.todo.data.TaskContract

/**
 * Created by a_shicelov on 13.12.2017.
 */
class TaskAdapter(context: Context, cursor: Cursor?, flag:Int): CursorAdapter(context, cursor, flag) {
    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        return LayoutInflater.from(context).inflate(R.layout.task_listview_item, parent, false)
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        var titleTextView = view?.findViewById<TextView>(R.id.list_item_title)

        val TITLE_COL_INDEX = cursor?.getColumnIndex((TaskContract.TaskEntry.COL_TITLE)) as Int
        val taskTitle = cursor?.getString(TITLE_COL_INDEX)

        val ID = cursor?.getColumnIndex(TaskContract.TaskEntry._ID)
        val id = cursor?.getString(ID)

        Log.d("Load item:","\n" +
                "\tid: ${id}\n" +
                "\ttitle: ${taskTitle}\n")
        titleTextView?.text = taskTitle
    }

}