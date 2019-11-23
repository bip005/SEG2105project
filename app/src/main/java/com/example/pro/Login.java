package com.example.pro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
    }

    public void OnLoginClick(View view){
        EditText userName = findViewById(R.id.userNameField);
        EditText password = findViewById(R.id.passwordField);
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        //Password encryption using SHA512 with Guava
        pass = Hashing.sha512().hashString(pass, Charsets.UTF_8).toString();

        MyDBHandler dbHandler = new MyDBHandler(this);
        Account account = dbHandler.findAccountByUserName(user);
        if (account == null){
            userName.setText("");
            password.setText("");
            Toast.makeText(this, "Username not found in database", Toast.LENGTH_LONG).show();
        }
        else if(account.getPassword().equals(pass)) {
            Bundle bundle = new Bundle();
            if(account instanceof Admin){
                Intent intent = new Intent(getApplicationContext(), AdminWelcomePage.class);
                bundle.putSerializable("Account", account);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
            if(account instanceof Employee){
                if(dbHandler.spProfileExists(account.getUserName())) {
                    Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
                    Employee sp = dbHandler.findServiceProvider(user);
                    bundle.putSerializable("Account", sp);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else{
                    Intent intent = new Intent(getApplicationContext(), EmployeeProfile.class);
                    bundle.putSerializable("Account", account);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }

        }
        else{
            userName.setText("");
            password.setText("");
            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_LONG).show();
        }
    }
}
