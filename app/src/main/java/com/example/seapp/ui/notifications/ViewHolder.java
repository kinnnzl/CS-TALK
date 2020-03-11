package com.example.seapp.ui.notifications;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seapp.R;

public class ViewHolder extends RecyclerView.ViewHolder {
    static View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView =itemView;

    }

    public static void  setDetails(Context ctx, String userid, String username, String comment)
    {
        TextView mpost=mView.findViewById(R.id.text_title);
        TextView mname=mView.findViewById(R.id.text_title);
        //TextView mcom=mView.findViewById(R.id.text_description);

        mname.setText(username);
        //mcom.setText(comment);
    }
}
