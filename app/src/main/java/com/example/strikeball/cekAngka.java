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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class cekAngka extends AppCompatActivity{

    TextView tvAngka,numstrike,numball;
    MaterialButton buttonC,Submit;
    MaterialButton button1,button2,button3,button4,button5,button6,button7,button8,button9;
    private AngkaWrapper angkaWrapper;
    private SesIDWrapper sesIDWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_angka);

        angkaWrapper = new AngkaWrapper();
        sesIDWrapper = new SesIDWrapper();

        Intent intent = getIntent();
        String user2 = intent.getStringExtra("userini");
        int angka2 = Integer.parseInt(intent.getStringExtra("angka2"));

//        GET ANGKA2 ///////////////
        String url = "http://192.168.86.29:7000/take1";

        JSONObject object = new JSONObject();
        try {
            object.put("us2",user2);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
//                    Toast.makeText(cekAngka.this, response.getString("response"), Toast.LENGTH_SHORT).show();
                    angkaWrapper.setAngka1Value(Integer.parseInt(response.getString("response")));
                    sesIDWrapper.setSesIDValue(Integer.parseInt(response.getString("response2")));
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                Toast.makeText(cekAngka.this, "Ambil angka 1 gagal", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(cekAngka.this).add(stringReq);

        numstrike = findViewById(R.id.jumStrike);
        numball = findViewById(R.id.jumBall);
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
//PENGECEKAN UNTUK STRIKES DAN BALL
                int num1 = angkaWrapper.getAngka1Value(); ;
                int num2 = Integer.parseInt(tvAngka.getText().toString());

//                Toast.makeText(cekAngka.this, String.valueOf(num1), Toast.LENGTH_SHORT).show();
//                Toast.makeText(cekAngka.this, String.valueOf(num2), Toast.LENGTH_SHORT).show();
                int strike = 0;
                int ball = 0;

                for (int i = 0; i < 4; i++) {
                    int digit1 = getDigit(num1, i);
                    int digit2 = getDigit(num2, i);

                    if (digit1 == digit2) {
                        strike++;
                    } else if (containsDigit(num1, digit2)) {
                        ball++;
                    }
                }

                System.out.println("Strikes: " + strike);
                System.out.println("Balls: " + ball);

                numstrike.setText(String.valueOf(strike));
                numball.setText(String.valueOf(ball));
//END PENGECEKAN STRIKE BALL ==================================

                if(strike == 4){

                    int room = sesIDWrapper.getSesIDValue();
                    String url = "http://192.168.86.29:7000/updategame";

                    JSONObject object = new JSONObject();
                    try {
                        object.put("winner",user2);
                        object.put("sesID",room);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                            Toast.makeText(cekAngka.this, response.getString("status"), Toast.LENGTH_SHORT).show();
//                                String msg = response.getString("status");

                                Intent winnings = new Intent(cekAngka.this, win.class);
                                startActivity(new Intent(winnings));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                            Toast.makeText(cekAngka.this, "Masukan winner gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Volley.newRequestQueue(cekAngka.this).add(stringReq);

                }else{
                    String url = "http://192.168.86.29:7000/cekWin";
                    int room = sesIDWrapper.getSesIDValue();

                    JSONObject object = new JSONObject();
                    try {
                        object.put("sesID",room);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    JsonObjectRequest stringReq = new JsonObjectRequest(Request.Method.POST, url, object, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                int stats = response.getInt("response");
                                if (stats==0){
                                    Intent losings = new Intent(cekAngka.this, lose.class);
                                    startActivity(new Intent(losings));
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("apiresult", error.getMessage() != null ? error.getMessage() : "Unknown error");
                            Toast.makeText(cekAngka.this, "Cek status gagal", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Volley.newRequestQueue(cekAngka.this).add(stringReq);

                }


            }//AKHIR DARI ON CLICK
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

    static int getDigit(int number, int position) {
        return (number / (int) Math.pow(10, position)) % 10;
    }
    static boolean containsDigit(int number, int digit) {
        while (number > 0) {
            int currentDigit = number % 10;
            if (currentDigit == digit) {
                return true;
            }
            number /= 10;
        }
        return false;
    }
    private static class AngkaWrapper {
        private int angka1Value;

        public int getAngka1Value() {
            return angka1Value;
        }

        public void setAngka1Value(int angka1Value) {
            this.angka1Value = angka1Value;
        }
    }

    private static class SesIDWrapper {
        private int sesIDValue;

        public int getSesIDValue() { return sesIDValue;}
        public void setSesIDValue(int sesIDValue) {this.sesIDValue = sesIDValue;}
    }

}