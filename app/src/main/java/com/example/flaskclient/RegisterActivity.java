package com.example.flaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;

public class RegisterActivity extends AppCompatActivity {

    EditText namef;
    EditText addressf;
    EditText phoneNof;
    EditText dadf;
    EditText momf;
    EditText emconf;
    EditText guardianf;
    EditText agef;
    Button submitbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        namef = findViewById((R.id.name));
        addressf = findViewById((R.id.address));
        phoneNof = findViewById((R.id.phone));
        dadf = findViewById((R.id.dad));
        momf = findViewById((R.id.mom));
        emconf = findViewById((R.id.emcon));
        guardianf = findViewById((R.id.guardian));
        agef = findViewById((R.id.age));
        submitbtn = findViewById((R.id.submitbutton));

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUser();
            }
        });


    }

    void setUser(){
        String name,address,phoneNo,dadName,momName,emContact,guardianName,userAge;
        name = namef.getText().toString();
        address = addressf.getText().toString();
        phoneNo = phoneNof.getText().toString();
        dadName = dadf.getText().toString();
        momName = momf.getText().toString();
        emContact = emconf.getText().toString();
        guardianName = guardianf.getText().toString();
        userAge = agef.getText().toString();

        if(!(name.isEmpty() || address.isEmpty() || phoneNo.isEmpty() || dadName.isEmpty() || momName.isEmpty() || emContact.isEmpty() || guardianName.isEmpty() || userAge.isEmpty() )){
            return;
        }

        User user = new User(name,address,phoneNo,dadName,momName,emContact,guardianName,userAge);

        Intent intent = new Intent(RegisterActivity.this,ChatScreen.class);
        intent.putExtra("user", user);
        startActivity(intent);





    }
}