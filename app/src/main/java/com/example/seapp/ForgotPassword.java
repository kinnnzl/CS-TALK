package com.example.seapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgotPassword extends AppCompatActivity {

    private Dialog dlReset;
    private Button confirm;
    private EditText email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.etEmailForgotPass);

        dlReset = new Dialog(this);

        confirm = findViewById(R.id.btnSendEmailForgotPass);
        mAuth = FirebaseAuth.getInstance();
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(email.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplication(), "กรุณากรอกข้อมูลให้ครบ", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(email.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                confirm = dlReset.findViewById(R.id.btnSendEmailForgotPass);
                                ShowPopup(view);
                                //Toast.makeText(getApplication(), "ตรวจสอบรายละเอียดในอีเมลของคุณ", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplication(), "เกิดข้อผิดพลาด", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

    }
    public void ShowPopup(View v){

        dlReset.setContentView(R.layout.activity_password_sent);
        //confirm = dlReset.findViewById(R.id.btnSendEmailForgotPass);

        dlReset.show();

        dlReset.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
