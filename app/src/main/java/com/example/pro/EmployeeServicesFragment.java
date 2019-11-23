package com.example.pro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class EmployeeServicesFragment extends Fragment {

    Bundle bundle;
    public Service serviceSelected;
    private Employee account;
    private Button addButton;
    private Button deleteButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_services, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();
        account = (Employee) bundle.get("Account");

        //UI changes here
        //Use getActivity() when referring to ServiceProviderWelcomePage
        //ex. instead of this use getActivity()

        //this list view displays the SP own services they provide
        ListView serviceListSP = getActivity().findViewById(R.id.spServicesList);
        final ArrayList<String> listServices = displayServices();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listServices);
        serviceListSP.setAdapter(adapter);
        //final MyDBHandler dbHandler = new MyDBHandler(getActivity());

        //listener that selects a service from the SP list and then it can delete it
        serviceListSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyDBHandler dbHandler = new MyDBHandler(getActivity());
                String serviceString = listServices.get(position);
                String serviceName = serviceString.substring(serviceString.indexOf("[") + 1, serviceString.indexOf("]"));
                serviceSelected = dbHandler.findService(serviceName);
                dbHandler.close();

            }
        });

        addButton = getActivity().findViewById(R.id.spServicesAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddNewService(v);
            }
        });

        deleteButton = getActivity().findViewById(R.id.spServicesDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDelFromSPlist(v);
            }
        });

    }// end onActivityCreated


    //add button that opens edit services activity with all available activities to add to SPown list
    public void onClickAddNewService (View view){

        Intent intent = new Intent(getActivity(), EmployeeEditServices.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    // delete button that deletes ONE OF THE SP OWN SERVICES
    public void onClickDelFromSPlist (View view) {
        if (serviceSelected == null) {
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(getActivity());
        dbHandler.deleteSpService(account.getUserName(), serviceSelected.getName()); //TODO update db handler
        dbHandler.close();

        startActivity(getActivity().getIntent());

    }


    private ArrayList<String> displayServices() {
        String services = "";

        Integer counter2 = 1;
        MyDBHandler dbHandler = new MyDBHandler(getActivity());
        ArrayList<String> serviceList = dbHandler.findAllSpServices(account.getUserName());
        ArrayList<String> listServices = new ArrayList<String>();

        if (serviceList == null) {
            return listServices;
        }

        for (String service : serviceList) {
            services = (counter2.toString() + " [" + service.toString() + "]");

            services = services.concat(" ");

            listServices.add(services);
            counter2++;
        }


        dbHandler.close();
        return listServices;
    }






}

