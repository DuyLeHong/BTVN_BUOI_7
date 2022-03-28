package com.example.btvn_buoi_7.folder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.btvn_buoi_7.R;

import java.util.List;

public class Activity_Add_Edit_Folder extends AppCompatActivity {
    TextView tv_cancel, tv_save,tv_title;
    EditText edt_name, edt_description;
    List<FolderModel> mlistFolder;
    String name, description;
    int index;
    FolderModel folder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        tv_title = findViewById(R.id.tv_title);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_save = findViewById(R.id.tv_save);
        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);

        setupToolbar();

        Bundle bundle = getIntent().getExtras();
        mlistFolder = (List<FolderModel>) getIntent().getSerializableExtra(Activity_Folder.LIST_FOLDER);
        folder = (FolderModel) bundle.getSerializable(Activity_Folder.ITEM_FOLDER);

        if(getIntent().getAction() == Activity_Folder.ACTION_ADD){
            AddActivity();
        }if (getIntent().getAction()== Activity_Folder.ACTION_EDIT){
            EditActivity();
        }

    }

    private void AddActivity(){
        tv_title.setText(getString(R.string.add_folder));
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addItemFoler();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void EditActivity(){
        tv_title.setText(getString(R.string.edit_folder));

        edt_name.setText(folder.getName());
        edt_description.setText(folder.getDescription());

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    updateItemFoler();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void addItemFoler() {
        name = edt_name.getText().toString().trim();
        description = edt_description.getText().toString().trim();
        if (checkFolderName(name)) {
            Toast.makeText(this, getString(R.string.name_folder_exists), Toast.LENGTH_SHORT).show();
        } else if (name.equals("")) {
            Toast.makeText(this, getString(R.string.name_folder_not_empty), Toast.LENGTH_SHORT).show();
        } else if (description.equals("")) {
            Toast.makeText(this,  getString(R.string.discription_folder_exists), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = getIntent();
            intent.putExtra(Activity_Folder.NAME_FOLDER,name);
            intent.putExtra(Activity_Folder.DESCRIPTION_FOLDER, description);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
    private void updateItemFoler() {

        String name = edt_name.getText().toString().trim();
        String description = edt_description.getText().toString().trim();

        if (name.equals("")) {
            Toast.makeText(this, getString(R.string.name_folder_not_empty), Toast.LENGTH_SHORT).show();
        } else if (description.equals("")) {
            Toast.makeText(this, getString(R.string.discription_folder_exists), Toast.LENGTH_SHORT).show();
        }  else if (name.equals(folder.getName()) && description.equals(folder.getDescription())) {
            Toast.makeText(this, getString(R.string.name_discription_not_change), Toast.LENGTH_SHORT).show();
        }else if(checkFolderName(name)){
            Toast.makeText(this, getString(R.string.name_folder_exists), Toast.LENGTH_SHORT).show();
        }else {
            folder.setName(name);
            folder.setDescription(description);
            Intent intent = new Intent(this, Activity_Folder.class);
            Bundle bundle = new Bundle();

            bundle.putSerializable(Activity_Folder.ITEM_FOLDER,folder);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    private boolean checkFolderName(String name) {
        for (FolderModel mfolder: mlistFolder) {
            if(folder != null){
                if(mfolder.getId() == folder.getId() ){
                    continue;
                }
            }
            if (name.equals(mfolder.getName())) {
                return true;
            }
        }
        return false;
    }

    public void setupToolbar(){
        getWindow().setStatusBarColor(getColor(R.color.white));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}
