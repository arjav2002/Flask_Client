package com.example.flaskclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button connect;
    private ServerConnection serverConnection;
    private EditText ipv4Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connect = findViewById(R.id.ipsubmitbtn);
        ipv4Input = findViewById((R.id.ipInput));
        serverConnection = new ServerConnection(this, "http://" + ipv4Input.getText().toString() + ":" + 5000 + "/");

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                if(ipv4Input.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Field is empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                myEdit.putString("ipv4", ipv4Input.getText().toString());
                myEdit.commit();

                Intent intent = new Intent(MainActivity.this,DashBoard.class);
                startActivity(intent);
            }
        });


    }
}