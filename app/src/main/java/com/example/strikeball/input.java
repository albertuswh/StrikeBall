package com.example.strikeball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;


public class input extends AppCompatActivity{

    TextView tvAngka;
    MaterialButton buttonC,Submit;
    MaterialButton button1,button2,button3,button4,button5,button6,button7,button8,button9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

//        nerima get username
        Intent intent = getIntent();
        String usernya = intent.getStringExtra("user");

        Intent pindahcek = new Intent(input.this, cekAngka.class);

        tvAngka = findViewById(R.id.tvAngka);
        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        Submit = findViewById(R.id.submit);
        buttonC = findViewById(R.id.Cbutton);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EnterNum("1");
            button1.setEnabled(false);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("2");
                button2.setEnabled(false);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("3");
                button3.setEnabled(false);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("4");
                button4.setEnabled(false);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("5");
                button5.setEnabled(false);
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("6");
                button6.setEnabled(false);
            }
        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("7");
                button7.setEnabled(false);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("8");
                button8.setEnabled(false);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EnterNum("9");
                button9.setEnabled(false);
            }
        });

        angka = (String) tvAngka.getText();
        jumlahAngka = angka.length();
        if(jumlahAngka != 4){
            Submit.setEnabled(false);
        }

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//          isi PUT

                String angka2 = tvAngka.getText().toString();
                String user2 = usernya;
                String url = "http://192.168.86.29:7000/updatestats";

                JSONObject object = new JSONObject();
                try {
                    object.put("user2",user2);
                    object.put("angka2",angka2);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Toast.makeText(input.this, response.getString("status"), Toast.LENGTH_SHORT).show();
                            String msg = response.getString("status");

                            pindahcek.putExtra("angka2",angka2);
                            pindahcek.putExtra("userini", user2);
                            startActivity(new Intent(pindahcek));
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                        Toast.makeText(input.this, "Masukan angka2 gagal", Toast.LENGTH_SHORT).show();
                    }
                });
                Volley.newRequestQueue(input.this).add(stringReq);


            }
        });
        buttonC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            tvAngka.setText("");
            button1.setEnabled(true);
            button2.setEnabled(true);
            button3.setEnabled(true);
            button4.setEnabled(true);
            button5.setEnabled(true);
            button6.setEnabled(true);
            button7.setEnabled(true);
            button8.setEnabled(true);
            button9.setEnabled(true);
            }
        });

    }

    String angka;
    int jumlahAngka;

    void EnterNum(String q){
            String Nums = tvAngka.getText() + q;
            tvAngka.setText(Nums);
            angka = (String) tvAngka.getText();
            jumlahAngka = angka.length();

            if(jumlahAngka == 4 && !"".equals(angka)){
                Submit.setEnabled(true);
            }else{
                Submit.setEnabled(false);
            }

        }

}