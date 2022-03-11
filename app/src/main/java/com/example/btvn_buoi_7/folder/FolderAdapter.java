package com.example.btvn_buoi_7.folder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btvn_buoi_7.R;

import java.io.Serializable;
import java.util.List;

public class FolderAdapter extends RecyclerView.Adapter {

    List<FolderModel> folderModelList;
    private Activity_Folder context;

    public FolderAdapter(Activity_Folder mcontext) {
        this.folderModelList = folderModelList;
        this.context = mcontext;
    }

    public void setData(List<FolderModel> folderModelList){
        this.folderModelList = folderModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_folder,parent,false);
        return new FolderViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,  int position) {
        int index = position;
        FolderModel folder = folderModelList.get(position);

        ((FolderViewholder) holder).tv_name.setText(folder.getName());
        ((FolderViewholder) holder).tv_description.setText(folder.getDescription());

        ((FolderViewholder) holder).layout_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Activity_Add_Edit_Folder.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Activity_Folder.ITEM_FOLDER,folder);
                bundle.putSerializable(Activity_Folder.LIST_FOLDER, (Serializable) folderModelList);
                intent.putExtras(bundle);
                intent.setAction(Activity_Folder.ACTION_EDIT);
                ((Activity)context).startActivityForResult(intent,Activity_Folder.REQUEST_CODE_EDIT_ITEM);
            }
        });

        ((FolderViewholder) holder).ic_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.showPopupMenu(((FolderViewholder) holder).ic_option, folder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderModelList.size();
    }

    public class FolderViewholder extends RecyclerView.ViewHolder{
        private TextView tv_name, tv_description;
        private RelativeLayout layout_item;
        private ImageView ic_option;
        public FolderViewholder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_description = itemView.findViewById(R.id.tv_description);
            layout_item = itemView.findViewById(R.id.layout_item);
            ic_option = itemView.findViewById(R.id.ic_option);


        }
    }
}
