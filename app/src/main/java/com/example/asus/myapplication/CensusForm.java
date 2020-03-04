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


public class CensusForm extends AppCompatActivity {
    private EditText name, age, sex, house_num, phone_num;
    private Button submit;
    private Button logout;
    private static String URL_REGISTER="http://192.168.43.5//ANDROID_REGISTER_LOGIN/census_data.php";
    private RequestQueue requestQueue;
    private JsonObjectRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.censusform);

        submit=findViewById(R.id.censusBtn);
        logout=findViewById(R.id.logoutBtn);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        sex=findViewById(R.id.sex);
        house_num=findViewById(R.id.house_number);
        phone_num=findViewById(R.id.phone_number);

        requestQueue = Volley.newRequestQueue(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AlertDialog.Builder builder = new AlertDialog.Builder(CensusForm.this);
                builder.setMessage("Do you want to record this data?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        censusData();

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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }

        });


    }

    public void censusData() {
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("name",name.getText().toString());
            jsonObject.put("age",age.getText().toString());
            jsonObject.put("sex",sex.getText().toString());
            jsonObject.put("house_number",house_num.getText().toString());
            jsonObject.put("phone_number",phone_num.getText().toString());

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
