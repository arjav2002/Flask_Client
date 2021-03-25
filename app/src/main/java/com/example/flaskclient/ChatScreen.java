package com.example.flaskclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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

        SharedPreferences pref = getSharedPreferences("MySharedPref",MODE_PRIVATE);

        serverConnection = new ServerConnection(this, "http://" +pref.getString("ipv4","localhost")+ ":" + 5000 + "/");

        user = (User) getIntent().getExtras().getParcelable("user");
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg.getText().toString().isEmpty()) {
                    String responseString = serverConnection.sendMessage(user, msg.getText().toString());
                    try {
                        String[] latlng = responseString.split(", ");
                        double lat = Double.parseDouble(latlng[0]);
                        double lng = Double.parseDouble(latlng[1]);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lng));
                        startActivity(intent);
                    } catch(Exception e) {
                        response.setText(responseString);
                    }
                }
            }
        });
    }
}