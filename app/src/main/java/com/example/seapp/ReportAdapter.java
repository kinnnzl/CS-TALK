package com.example.seapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    Context mContext;
    List<ReportNotification> mData;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference mRef;



    public ReportAdapter(Context mContext, List<ReportNotification> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.report_row,parent, false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.report_detail.setText(" ' "+mData.get(position).getUsername()+" ' "+" แจ้งลบกระทู้ "+" : "+mData.get(position).getPostTopic());

        holder.img.setImageResource(R.mipmap.warning);

//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Toast.makeText(mContext,"You Click"+post.getName(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(mContext, PostDetials.class);
//                intent.putExtra("PostKey",mData.get(position).getPostKey().toString());
//                intent.putExtra("Details",mData.get(position).getDetail().toString());
//                intent.putExtra("ownerID",mData.get(position).getUserid().toString());
//                intent.putExtra("Picture",mData.get(position).getPic());
//                intent.putExtra("Name",mData.get(position).getName().toString());
//                mContext.startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        TextView report_detail;
        ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            report_detail = (TextView) itemView.findViewById(R.id.text_title);
            img = (ImageView) itemView.findViewById(R.id.imageReport);
            layout = (ConstraintLayout)itemView.findViewById(R.id.reportLayout);


        }

    }




}
