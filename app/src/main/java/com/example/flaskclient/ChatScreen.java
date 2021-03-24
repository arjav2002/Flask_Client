package com.example.flaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChatScreen extends AppCompatActivity {

    Button send;
    EditText msg;
    TextView response;
    User user;
    ServerConnection serverConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        send = (Button)findViewById(R.id.send);
        msg = (EditText)findViewById(R.id.message);
        response = (TextView) findViewById(R.id.response);
        serverConnection = new ServerConnection(this, "http://" + "192.168.0.102" + ":" + 5000 + "/");
        user = (User) getIntent().getSerializableExtra("user");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg.getText().toString().isEmpty()) {
                    serverConnection.sendMessage(user, msg.getText().toString());
                }
            }
        });
    }
}