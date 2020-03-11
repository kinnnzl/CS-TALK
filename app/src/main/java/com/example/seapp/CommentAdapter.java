package com.example.seapp;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CommentAdapter extends  RecyclerView.Adapter<CommentAdapter.MyViewHolder>{
    Context mContext;
    List<Comment> mData;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference mRef;


    public CommentAdapter(Context mContext, List<Comment> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.comment_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment comment = mData.get(position);
        holder.profile_name.setText(mData.get(position).getName());
        holder.comment_detail.setText(mData.get(position).getDetail());
        holder.profile_img.setImageResource(mData.get(position).getPic());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        TextView profile_name,comment_detail;
        ImageView profile_img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            comment_detail = (TextView) itemView.findViewById(R.id.comment_detail);
            profile_name = (TextView) itemView.findViewById(R.id.comment_username);
            profile_img = (ImageView) itemView.findViewById(R.id.comment_image);
            layout = (ConstraintLayout)itemView.findViewById(R.id.commentLayout);

        }


    }
}
