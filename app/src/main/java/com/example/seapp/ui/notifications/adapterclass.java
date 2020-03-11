package com.example.seapp.ui.notifications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seapp.R;

import java.util.List;

public class adapterclass extends RecyclerView.Adapter<adapterclass.Holder>      {
    private String postmess;
    private String username;
    private String comment;
    private String detail;
    Context mContext;
    List<CommentNotification> notilist;


    public adapterclass(Context mContext, List<CommentNotification> notilist){
        this.mContext = mContext;
        this.notilist = notilist;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mContext).inflate(R.layout.card_holder,parent,false);
        return  new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position)
    {
        holder.textTitle.setText(notilist.get(position).getPostTopic());
        holder.textnamepost.setText(notilist.get(position).getName()+"  :  "+notilist.get(position).getDetail());
        //holder.textDescription.setText(notilist.get(position).getDetail());
        holder.imagenoti.setImageResource(R.drawable.back_orange);
    }






    @Override
    public int getItemCount() {
        return notilist.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        public ConstraintLayout layout;
        TextView textTitle;
        TextView textDescription;
        TextView textnamepost;
        ImageView imagenoti;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.text_title);
            textnamepost = itemView.findViewById(R.id.text_namepost);
            //textDescription =itemView.findViewById(R.id.text_description);
            imagenoti =itemView.findViewById(R.id.imagenoti);

        }

    }


}
