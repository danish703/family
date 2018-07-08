package com.bsccsitnepal.myfamily.family;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class LoginActivity extends AppCompatActivity {
    EditText editusername,editpassword;
    Button loginBtn,registerLinkButtonl;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editusername = (EditText) findViewById(R.id.lemail);
        editpassword = (EditText) findViewById(R.id.lpassword);
        loginBtn = (Button) findViewById(R.id.lbtnLogin);
        registerLinkButtonl = (Button) findViewById(R.id.btnLinkToRegisterScreen);

        registerLinkButtonl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    public void login(){
        final String email = editusername.getText().toString().toLowerCase();
        final String password = editpassword.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String s = response;
                String status = s.substring(10,11);
                if(status.compareTo("1")==0){
                    String ID = s.substring(18,19);
                    sharedPreferences = getApplicationContext().getSharedPreferences("mypref",0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id",ID);
                    editor.commit();
                    Intent i =  new Intent(LoginActivity.this,Dashborad.class);
                    startActivity(i);
                }else{
                    Toast.makeText(LoginActivity.this,"email and password does not match",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
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
