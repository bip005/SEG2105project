package com.example.pro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.ViewSwitcher;

import java.util.ArrayList;

public class AdminWelcomePage extends AppCompatActivity {

    Bundle bundle;
    private DrawerLayout mDrawerLayout;
    private Service service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        setupNavigationMenu();

        Intent intent = this.getIntent();
        bundle = intent.getExtras();

        //Add header to navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view);
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerUsername = (TextView) navHeaderView.findViewById(R.id.headerUsername);
        headerUsername.setText(extractAccount());
        TextView headerRole = (TextView) navHeaderView.findViewById(R.id.headerRole);
        headerRole.setText("Admin");

        //Accounts List Screen
        ListView accountList = findViewById(R.id.accountList);
        final ArrayList<String> listAccounts = displayAccounts();
        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1, listAccounts);
        accountList.setAdapter(adapter1);

        //Service List Screen
        ListView serviceList = findViewById(R.id.serviceList);
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
    }

    public void onClickEdit(View view) {
        if (service == null) {
            return;
        }
        Intent intent = new Intent(getApplicationContext(), AdminServiceEditor.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Service", service);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onClickDelete(View view) {
        if (service == null) {
            return;
        }
        MyDBHandler dbHandler = new MyDBHandler(this);
        dbHandler.deleteService(service.getName());

        finish();
        startActivity(getIntent());
    }

    private ArrayList<String> displayAccounts(){
        String accounts = "";

        Integer counter = 1;
        MyDBHandler dbHandler = new MyDBHandler(this);
        ArrayList<Account> accountList = dbHandler.findAllAccounts();
        ArrayList<String> listAccounts = new ArrayList<String>();

        for(Account account : accountList) {
            accounts = (counter.toString() + " " + account.toString());
            if (account instanceof Admin) {
                accounts = accounts.concat(" Type: Admin");
            } if (account instanceof Employee) {
                accounts = accounts.concat(" Type: Employee");
            }
            listAccounts.add(accounts);
            counter ++;
        }

        dbHandler.close();
        return listAccounts;
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

    private String extractAccount(){
        Admin admin = (Admin) bundle.get("Account");
        return admin.getUserName();
    }

    public void OnClickLogOut(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onClickAddNewService(View view) {
        Intent intent = new Intent(getApplicationContext(), AdminServiceEditor.class);
        startActivity(intent);
    }
    public void setupNavigationMenu(){
        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        ViewSwitcher viewSwitcher = findViewById(R.id.viewSwitcher);
                        LinearLayout accountsView = findViewById(R.id.admin_accounts_view);
                        RelativeLayout servicesView = findViewById(R.id.admin_services_view);

                        if (menuItem.getItemId() == R.id.nav_accounts && viewSwitcher.getCurrentView() != accountsView){
                            viewSwitcher.showNext();
                            return true;
                        }
                        else if (menuItem.getItemId() == R.id.nav_services && viewSwitcher.getCurrentView() != servicesView){
                            viewSwitcher.showPrevious();
                            return true;
                        }
                        else if (menuItem.getItemId() == R.id.nav_logout){
                            OnClickLogOut(findViewById(R.id.drawer_layout));
                            return true;
                        }
                        return true;
                    }
                }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
