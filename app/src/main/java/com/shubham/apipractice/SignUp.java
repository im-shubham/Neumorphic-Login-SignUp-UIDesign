package com.shubham.apipractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    TextView loginActivity;
    EditText editName,editEmail,editPass;
    Button signUpBtn;
    public static final String url = "http://192.168.1.2/api/userdata.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

//        id linking..
        loginActivity = findViewById(R.id.loginActivity);
        editName = findViewById(R.id.editNameSignUp);
        editEmail = findViewById(R.id.editEmailSignUp);
        editPass = findViewById(R.id.editPassSignUp);
        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(editName.getText().toString(),editEmail.getText().toString(),editPass.getText().toString());
            }
        });



        loginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this,LoginActivity.class));
                finish();
            }
        });
    }

    private void registerUser(String name, String email, String password) {

        RequestQueue requestQueue = Volley.newRequestQueue(this);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("exist")){
                    TastyToast.makeText(SignUp.this,"User Already Exist ",TastyToast.LENGTH_LONG,TastyToast.WARNING);
                }else{
                    TastyToast.makeText(SignUp.this,"Data Inserted ",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);
                }
                editName.setText("");
                editEmail.setText("");
                editPass.setText("");
//                startActivity(new Intent(SignUp.this, MainActivity.class));
//                finish();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                TastyToast.makeText(SignUp.this,"Insertion Failed "+ error.toString(),TastyToast.LENGTH_LONG,TastyToast.ERROR);

            }
        }){
            @NonNull
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> map = new HashMap<>();
                map.put("name",name);
                map.put("email",email);
                map.put("password",password);
                return map;

            }
        };
        requestQueue.add(stringRequest);
    }
}