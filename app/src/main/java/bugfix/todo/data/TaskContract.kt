package bugfix.todo.data

import android.content.ContentResolver
import android.content.ContentUris
import android.net.Uri
import android.provider.BaseColumns
import java.net.URI
//Обшие описание контента для приложения
object TaskContract {
//    Кому принадлежит содержимое (полное название основного пакета)
    val CONTENT_AUTHORITY = "bugfix.todo"
//    Создание корневого URI для нашего приложения
    val BASE_CONTENT_URI: Uri = Uri.parse("content://${CONTENT_AUTHORITY}")
//    Часть пути относильно корня для получения списка задач, без знака / в начале и в конце
    val TASK_PATH = "${TaskEntry.TABLE_NAME}"

//    Описание отдельной сущности и таблицы
    object TaskEntry {

//    URI для получения списка всех задач
        val CONTENT_URI: Uri = Uri.parse("${BASE_CONTENT_URI.toString()}/${TASK_PATH}")
//    Тип контента
        val CONTENT_TYPE = "${ContentResolver.CURSOR_DIR_BASE_TYPE}/${CONTENT_AUTHORITY}/${TASK_PATH}"
//    Тип отдельного элимента
        val CONTENT_ITEM_TYPE = "${ContentResolver.CURSOR_ITEM_BASE_TYPE}/${CONTENT_AUTHORITY}/${TASK_PATH}"

//    Название таблице
        val TABLE_NAME = "tasks"

//    ===============================
//    Стандартные поля
//    Название поля ID
        val _ID = BaseColumns._ID // Y U NO implement?!
//    Название поля COUNT
        val _COUNT = BaseColumns._COUNT
//    ===============================
//    Название поля с названием задачи
        val COL_TITLE = "title"
//    Название поля с описанием задачи
        val COL_DESCRIPTION = "description"

//    Метода построения URI для нужного id и его получения с URI
        fun buildWithId(id: Long): Uri {
            return ContentUris.withAppendedId(CONTENT_URI, id)
        }

        fun getIdFromUri(uri: Uri): Long {
            return ContentUris.parseId(uri)
        }
    }
}