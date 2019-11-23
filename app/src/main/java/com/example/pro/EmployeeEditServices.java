package com.example.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EmployeeEditServices extends AppCompatActivity {

    Bundle bundle;
    private Employee account;
    private Service service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_available_services);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);


        Intent intent = getIntent();
        bundle = intent.getExtras();
        account = (Employee) bundle.get("Account");


        // ALL service list display and service list listener for selecting a service to ad to SP OWN list
        ListView serviceList = findViewById(R.id.spServicesList);
        final ArrayList<String> listServices = displayServices();
        ArrayAdapter adapter2 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listServices);
        serviceList.setAdapter(adapter2);
        final MyDBHandler dbHandler = new MyDBHandler(this);

        serviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String serviceString = listServices.get(position);

                String serviceName = serviceString.substring(serviceString.indexOf("[") + 1, serviceString.indexOf("]"));
                service = dbHandler.findService(serviceName);
                dbHandler.close();
            }
        });


    } //end onCreate

    //button adds from available services to SP OWN list
    public void onClickAddToSPlist(View view) {
        if (service== null) {
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(this);
        ArrayList<String> spServices = dbHandler.findAllSpServices(account.getUserName());
        if(spServices !=null) {
            if (!spServices.contains(service.getName())) {
                dbHandler.addSpService(account.getUserName(), service.getName());
                Intent intent = new Intent(this, EmployeeWelcomePage.class);
                intent.putExtras(bundle);
                startActivity(intent);

            } else {
                Toast.makeText(getApplicationContext(), "Service already provided by this Service Provider.", Toast.LENGTH_LONG).show();
            }
        }else {
            dbHandler.addSpService(account.getUserName(), service.getName());
            Intent intent = new Intent(this, EmployeeWelcomePage.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        dbHandler.close();


    }
    // goes back to ServiceProviderWelcomePage
    public void onClickCancelToFragSP(View view) {

        Intent intent = new Intent(this, EmployeeWelcomePage.class);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    private ArrayList<String> displayServices(){
        String services = "";

        Integer counter2 = 1;
        MyDBHandler dbHandler = new MyDBHandler(this);
        ArrayList<Service> serviceList = dbHandler.findAllServices();
        ArrayList<String> listServices = new ArrayList<String>();

        if (serviceList==null){
            return listServices;
        }

        for(Service service : serviceList) {
            services = (counter2.toString() + " " + service.toString());

            services=services.concat(" ");

            listServices.add(services);
            counter2 ++;
        }


        dbHandler.close();
        return listServices;
    }




}//end ServiceProviderEditServices class
