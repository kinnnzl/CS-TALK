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

import com.example.seapp.ui.additional.History;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.MyViewHolder> {

    Context mContext;
    List<SearchHistory> mData;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase database;
    private FirebaseUser user;
    private DatabaseReference mRef;


    public SearchHistoryAdapter(Context mContext, List<SearchHistory> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(mContext).inflate(R.layout.search_history_row,parent,false);
        return new MyViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //final SearchHistory history = mData.get(position);

        holder.history_detail.setText(mData.get(position).getDetail());
        holder.icon.setImageResource(R.drawable.search_orange);
        //holder.cross.setImageResource(R.drawable.cross);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //Toast.makeText(mContext,"You Click"+post.getName(),Toast.LENGTH_LONG).show();

//                SearchHistory history = new SearchHistory(mData.get(position).getDetail());
//                addSearchHistory(history);

                Intent intent = new Intent(mContext, PostDetials.class);
               intent.putExtra("PostKey",mData.get(position).getPostKey().toString());
               intent.putExtra("Details",mData.get(position).getDetail().toString());
               intent.putExtra("Picture",mData.get(position).getPic());
               intent.putExtra("Name",mData.get(position).getName().toString());
                mContext.startActivity(intent);
            }
        });

    }

//    //add searchHistory to firebase
//    public void addSearchHistory(SearchHistory searchHistory) {
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        String id = user.getUid();
//        DatabaseReference mRef_addSearchHistory = firebaseDatabase.getReference("User").child(id).child("History").push();
//
//
//        //String key = mRef_addSearchHistory.getKey();
//
//
//        //database.getReference("User").child(id).child("Post").child("PostKey").setValue(key);
//        //DatabaseReference userHistoryinUser = firebaseDatabase.getReference("User").child(id).child("History").push();
//
//
//        //searchHistory.setPostKey(userHistoryinUser.getKey());
//        mRef_addSearchHistory.setValue(searchHistory);
//
//
//    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout layout;
        TextView history_detail;
        ImageView icon,cross;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            history_detail = (TextView) itemView.findViewById(R.id.tvHistoryDetail);
            icon = (ImageView) itemView.findViewById(R.id.search_icon_fix);
            cross = (ImageView) itemView.findViewById(R.id.ic_delete);
            layout = (ConstraintLayout)itemView.findViewById(R.id.historyLayout);

        }


    }


}
