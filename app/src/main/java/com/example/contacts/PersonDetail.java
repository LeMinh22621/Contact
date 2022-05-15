package com.example.contacts;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.contacts.databinding.ActivityPersonDetailBinding;

public class PersonDetail extends AppCompatActivity {

    ActivityPersonDetailBinding binding;
    private int index;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPersonDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        Intent receiveIntent = getIntent();
        try{
            index = Integer.parseInt(receiveIntent.getStringExtra("index"));
        }
        catch (Exception exception)
        {
            index = -1;
        }
        if(receiveIntent != null && index != -1)
        {
            ContactEntity contactEntity = (ContactEntity) receiveIntent.getSerializableExtra("contactEntity");
            binding.etFirstname.setText(contactEntity.getFirstName());
            binding.etLastname.setText(contactEntity.getLastName());
            binding.etPhone.setText(contactEntity.getPhone());
            binding.etEmail.setText(contactEntity.getEmail());
        }
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("index", index + "");
                ContactEntity contactEntity = new ContactEntity(binding.etFirstname.getText().toString(),
                                                    binding.etLastname.getText().toString(),
                                                    binding.etPhone.getText().toString(),
                                                    binding.etEmail.getText().toString());
                intent.putExtra("contactEntity", contactEntity);
                setResult(RESULT_OK,intent);
                //Close the activity
                finish();
            }
        });
//        binding.ibCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                try{
//                    activityResultLauncher.launch(takePictureIntent);
//                }
//                catch (ActivityNotFoundException ex)
//                {
//
//                }
//            }
//        });
    }
//    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK)
//                    {
//                        try {
//                            Bundle extras = result.getData().getExtras();
//                            Bitmap imageBitmap = (Bitmap) extras.get("data");
//                            binding.ivAvatar.setImageBitmap(imageBitmap);
//                            Toast.makeText(PersonDetail.this, "Action Success", Toast.LENGTH_SHORT).show();
//                        }
//                        catch (Exception ex)
//                        {
//                            Toast.makeText(PersonDetail.this, "Action Failed", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//    );
}