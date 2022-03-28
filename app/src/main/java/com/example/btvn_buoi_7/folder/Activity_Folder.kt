package com.example.btvn_buoi_7.folder

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.btvn_buoi_7.folder.FolderModel
import android.widget.TextView
import com.example.btvn_buoi_7.folder.FolderAdapter
import com.example.btvn_buoi_7.database.DatabaseFolder
import android.os.Bundle
import com.example.btvn_buoi_7.R
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import com.example.btvn_buoi_7.folder.Activity_Folder
import android.app.Activity
import android.app.AlertDialog
import android.widget.Toast
import com.example.btvn_buoi_7.folder.Activity_Add_Edit_Folder
import android.content.DialogInterface
import android.view.View
import android.widget.Toolbar
import androidx.appcompat.widget.PopupMenu
import java.io.Serializable
import java.util.ArrayList

class Activity_Folder : AppCompatActivity() {
    var rcv_folder: RecyclerView? = null
    var folderList: MutableList<FolderModel> = ArrayList()
    var toolbar: Toolbar? = null
    var tv_add_item_toolbar: TextView? = null
    var adapter: FolderAdapter? = null
    var databaseFolder: DatabaseFolder? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_folder)
        initUI()
        databaseFolder = DatabaseFolder(this)
        adapter = FolderAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_folder!!.layoutManager = linearLayoutManager
        rcv_folder!!.adapter = adapter
        setToolbar()
        loadData()
        adapter!!.setData(folderList)
        tv_add_item_toolbar!!.setOnClickListener { toActivityAddItem() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            val name = data!!.extras!!.getString(NAME_FOLDER)
            val description = data.extras!!.getString(DESCRIPTION_FOLDER)
            val folder = FolderModel(name!!, description!!)
            databaseFolder!!.insertFolder(folder)
            loadData()
            Toast.makeText(this, getString(R.string.add_folder_successfully), Toast.LENGTH_SHORT)
                .show()
        } else if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK) {
            val bundle = data!!.extras
            val folder = bundle!!.getSerializable(ITEM_FOLDER) as FolderModel?
            databaseFolder!!.updateFolder(folder!!)
            loadData()
            Toast.makeText(this, getString(R.string.edit_folder_successfully), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initUI() {
        rcv_folder = findViewById(R.id.rcv_folder)
        toolbar = findViewById(R.id.toolbar)
        tv_add_item_toolbar = findViewById(R.id.tv_add_item_toolbar)
    }

    private fun toActivityAddItem() {
        val intent = Intent(this@Activity_Folder, Activity_Add_Edit_Folder::class.java)
        intent.putExtra(LIST_FOLDER, folderList as Serializable)
        intent.action = ACTION_ADD
        startActivityForResult(intent, REQUEST_CODE_ADD_ITEM)
    }

    private fun loadData() {
        folderList.clear()
        folderList.addAll(databaseFolder!!.allFolder)
        adapter!!.notifyDataSetChanged()
    }

    private fun setToolbar() {
        toolbar!!.setNavigationIcon(R.drawable.ic_back)
        toolbar!!.setNavigationOnClickListener { onBackPressed() }
        window.statusBarColor = getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    fun showPopupMenu(view: View?, folder: FolderModel) {
        val popup = PopupMenu(this, view!!)
        popup.inflate(R.menu.menu_option)
        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_delete -> {
                    showActionDialogDeleteItem(folder)
                    return@OnMenuItemClickListener true
                }
                R.id.menu_edit -> {
                    val intent = Intent(this@Activity_Folder, Activity_Add_Edit_Folder::class.java)
                    intent.putExtra(LIST_FOLDER, folderList as Serializable)
                    intent.putExtra(ITEM_FOLDER, folder)
                    intent.action = ACTION_EDIT
                    startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM)
                }
            }
            false
        })
        popup.show()
    }

    private fun showActionDialogDeleteItem(folder: FolderModel) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Bạn có muốn xóa folder này")
        dialog.setNegativeButton("Có") { dialogInterface, i ->
            databaseFolder!!.deleteFolder(folder)
            loadData()
        }
        dialog.setPositiveButton("Không") { dialogInterface, i -> }
        dialog.show()
    }

    companion object {
        const val REQUEST_CODE_ADD_ITEM = 123
        const val REQUEST_CODE_EDIT_ITEM = 1234
        const val ACTION_ADD = "add"
        const val ACTION_EDIT = "edit"
        const val ITEM_FOLDER = "item_folder"
        const val LIST_FOLDER = "list_folder"
        const val NAME_FOLDER = "name"
        const val DESCRIPTION_FOLDER = "description"
    }
}