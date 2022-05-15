package com.example.contacts;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AsyncNotedAppOp;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.contacts.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ContactEntity> contactList;
    private ContactAdapter contactAdapter;
    ActivityMainBinding binding;
    private AppDatabase appDatabase;
    private ContactDAO contactDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        binding.rvContacts.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactAdapter = new ContactAdapter(contactList,launchSomeActivity);
        binding.rvContacts.setAdapter(contactAdapter);


        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PersonDetail.class);
                launchSomeActivity.launch(intent);
            }
        });


        appDatabase = AppDatabase.getInstance(getApplicationContext());
        contactDAO = appDatabase.contactDAO();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.delete();
            }
        });

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contactDAO.insert(
                        new ContactEntity("Le Hong","Minh","0971625348","lhm@gmail.com"),
                        new ContactEntity("Le Thi Minh","Nguyet", "0971625348", "hn@gmail.com"),
                        new ContactEntity("Le Thi Thuy","Hang", "0763034216", "lttt@gmail.com"),
                        new ContactEntity("Luong Van","Do", "0763034216", "lvd1993@gmail.com"),
                        new ContactEntity("Trinh Thi","Lien", "0763034216", "ttl1973@gmail.com"),
                        new ContactEntity("Le Thanh","Vy", "0763034216", "ltv1972@gmail.com")
                );

                contactList = (ArrayList<ContactEntity>) contactDAO.getAll();
                contactAdapter.setData(contactList);
            }
        });
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                contactList = (ArrayList<ContactEntity>) contactDAO.getAll();
                contactAdapter.setData(contactList);
            }
        });
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        String s = binding.etSearch.getText().toString();
                        if(s.equals(""))
                            contactList = (ArrayList<ContactEntity>) contactDAO.getAll();
                        else {
                            contactList = (ArrayList<ContactEntity>) contactDAO.search(s);
                        }
                    }
                });
                contactAdapter.setData(contactList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        int index = Integer.parseInt(data.getStringExtra("index"));
                        ContactEntity contactEntity = (ContactEntity) data.getSerializableExtra("contactEntity");
                        if(index != -1) {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    int id = contactList.get(index).getId();
                                    contactDAO.update(id, contactEntity.getFirstName(), contactEntity.getLastName(), contactEntity.getPhone(), contactEntity.getEmail());
                                    contactList = (ArrayList<ContactEntity>) contactDAO.getAll();
                                    contactAdapter.setData(contactList);
                                }
                            });
                            Toast.makeText(MainActivity.this, "Edit Success", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            AsyncTask.execute(new Runnable() {
                                @Override
                                public void run() {
                                    contactDAO.insert(contactEntity);
                                    contactList = (ArrayList<ContactEntity>) contactDAO.getAll();
                                    contactAdapter.setData(contactList);
                                }
                            });
                            Toast.makeText(MainActivity.this, "Add Success", Toast.LENGTH_SHORT).show();
                        }
                        contactAdapter.notifyDataSetChanged();
                    }
                }
            });
}