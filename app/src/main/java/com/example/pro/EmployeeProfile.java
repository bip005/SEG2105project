package com.example.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;


public class EmployeeProfile extends AppCompatActivity {
    private Bundle bundle;
    private Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);
        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        employee = (Employee) bundle.get("Account");
        MyDBHandler dbHandler = new MyDBHandler(this);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        if ((bundle != null) && (dbHandler.spProfileExists(employee.getUserName()))) {
            Employee sP = (Employee) bundle.get("Account");
            EditText field = findViewById(R.id.spEditStreetNumber);
            field.setText(Integer.toString(sP.getStreetNumber()));
            field = findViewById(R.id.spEditStreetName);
            field.setText(sP.getStreetName());
            field = findViewById(R.id.spEditCity);
            field.setText(sP.getCity());
            field = findViewById(R.id.spEditPhone);
            field.setText(sP.getPhoneNumber());
        }
    }
    public void onSaveClick(View view){
        EditText field;
        boolean flag= true;
        MyDBHandler dbHandler = new MyDBHandler(this);
        field = findViewById(R.id.spEditStreetName);
        String streetName = field.getText().toString();
        if (!streetName.equals("")){
            if(dbHandler.spProfileExists(employee.getUserName())) {
                dbHandler.editStreetName(employee.getUserName(), streetName);
            }
            employee.setStreetName(streetName);
        }
        else{
            field.getText().clear();
            Toast.makeText(getApplicationContext(), "Street name cannot be empty.", Toast.LENGTH_LONG).show();
            flag = false;
        }
        field = findViewById(R.id.spEditStreetNumber);
        String streetNumber = field.getText().toString();
        if (streetNumber.equals("")){
            field.getText().clear();
            Toast.makeText(getApplicationContext(), "Street number cannot be empty.", Toast.LENGTH_LONG).show();
            flag = false;
        }
        else {
            if (TextUtils.isDigitsOnly(streetNumber)) {
                if (dbHandler.spProfileExists(employee.getUserName())) {
                    dbHandler.editStreetNumber(employee.getUserName(), Integer.parseInt(streetNumber));
                }
                employee.setStreetNumber(Integer.parseInt(streetNumber));

            } else {
                field.getText().clear();
                Toast.makeText(getApplicationContext(), "Street number cannot be have non-numerical characters", Toast.LENGTH_LONG).show();
                flag = false;
            }
        }

        field = findViewById(R.id.spEditCity);
        String city = field.getText().toString();
        if (!city.equals("")){
            if(dbHandler.spProfileExists(employee.getUserName())) {
                dbHandler.editCity(employee.getUserName(), city);
            }
            employee.setCity(city);
        }
        else{
            field.getText().clear();
            Toast.makeText(getApplicationContext(), "City cannot be empty.", Toast.LENGTH_LONG).show();
            flag = false;
        }
        field = findViewById(R.id.spEditPhone);
        String phoneNumber = field.getText().toString();
        if (!phoneNumber.equals("") ){
            String str1 = phoneNumber.substring(0,phoneNumber.length());
            if(TextUtils.isDigitsOnly(str1) ) {
                if(dbHandler.spProfileExists(employee.getUserName())) {
                    dbHandler.editPhoneNumber(employee.getUserName(), phoneNumber);
                }
                employee.setPhoneNumber(phoneNumber);
            }
            else{
                field.getText().clear();
                Toast.makeText(getApplicationContext(), "Phone number should only have digits.", Toast.LENGTH_LONG).show();
                flag = false;
            }
        }
        else{
            field.getText().clear();
            Toast.makeText(getApplicationContext(), "Phone number should be entered.", Toast.LENGTH_LONG).show();
            flag = false;
        }
        if(flag) {
            if (!dbHandler.spProfileExists(employee.getUserName())) {
                dbHandler.completeSpProfile(employee);
                if (dbHandler.spProfileExists(employee.getUserName())) {
                    Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
                    bundle.putSerializable("Account", employee);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Does not exist", Toast.LENGTH_LONG).show();
                }
            } else {
                Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
                bundle.putSerializable("Account", employee);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
        dbHandler.close();
    }
    public void onCancelClick(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this);
        if (!dbHandler.spProfileExists(employee.getUserName())) {
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
            bundle.putSerializable("Account", employee);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        dbHandler.close();
    }

}

