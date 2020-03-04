package com.example.asus.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;


public class Register extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private Button btn_register;
    private ProgressBar loading;
    private static String URL_REGISTER="http://192.168.43.5//ANDROID_REGISTER_LOGIN/register.php";
    private RequestQueue requestQueue;
    private JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register=findViewById(R.id.registerBtn);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        c_password=findViewById(R.id.c_password);

        requestQueue = Volley.newRequestQueue(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setMessage("Do you want to register this user?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        register();

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    public void register() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("Full_Name",name.getText().toString());
            jsonObject.put("Email",email.getText().toString());
            jsonObject.put("Password",password.getText().toString());
            jsonObject.put("C_Password",c_password.getText().toString());

        }catch(JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        Log.d("JSON",jsonObject.toString());
        request = new JsonObjectRequest(Request.Method.POST,URL_REGISTER,jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{

                    if(response.getBoolean("error")== false){
                        Toast.makeText(getApplicationContext()," "+response.getString("message"),Toast.LENGTH_LONG).show();
                        startActivity(new Intent(getApplicationContext(),Login.class));
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
}
