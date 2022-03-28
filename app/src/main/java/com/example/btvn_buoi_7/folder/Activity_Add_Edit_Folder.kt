package com.example.btvn_buoi_7.folder

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.widget.EditText
import com.example.btvn_buoi_7.folder.FolderModel
import android.os.Bundle
import com.example.btvn_buoi_7.R
import com.example.btvn_buoi_7.folder.Activity_Folder
import android.widget.Toast
import android.content.Intent
import android.app.Activity
import android.view.View

class Activity_Add_Edit_Folder : AppCompatActivity() {
    lateinit var tv_cancel: TextView
    var tv_save: TextView? = null
    var tv_title: TextView? = null
    var edt_name: EditText? = null
    var edt_description: EditText? = null
    var mlistFolder: List<FolderModel>? = null
    var name: String? = null
    var description: String? = null
    var index = 0
    var folder: FolderModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_folder)
        tv_title = findViewById(R.id.tv_title)
        tv_cancel = findViewById(R.id.tv_cancel)
        tv_save = findViewById(R.id.tv_save)
        edt_name = findViewById(R.id.edt_name)
        edt_description = findViewById(R.id.edt_description)
        setupToolbar()
        val bundle = intent.extras
        mlistFolder = intent.getSerializableExtra(Activity_Folder.LIST_FOLDER) as List<FolderModel>?
        folder = bundle!!.getSerializable(Activity_Folder.ITEM_FOLDER) as FolderModel?
        if (intent.action == Activity_Folder.ACTION_ADD) {
            AddActivity()
        }
        if (intent.action === Activity_Folder.ACTION_EDIT) {
            EditActivity()
        }
    }

    private fun AddActivity() {
        tv_title!!.text = getString(R.string.add_folder)
        tv_save!!.setOnClickListener { addItemFoler() }
        tv_cancel!!.setOnClickListener { onBackPressed() }
    }

    private fun EditActivity() {
        tv_title!!.text = getString(R.string.edit_folder)
        edt_name!!.setText(folder!!.name)
        edt_description!!.setText(folder!!.description)
        tv_save!!.setOnClickListener { updateItemFoler() }
        tv_cancel!!.setOnClickListener { onBackPressed() }
    }

    private fun addItemFoler() {
        //name = edt_name!!.text.toString().trim { it <= ' ' }
        name = edt_name!!.text.toString().trim()
        description = edt_description!!.text.toString().trim { it <= ' ' }
        if (checkFolderName(name!!)) {
            Toast.makeText(this, getString(R.string.name_folder_exists), Toast.LENGTH_SHORT).show()
        } else if (name == "") {
            Toast.makeText(this, getString(R.string.name_folder_not_empty), Toast.LENGTH_SHORT)
                .show()
        } else if (description == "") {
            Toast.makeText(this, getString(R.string.discription_folder_exists), Toast.LENGTH_SHORT)
                .show()
        } else {
            val intent = intent
            intent.putExtra(Activity_Folder.NAME_FOLDER, name)
            intent.putExtra(Activity_Folder.DESCRIPTION_FOLDER, description)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun updateItemFoler() {
        val name = edt_name!!.text.toString().trim()
        val description = edt_description!!.text.toString().trim { it <= ' ' }
        if (name == "") {
            Toast.makeText(this, getString(R.string.name_folder_not_empty), Toast.LENGTH_SHORT)
                .show()
        } else if (description == "") {
            Toast.makeText(this, getString(R.string.discription_folder_exists), Toast.LENGTH_SHORT)
                .show()
        } else if (name == folder!!.name && description == folder!!.description) {
            Toast.makeText(
                this,
                getString(R.string.name_discription_not_change),
                Toast.LENGTH_SHORT
            ).show()
        } else if (checkFolderName(name)) {
            Toast.makeText(this, getString(R.string.name_folder_exists), Toast.LENGTH_SHORT).show()
        } else {
            folder!!.name = name
            folder!!.description = description
            val intent = Intent(this, Activity_Folder::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Activity_Folder.ITEM_FOLDER, folder)
            intent.putExtras(bundle)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    private fun checkFolderName(name: String): Boolean {
        for (mfolder in mlistFolder!!) {
            if (folder != null) {
                if (mfolder.id == folder!!.id) {
                    continue
                }
            }
            if (name == mfolder.name) {
                return true
            }
        }
        return false
    }

    fun setupToolbar() {
        window.statusBarColor = getColor(R.color.white)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}