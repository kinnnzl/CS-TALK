package com.example.seapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Report extends AppCompatActivity {
    private RadioGroup radio;
    private EditText text;
    private Button commit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        commit = (Button)findViewById(R.id.commit_btn);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"รายงานสำเร็จ",Toast.LENGTH_LONG).show();
                String name = getIntent().getExtras().getString("Name");
                String detail = getIntent().getExtras().getString("Detail");
                String postID = getIntent().getExtras().getString("PostKey");
                ReportNotification reportNotification = new ReportNotification(postID, detail, name);
                addreportNotification(reportNotification);
                finish();
            }
        });



        text = (EditText)findViewById(R.id.other_textfiled);
        radio = (RadioGroup) findViewById(R.id.radioGroup);
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                View radioButton = radio.findViewById(checkedId);
                int index = radio.indexOfChild(radioButton);
                switch (index) {
                    case 4:
                        text.setVisibility(View.VISIBLE);
                        break;
                    default:
                        text.setVisibility(View.INVISIBLE);
                        break;
                }

            }
        });//setOncheck
    }//Oncreate

    private void addreportNotification(ReportNotification reportNotification) {
        DatabaseReference ref_report = FirebaseDatabase.getInstance().getReference("User").child("R5cKy3irp6dW14NrZlMNIokx3j43")
                .child("Report").push();
        ref_report.setValue(reportNotification).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "รีพอร์ทสำเร็จ", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
