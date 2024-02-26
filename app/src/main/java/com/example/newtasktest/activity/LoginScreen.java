package com.example.newtasktest.activity;

import static com.example.newtasktest.util.appConstants.Email;
import static com.example.newtasktest.util.appConstants.Flag;
import static com.example.newtasktest.util.appConstants.My_preference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.newtasktest.R;

public class LoginScreen extends AppCompatActivity {

    EditText edt_email, edt_pwd;
    Button btn_submit, btn_clear;
    String email, password;

    RelativeLayout relativeLayout;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        edt_email= findViewById(R.id.edt_email);
        edt_pwd= findViewById(R.id.edt_pass);
        btn_submit= findViewById(R.id.btn_submit);
        btn_clear= findViewById(R.id.btn_clear);
        relativeLayout= findViewById(R.id.relativelogin);

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(v);
            }
        });


//       submit button onclick listener
        btn_submit.setOnClickListener(v -> {
            if(validate()){
                flag=1;
                //create Shared preference
                SharedPreferences sharedPreferences= getSharedPreferences(My_preference,MODE_PRIVATE);
                SharedPreferences.Editor edit= sharedPreferences.edit();
                //Assigning values in Shared preferences
                edit.putInt(Flag,flag);
                edit.putString(Email,email);
                edit.apply();
                Intent intent= new Intent(LoginScreen.this,MainActivity.class);
                intent.putExtra("Email",email);
                intent.putExtra("Password",password);
                startActivity(intent);
                finish();

            }
        });

        btn_clear.setOnClickListener(v -> {
            edt_email.setText("");
            edt_pwd.setText("");
        });

    }





    //method to validate and open another activity
    public boolean validate(){

        boolean bool= false;
        email= edt_email.getText().toString();
        password=edt_pwd.getText().toString();
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(edt_pwd.getText().length()>3){
                bool= true;
            }else edt_pwd.setError(getString(R.string.err_pwd));
        }else edt_email.setError(getString(R.string.err_email));
       return  bool;
    }

    private  void hideKeyboard(View v){
        InputMethodManager im=(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}