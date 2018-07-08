package com.bsccsitnepal.myfamily.family;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class Dashborad extends AppCompatActivity {
    Button add_btn,view_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashborad);
        view_btn = (Button) findViewById(R.id.location_view);
        add_btn = (Button) findViewById(R.id.addbtn);


        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashborad.this, MapsActivity.class);
                startActivity(i);
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Dashborad.this,AddMember.class);
                startActivity(i);
            }
        });
    }
}
