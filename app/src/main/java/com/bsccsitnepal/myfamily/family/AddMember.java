package com.bsccsitnepal.myfamily.family;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddMember extends AppCompatActivity {
   EditText member;
   Button btn;
   String id;
    public static final String mypreference = "mypref";
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);
        member = (EditText) findViewById(R.id.member_email);
        btn = (Button) findViewById(R.id.btnAddmember);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        id = sharedpreferences.getString("id","0");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addmember();
            }
        });
    }

    public void addmember(){
        final String email = member.getText().toString().toLowerCase();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.relation_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddMember.this,response,Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMember.this,error.toString(),Toast.LENGTH_SHORT).show();
                member.setText("");
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("myid",id);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
