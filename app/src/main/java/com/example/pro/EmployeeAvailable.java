package com.example.pro;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import org.json.*;

public class EmployeeAvailable extends AppCompatActivity {

    private Employee account;

    private JSONArray monday = new JSONArray();
    private JSONArray tuesday = new JSONArray();
    private JSONArray wednesday = new JSONArray();
    private JSONArray thursday = new JSONArray();
    private JSONArray friday = new JSONArray();
    private JSONArray saturday = new JSONArray();
    private JSONArray sunday = new JSONArray();

    private JSONObject availabilities = new JSONObject();

    private enum Weekday {MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}
    private Weekday day = Weekday.MONDAY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_available_times);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        account = (Employee) bundle.get("Account");
        getAvailabilities();
        populateDay();
    }

    public void setMonday(View view) {
        updateOldDay();
        day = Weekday.MONDAY;
        populateDay();
    }
    public void setTuesday(View view) {
        updateOldDay();
        day = Weekday.TUESDAY;
        populateDay();
    }
    public void setWednesday(View view) {
        updateOldDay();
        day = Weekday.WEDNESDAY;
        populateDay();
    }
    public void setThursday(View view) {
        updateOldDay();
        day = Weekday.THURSDAY;
        populateDay();
    }
    public void setFriday(View view) {
        updateOldDay();
        day = Weekday.FRIDAY;
        populateDay();
    }
    public void setSaturday(View view) {
        updateOldDay();
        day = Weekday.SATURDAY;
        populateDay();
    }
    public void setSunday(View view) {
        updateOldDay();
        day = Weekday.SUNDAY;
        populateDay();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Account", account);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickSave(View view) throws JSONException {
        updateOldDay();

        availabilities.put("monday", monday);
        availabilities.put("tuesday", tuesday);
        availabilities.put("wednesday", wednesday);
        availabilities.put("thursday", thursday);
        availabilities.put("friday", friday);
        availabilities.put("saturday", saturday);
        availabilities.put("sunday", sunday);

        MyDBHandler dbHandler = new MyDBHandler(this);
        dbHandler.editAvailabilities(account.getUserName(), availabilities.toString());
        account.setAvailabilities(availabilities.toString());
        dbHandler.close();
        Intent intent = new Intent(getApplicationContext(), EmployeeWelcomePage.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Account", account);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void populateDay() {

        CheckBox box;
        String boxID;
        int resID;

        //clears table
        for (int i = 0; i <= 23; i++) {
            boxID = "c" + i;
            resID = getResources().getIdentifier(boxID, "id", getPackageName());
            box = findViewById(resID);
            box.setChecked(false);

        }
        try {

            switch (day) {
                case MONDAY:
                    for (int i = 0; i < monday.length(); i++) {
                        boxID = "c" + monday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case TUESDAY:
                    for (int i = 0; i < tuesday.length(); i++) {
                        boxID = "c" + tuesday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case WEDNESDAY:
                    for (int i = 0; i < wednesday.length(); i++) {
                        boxID = "c" + wednesday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case THURSDAY:
                    for (int i = 0; i < thursday.length(); i++) {
                        boxID = "c" + thursday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case FRIDAY:
                    for (int i = 0; i < friday.length(); i++) {
                        boxID = "c" + friday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case SATURDAY:
                    for (int i = 0; i < saturday.length(); i++) {
                        boxID = "c" + saturday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
                case SUNDAY:
                    for (int i = 0; i < sunday.length(); i++) {
                        boxID = "c" + sunday.get(i);
                        resID = getResources().getIdentifier(boxID, "id", getPackageName());
                        box = findViewById(resID);
                        box.setChecked(true);
                    }
                    break;
            }

        } catch (JSONException e) {
            //TODO
        }

        //TODO: Get availability for current day. Call at end of each day setter method,
        // and (maybe) once at activity creation to populate Monday.

        //potential short-term memory solution for flipping through days repeatedly: populate using the
        //class json arrays, then wipe the array, and rewrite when day is changed.



    }

    private void updateOldDay() {
        switch (day) {
            case MONDAY:
                monday = new JSONArray();
                updateDay(monday);
                break;
            case TUESDAY:
                tuesday = new JSONArray();
                updateDay(tuesday);
                break;
            case WEDNESDAY:
                wednesday = new JSONArray();
                updateDay(wednesday);
                break;
            case THURSDAY:
                thursday = new JSONArray();
                updateDay(thursday);
                break;
            case FRIDAY:
                friday = new JSONArray();
                updateDay(friday);
                break;
            case SATURDAY:
                saturday = new JSONArray();
                updateDay(saturday);
                break;
            case SUNDAY:
                sunday = new JSONArray();
                updateDay(sunday);
                break;
        }
    }

    private void updateDay(JSONArray day) {
        CheckBox box;

        for (int i = 0; i <= 23; i++) {
            String boxID = "c" + i;
            int resID = getResources().getIdentifier(boxID, "id", getPackageName());
            box = findViewById(resID);
            if (box.isChecked()) {
                day.put(i);
            }
        }
    }

    private void getAvailabilities() {
        try {
            availabilities = new JSONObject(account.getAvailabilities());
        } catch (NullPointerException e) {
            //TODO
        } catch (JSONException e) {
            //TODO
        }

        try {
            monday = availabilities.getJSONArray("monday");
        } catch (JSONException e) {

        }
        try {
            tuesday = availabilities.getJSONArray("tuesday");
        } catch (JSONException e) {

        }
        try {
            wednesday = availabilities.getJSONArray("wednesday");
        } catch (JSONException e) {

        }
        try {
            thursday = availabilities.getJSONArray("thursday");
        } catch (JSONException e) {

        }
        try {
            friday = availabilities.getJSONArray("friday");
        } catch (JSONException e) {

        }
        try {
            saturday = availabilities.getJSONArray("saturday");
        } catch (JSONException e) {

        }
        try {
            sunday = availabilities.getJSONArray("sunday");
        } catch (JSONException e) {

        }
    }
}

