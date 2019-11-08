package com.example.crud_mysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button btnlog;
    EditText user, pass;
    String usuario, password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnlog = findViewById(R.id.Btn_iniciar);

        user = findViewById(R.id.TV_usu);
        pass = findViewById(R.id.TV_pas);

        usuario = user.getText().toString();
        password = pass.getText().toString();

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String username = user.getText().toString();
                final String password = pass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (user.getText().toString().length() == 0 || pass.getText().toString().length() == 0){
                                Toast.makeText(MainActivity.this, "Por favor llene los campos", Toast.LENGTH_SHORT).show();
                            }else if (success){
                                Intent intent = new Intent(MainActivity.this, Inicio.class);
                                MainActivity.this.startActivity(intent);
                                user.setText("");
                                pass.setText("");

                            }else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Error Login")
                                        .setNegativeButton("Retry", null)

                                        .create().show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });
    }

    public void registro(View view) {
        Intent intent = new Intent(this,Registro.class);
        startActivity(intent);
    }

}
