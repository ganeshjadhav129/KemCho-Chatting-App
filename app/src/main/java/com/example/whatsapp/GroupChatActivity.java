package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Models.Massage;
import com.example.whatsapp.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
    ActivityGroupChatBinding binding;
    FirebaseDatabase database;
    ArrayList<Massage>arrayList=new ArrayList<Massage>();
    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        binding.ivBackgroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(GroupChatActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final String senderId= FirebaseAuth.getInstance().getUid();
        binding.tvActiveUsernamegroupChat.setText("Friends Group");
        Picasso.get().load(R.drawable.avtar).into(binding.profileImagegroupChat);
        chatAdapter=new ChatAdapter(arrayList,this,null);
        binding.recyclerviewDetailChatgroupChat.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerviewDetailChatgroupChat.setLayoutManager(layoutManager);
        database.getReference().child("GroupChat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    Massage massage=dataSnapshot.getValue(Massage.class);
                    massage.setmId(dataSnapshot.getKey());
                    arrayList.add(massage);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        binding.ivInfogroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroupChatActivity.this, "We will Update this feature soon.!!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivSimpleCallgroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroupChatActivity.this, "We will Update this feature soon.!!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivVideoCallgroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(GroupChatActivity.this, "We will Update this feature soon.!!", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivSendgroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!binding.etTypeMassagegroupChat.getText().toString().isEmpty()) {
                    String ourmasg=binding.etTypeMassagegroupChat.getText().toString();
                    Massage msg=new Massage();
                    msg.setMassage(ourmasg);
                    msg.setuId(senderId);
                    msg.setTimestamp(new Date().getTime());
                    binding.etTypeMassagegroupChat.setText("");
                    database.getReference().child("GroupChat").push().setValue(msg).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }
        });
    }
}