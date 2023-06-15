package com.example.strikeball;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    Button logB, signB;
    Intent pindahinput;
    EditText user, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = findViewById(R.id.userET);
        pass = findViewById(R.id.passET);
        logB = findViewById(R.id.loginbtn);
        signB = findViewById(R.id.signupbtn);
        pindahinput = new Intent(Login.this, input.class);

        logB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                String url = "http://192.168.86.29:7000/login";

                JSONObject object = new JSONObject();
                try {
                    object.put("username",username);
                    object.put("password",password);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(Login.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                            String msg = response.getString("message");

                            pindahinput.putExtra("user",username);
                            startActivity(new Intent(pindahinput));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                        Toast.makeText(Login.this, "Login gagal", Toast.LENGTH_SHORT).show();
                    }
                });
                Volley.newRequestQueue(Login.this).add(stringReq);
            }
        });


        signB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = user.getText().toString();
                String password = pass.getText().toString();
                String url = "http://192.168.86.29:7000/signup";

                JSONObject object = new JSONObject();
                try {
                    object.put("username",username);
                    object.put("password",password);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(Login.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                            String msg = response.getString("status");
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                        Toast.makeText(Login.this, "Sign Up gagal", Toast.LENGTH_SHORT).show();
                    }
                });
                Volley.newRequestQueue(Login.this).add(stringReq);
            }
        });

    }
}