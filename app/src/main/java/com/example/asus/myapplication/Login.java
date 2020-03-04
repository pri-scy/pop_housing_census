package com.example.asus.myapplication;

import android.content.Context;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {


    public static Context context;
    public Button login;
    EditText input_email, input_password;
    private RequestQueue requestQueue;
    private static final String URL = "http://192.168.43.5//ANDROID_REGISTER_LOGIN/Login.php";
    private JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.loginBtn);
        input_email = (EditText) findViewById(R.id.email);
        input_password = (EditText) findViewById(R.id.password);

        requestQueue = Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                JSONObject jsonObject = new JSONObject();
                try{
                    jsonObject.put("Email",input_email.getText().toString());
                    jsonObject.put("Password",input_password.getText().toString());
                }catch(JSONException e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.d("JSON",jsonObject.toString());
                request = new JsonObjectRequest(Request.Method.POST,URL,jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            if(response.getBoolean("error")== false){
                                Toast.makeText(getApplicationContext()," "+response.getString("message"),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),CensusForm.class));
                                finish();

                            } else {
                                Toast.makeText(getApplicationContext(), " " +response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),""+response.toString(),Toast.LENGTH_LONG).show();
                        }

                    }

                }, new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        try{
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();}
                        catch (NullPointerException e){
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                requestQueue.add(request);

            }

        });


    }

}

