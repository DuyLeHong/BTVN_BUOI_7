package com.example.btvn_buoi_7.folder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btvn_buoi_7.R;
import com.example.btvn_buoi_7.database.DatabaseFolder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Activity_Folder extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_ITEM = 123;
    public static final int REQUEST_CODE_EDIT_ITEM = 1234;

    public static final String ACTION_ADD = "add";
    public static final String ACTION_EDIT = "edit";

    public static final String ITEM_FOLDER = "item_folder";
    public static final String LIST_FOLDER = "list_folder";

    public static final String NAME_FOLDER = "name";
    public static final String DESCRIPTION_FOLDER = "description";

    RecyclerView rcv_folder;
    public List<FolderModel> folderList = new ArrayList<>();
    Toolbar toolbar;
    TextView tv_add_item_toolbar;
    FolderAdapter adapter;
    DatabaseFolder databaseFolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        initUI();

        databaseFolder = new DatabaseFolder(this);

        adapter = new FolderAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcv_folder.setLayoutManager(linearLayoutManager);

        rcv_folder.setAdapter(adapter);

        setToolbar();

        loadData();

        adapter.setData(folderList);

        tv_add_item_toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toActivityAddItem();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK) {
            String name = data.getExtras().getString(NAME_FOLDER);
            String description = data.getExtras().getString(DESCRIPTION_FOLDER);
            FolderModel folder = new FolderModel(name, description);
            databaseFolder.insertFolder(folder);
            loadData();
            Toast.makeText(this,getString(R.string.add_folder_successfully) , Toast.LENGTH_SHORT).show();

        } else if (requestCode == REQUEST_CODE_EDIT_ITEM && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            FolderModel folder = (FolderModel) bundle.getSerializable(ITEM_FOLDER);
            databaseFolder.updateFolder(folder);
            loadData();
            Toast.makeText(this, getString(R.string.edit_folder_successfully), Toast.LENGTH_SHORT).show();
        }


    }

    private void initUI() {
        rcv_folder = findViewById(R.id.rcv_folder);
        toolbar = findViewById(R.id.toolbar);
        tv_add_item_toolbar = findViewById(R.id.tv_add_item_toolbar);
    }

    private void toActivityAddItem() {
        Intent intent = new Intent(Activity_Folder.this, Activity_Add_Edit_Folder.class);
        intent.putExtra( LIST_FOLDER, (Serializable) folderList);
        intent.setAction(ACTION_ADD);
        startActivityForResult(intent, REQUEST_CODE_ADD_ITEM);
    }

    private void loadData() {
        folderList.clear();
        folderList.addAll(databaseFolder.getAllFolder());

        adapter.notifyDataSetChanged();
    }

    private void setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getWindow().setStatusBarColor(getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

     void showPopupMenu(View view, FolderModel folder){
        PopupMenu popup = new PopupMenu(this,view);
        popup.inflate(R.menu.menu_option);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_delete:
                        showActionDialogDeleteItem(folder);
                        return true ;
                    case R.id.menu_edit:
                        Intent intent = new Intent(Activity_Folder.this, Activity_Add_Edit_Folder.class);
                        intent.putExtra( LIST_FOLDER, (Serializable) folderList);
                        intent.putExtra(ITEM_FOLDER,folder);
                        intent.setAction(ACTION_EDIT);
                        startActivityForResult(intent, REQUEST_CODE_EDIT_ITEM);
                }
                return false;
            }
        });
        popup.show();
    }

    private void showActionDialogDeleteItem(FolderModel folder){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Bạn có muốn xóa folder này");
        dialog.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                databaseFolder.deleteFolder(folder);
                loadData();
            }
        });
        dialog.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog.show();
    }


}