package com.example.whatsapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whatsapp.Models.Massage;
import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends RecyclerView.Adapter {
    ArrayList<Massage> massageslist;
    Context context;
    String rcvId;
    int SENDER_VIEW_TYPE=1,RECIEVER_VIEW_TYPE=2;

    public ChatAdapter(ArrayList<Massage> massages, Context context) {
        this.massageslist = massages;
        this.context = context;
    }

    public ChatAdapter(ArrayList<Massage> massageslist, Context context, String rcvId) {
        this.massageslist = massageslist;
        this.context = context;
        this.rcvId = rcvId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE) {
            View view= LayoutInflater.from(context).inflate(R.layout.sender_layout,parent,false);
            return new senderViewHolder(view);
        } else {
            View view= LayoutInflater.from(context).inflate(R.layout.reciever_layout,parent,false);
            return new recieverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(massageslist.get(position).getuId().equals(FirebaseAuth.getInstance().getUid())) {
            return SENDER_VIEW_TYPE;
        } else {
            return RECIEVER_VIEW_TYPE;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Massage massage=massageslist.get(position);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are You Sure , you want to delet This Massage?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                FirebaseDatabase database=FirebaseDatabase.getInstance();
                                if(rcvId!=null) {
                                    String sender = FirebaseAuth.getInstance().getUid() + rcvId;
                                    database.getReference().child("Chats").child(sender).child(massage.getmId()).setValue(null);
                                } else {
                                    database.getReference().child("GroupChat").child(massage.getmId()).setValue(null);
                                }
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
                return false;
            }
        });
        if(holder.getClass()==senderViewHolder.class) {
            ((senderViewHolder)holder).tvSender.setText(massage.getMassage());
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String dateString = formatter.format(new Date(massage.getTimestamp()));
            ((senderViewHolder)holder).tvSenderTime.setText(dateString);
        } else {
            ((recieverViewHolder)holder).tvReciever.setText(massage.getMassage());
            SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
            String dateString = formatter.format(new Date(massage.getTimestamp()));
            ((recieverViewHolder)holder).tvRecieverTime.setText(dateString);
        }
    }

    @Override
    public int getItemCount() {
        return massageslist.size();
    }

    public class recieverViewHolder extends RecyclerView.ViewHolder {
        TextView tvReciever,tvRecieverTime;

        public recieverViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReciever=itemView.findViewById(R.id.tv_receiver_text);
            tvRecieverTime=itemView.findViewById(R.id.tv_reciever_time);

        }
    }
    public class senderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSender,tvSenderTime;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSender=itemView.findViewById(R.id.tv_sender_text);
            tvSenderTime=itemView.findViewById(R.id.tv_sender_time);

        }
    }
}
