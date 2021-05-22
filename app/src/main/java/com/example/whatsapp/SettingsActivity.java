package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.whatsapp.Models.Users;
import com.example.whatsapp.databinding.ActivitySettingsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding binding;
    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.show();
        if(data.getData()!=null) {
            Uri filepath = data.getData();
            binding.ivProfilePicSettings.setImageURI(filepath);
            final StorageReference reference=storage.getReference().child("ProfilePics")
                    .child(auth.getUid());
            reference.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            database.getReference().child("Users").child(auth.getUid()).child("profilePicPath").setValue(uri.toString());
                        }
                    });
                    Toast.makeText(SettingsActivity.this, "Profile Photo Uploaded Succesfully.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        getSupportActionBar().hide();

        binding.ivAddNewPP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
                progressDialog=new ProgressDialog(SettingsActivity.this);
                progressDialog.setTitle("Please Wait.!!");
                progressDialog.setMessage("We Are Uploading Your Profile Photo .!!");
            }
        });
        binding.tvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will Update this feature soon.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will Update this feature soon.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvInviteF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will Update this feature soon.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will Update this feature soon.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.tvPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "We will Update this feature soon.", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivBackSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        binding.btnSetSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=binding.etAddUserName.getText().toString(),status=binding.etAddStatus.getText().toString();
                HashMap<String,Object>obj=new HashMap<>();
                obj.put("userName",uname);
                obj.put("about",status);
                database.getReference().child("Users").child(auth.getUid()).updateChildren(obj);
                Toast.makeText(SettingsActivity.this, "Updated Successfully.!!", Toast.LENGTH_SHORT).show();


            }
        });
        database.getReference().child("Users").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                Picasso.get().load(users.getProfilePicPath()).placeholder(R.drawable.avtar).into(binding.ivProfilePicSettings);
                binding.etAddUserName.setText(users.getUserName());
                binding.etAddStatus.setText(users.getAbout());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}