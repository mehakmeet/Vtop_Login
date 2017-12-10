package com.example.mehakmeet.loginadg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    String key="ABCDEFG";
    String BASE_URL = "http://young-ravine-50082.herokuapp.com";

    EditText regno;
    EditText pass;
    Button login;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regno = (EditText) findViewById(R.id.regn);
        pass = (EditText) findViewById(R.id.pass);
        login = (Button) findViewById(R.id.login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                insert();
            }
        });

    }


    public void insert() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        RegisterAPI api = adapter.create(RegisterAPI.class);

        api.insert(
                regno.getText().toString(),pass.getText().toString(),
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        BufferedReader reader=null;
                        String resp="";
                        try {

                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            resp = reader.readLine();
                            Log.i("RESPONSE",resp);

                           JSONObject jObj = new JSONObject(resp);
                            String result_s=jObj.getString("result");
                            JSONObject code_obj=new JSONObject(result_s);
                            String code_s=code_obj.getString("code");
                            int code=Integer.parseInt(code_s) ;
                            Log.d("result", "" + code);

                            progressBar.setVisibility(View.INVISIBLE);
                            if(code == 200){
                                Toast.makeText(getApplicationContext(), "Successfully logged in", Toast.LENGTH_SHORT).show();
                            } else if(code == 500){
                                Toast.makeText(getApplicationContext(), "Your Reg. No. or Password is incorrect ", Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Login failed... ", Toast.LENGTH_LONG).show();

                            }

                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } /*catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }*/ catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

}
