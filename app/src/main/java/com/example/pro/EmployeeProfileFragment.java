package com.example.pro;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class EmployeeProfileFragment extends Fragment{

    Bundle bundle;
    private Employee employee;
    private Button editButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_employee_profile, container, false);
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Intent intent = getActivity().getIntent();
        bundle = intent.getExtras();
        employee = (Employee) bundle.get("Account");

        TextView name = getView().findViewById(R.id.spNameField);
        name.setText(extractAccount(employee)[0]);
        TextView lastName = getView().findViewById(R.id.spLastNameField);
        lastName.setText(extractAccount(employee)[1]);
        TextView userName = getView().findViewById(R.id.spUserNameField);
        userName.setText(extractAccount(employee)[2]);
        TextView email = getView().findViewById(R.id.spEmailField);
        email.setText(extractAccount(employee)[3]);
        TextView address = getView().findViewById(R.id.spAddressField);
        if (!extractAccount(employee)[5].equals("")) {
            String setText = extractAccount(employee)[4] +" " + extractAccount(employee)[6] + " Apartment " + extractAccount(employee)[5] + " " + extractAccount(employee)[7] + " " + extractAccount(employee)[8];
            address.setText(setText);
        }
        else{
            String setText = extractAccount(employee)[4] +" " + extractAccount(employee)[6] + " " + extractAccount(employee)[7] + " " + extractAccount(employee)[8];
            address.setText(setText);
        }
        TextView phone = getView().findViewById(R.id.spPhoneField);
        phone.setText(extractAccount(employee)[9]);

        editButton = getActivity().findViewById(R.id.spEditButton);
        editButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                onEditClick(v);
            }
        });
    }

    private String[] extractAccount(Employee employee){
        String[] str = new String[13];
        str[0] = employee.getName();
        str[1] = employee.getLastName();
        str[2] = employee.getUserName();
        str[3] = employee.getEmail();
        str[4] = employee.getStreetNumber().toString();
        str[5] = employee.getStreetName();
        str[6] = employee.getCity();
        str[7] = employee.getPhoneNumber();
        return str;
    }

    public void onEditClick(View view){
        Intent intent = new Intent(getActivity(), EmployeeProfile.class);
        bundle.putSerializable("Account", this.employee);
        intent.putExtras(bundle);
        startActivity(intent);
    }


}

