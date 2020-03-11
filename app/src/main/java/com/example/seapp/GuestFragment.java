package com.example.seapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuestFragment extends Fragment {
    private Button commit;
    private EditText fname,lname,email,password,confirmpass;
    private TextView warning;
    public FirebaseDatabase database;
    public DatabaseReference myRef;
    private String userId;
    private FirebaseAuth mAuth;
    private String userType ="guest";
    private String inType = "บุคลภายนอก";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.guest_fragment, container,false);

        setComponent(v);

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean passCorrect =false;
                boolean nameCorrrect = false;

                Intent intent = new Intent(getActivity(), register2.class);
                intent.putExtra("email",email.getText().toString().trim());
                intent.putExtra("userType",userType);
                intent.putExtra("inType",inType);
                if(fname.getText().toString().trim().isEmpty() || lname.getText().toString().trim().isEmpty()
                        || email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()
                        || confirmpass.getText().toString().trim().isEmpty())
                    Toast.makeText(getActivity(), "กรุณากรอกข้อมูลให้ครบทุกช่อง", Toast.LENGTH_SHORT).show();
                else {
                    if (isValidNameFormat()) {
                        intent.putExtra("fname",fname.getText().toString().trim());
                        intent.putExtra("lname",lname.getText().toString().trim());
                        nameCorrrect= true;

                    } else {
                        Toast.makeText(getActivity(), "ชื่อไม่ตรงตามรูปแบบ", Toast.LENGTH_SHORT).show();
                    }

                    if (!(password.getText().toString().trim()).equals(confirmpass.getText().toString().trim())) {
                        Toast.makeText(getActivity(), "พาสเวิร์ดไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                    }else {

                        if (isValidPassword()) {
                            password.setBackgroundResource(R.drawable.borderbox);
                            warning.setVisibility(View.INVISIBLE);
                            intent.putExtra("password",password.getText().toString().trim());
                            passCorrect=true;
                        } else {
                            password.setBackgroundResource(R.drawable.red_border);
                            warning.setVisibility(View.VISIBLE);


                        }
                    }

                }
                if(passCorrect && nameCorrrect){
                    startActivity(intent);
                }

            }//OnClick



        });



        return  v;
    }


    public void setComponent(View v){
        commit = (Button)getActivity().findViewById(R.id.cmt2_btn);
        fname = (EditText)v.findViewById(R.id.Fname2);
        lname =(EditText)v.findViewById(R.id.Lname2);
        email =(EditText)v.findViewById(R.id.email);
        password =(EditText)v.findViewById(R.id.password);
        confirmpass=(EditText)v.findViewById(R.id.confirmPass);
        warning = (TextView)v.findViewById(R.id.expression_text);


    }

    public boolean isValidNameFormat() {
        Pattern pattern;
        Matcher matcher;
        final String Name_PATTERN = "^[ก-๙a-zA-Z]*$";
        pattern = Pattern.compile(Name_PATTERN);
        matcher = pattern.matcher(fname.getText().toString().trim());
        matcher = pattern.matcher(lname.getText().toString().trim());
        return matcher.matches();
    }

    public boolean isValidPassword() {

        Pattern pattern;
        Matcher matcher;

        final String Name_PATTERN = "^[ก-๙a-zA-Z0-9. ]*$";
        pattern = Pattern.compile(Name_PATTERN);
        matcher = pattern.matcher(password.getText().toString().trim());
        return matcher.matches();

    }

}
