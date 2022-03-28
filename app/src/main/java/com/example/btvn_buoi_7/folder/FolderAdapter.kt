package com.example.btvn_buoi_7.folder

import androidx.recyclerview.widget.RecyclerView
import com.example.btvn_buoi_7.folder.FolderModel
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.btvn_buoi_7.R
import com.example.btvn_buoi_7.folder.FolderAdapter.FolderViewholder
import android.content.Intent
import com.example.btvn_buoi_7.folder.Activity_Add_Edit_Folder
import android.os.Bundle
import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.RelativeLayout
import java.io.Serializable

class FolderAdapter(mcontext: Activity_Folder) : RecyclerView.Adapter<FolderViewholder>() {
    lateinit var folderModelList: List<FolderModel>
    private val context: Activity_Folder
    fun setData(folderModelList: List<FolderModel>) {
        this.folderModelList = folderModelList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewholder{
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_item_folder, parent, false)
        return FolderViewholder(view)
    }

    override fun onBindViewHolder(holder: FolderViewholder, position: Int) {
        val index = position
        val folder = folderModelList[position]
        (holder as FolderViewholder).tv_name.text = folder.name
        holder.tv_description.text = folder.description
        holder.layout_item.setOnClickListener {
            val intent = Intent(context, Activity_Add_Edit_Folder::class.java)
            val bundle = Bundle()
            bundle.putSerializable(Activity_Folder.ITEM_FOLDER, folder)
            bundle.putSerializable(Activity_Folder.LIST_FOLDER, folderModelList as Serializable)
            intent.putExtras(bundle)
            intent.action = Activity_Folder.ACTION_EDIT
            (context as Activity).startActivityForResult(
                intent,
                Activity_Folder.REQUEST_CODE_EDIT_ITEM
            )
        }
        holder.ic_option.setOnClickListener { context.showPopupMenu(holder.ic_option, folder) }
    }

    override fun getItemCount(): Int {
        return folderModelList.size
    }

    inner class FolderViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tv_name: TextView
         val tv_description: TextView
         val layout_item: RelativeLayout
         val ic_option: ImageView

        init {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_description = itemView.findViewById(R.id.tv_description)
            layout_item = itemView.findViewById(R.id.layout_item)
            ic_option = itemView.findViewById(R.id.ic_option)
        }
    }

    init {
        context = mcontext
    }
}