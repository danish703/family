package com.bsccsitnepal.myfamily.family;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText username,pass;
    Button button,login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.password);
        button = (Button) findViewById(R.id.btnRegister);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        login_btn = (Button) findViewById(R.id.btnLinkToLoginScreen);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

    }



    public void register(){
        final String email = username.getText().toString().toLowerCase();
        final String password = pass.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(RegisterActivity.this,response,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("email",email);
                params.put("password",password);
                return  params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
