package com.example.btvn_buoi_7.database

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.example.btvn_buoi_7.database.DatabaseFolder
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.btvn_buoi_7.folder.FolderModel
import java.util.ArrayList

class DatabaseFolder(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        val sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " VACHAR(200)," +
                KEY_DESCRIPTION + "  VACHAR(200) )"
        sqLiteDatabase.execSQL(sql)
        Log.d("AAA", "create table")
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {}
    val allFolder: List<FolderModel>
        get() {
            val listFolder: MutableList<FolderModel> = ArrayList()
            val db = readableDatabase
            val data = db.rawQuery("SELECT * FROM " + TABLE_NAME + "", null)
            while (data.moveToNext()) {
                val id = data.getInt(data.getColumnIndex(KEY_ID))
                val name = data.getString(data.getColumnIndex(KEY_NAME))
                val description = data.getString(data.getColumnIndex(KEY_DESCRIPTION))
                listFolder.add(FolderModel(id, name, description))
            }
            return listFolder
        }

    fun insertFolder(folder: FolderModel) {
        val db = writableDatabase
        //db.execSQL("INSERT INTO "+TABLE_NAME+"(name,description) VALUES (?,?)",new String[]{folder.getName(),folder.getDescription()});
        db.execSQL("INSERT INTO folder(name,description) VALUES ('" + folder.name + "','" + folder.description + "')")
        Log.d("AAA", "thêm mới dữ liệu thành công")
    }

    fun updateFolder(folder: FolderModel) {
        val db = writableDatabase
        db.execSQL(
            "UPDATE " + TABLE_NAME + " SET " +
                    KEY_NAME + " = ?," +
                    KEY_DESCRIPTION + " = ? WHERE " +
                    KEY_ID + " = ?", arrayOf(folder.name, folder.description, folder.id.toString())
        )
    }

    fun deleteFolder(folder: FolderModel) {
        val db = writableDatabase
        val sql = "DELETE FROM " + TABLE_NAME + " WHERE " + KEY_ID + "=? "
        db.execSQL(sql, arrayOf<String>(folder.id.toString()))
    }

    companion object {
        private const val DATABASE_NAME = "myfolder.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "folder"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
    }
}