package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Models.Massage;
import com.example.whatsapp.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.internal.cache.DiskLruCache;

public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        final String senderId=auth.getUid();
        String recieveId=getIntent().getStringExtra("UserId");
        String ProfilePic=getIntent().getStringExtra("UserProfilePic");
        String UserName=getIntent().getStringExtra("UserName");
        binding.tvActiveUsername.setText(UserName);
        Picasso.get().load(ProfilePic).placeholder(R.drawable.avtar).into(binding.profileImage);
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        final ArrayList<Massage>arrayList=new ArrayList<Massage>();
        final ChatAdapter chatAdapter=new ChatAdapter(arrayList,this,recieveId);
        binding.recyclerviewDetailChat.setAdapter(chatAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.recyclerviewDetailChat.setLayoutManager(layoutManager);
        final String senderRoom=senderId+recieveId;
        final String receiverRooms=recieveId+senderId;
        database.getReference().child("Chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot1:snapshot.getChildren()) {
                    Massage mmodel=snapshot1.getValue(Massage.class);
                    mmodel.setmId(snapshot1.getKey());
                    arrayList.add(mmodel);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        binding.ivSimpleCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatDetailActivity.this, "We will update This Feature Soon.!!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatDetailActivity.this, "We will update This Feature Soon.!!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivVideoCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChatDetailActivity.this, "We will update This Feature Soon.!!", Toast.LENGTH_SHORT).show();
            }
        });
        binding.ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=binding.etTypeMassage.getText().toString().trim();
                if(!msg.isEmpty()) {
                    final Massage massage=new Massage(senderId,msg);
                    massage.setTimestamp(new Date().getTime());
                    binding.etTypeMassage.setText("");
                    database.getReference().child("Chats").child(senderRoom).push().setValue(massage)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    database.getReference().child("Chats").child(receiverRooms).push().setValue(massage)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {

                                                }
                                            });
                                }
                            });
                }
            }
        });
    }
}