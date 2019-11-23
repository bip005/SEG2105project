package com.example.pro;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EmployeeWelcomePage extends AppCompatActivity {

    public static Bundle bundle;
    private DrawerLayout mDrawerLayout;
    Employee account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_welcome_page);

        Intent intent = this.getIntent();
        bundle = intent.getExtras();
        account = (Employee) bundle.get("Account");

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame_sp, new EmployeeProfileFragment());
        ft.commit();

        setupNavigationMenu();
        //Add header to navigation drawer
        NavigationView navigationView = findViewById(R.id.nav_view2);
        View navHeaderView = navigationView.inflateHeaderView(R.layout.nav_header);
        TextView headerUsername = (TextView) navHeaderView.findViewById(R.id.headerUsername);
        headerUsername.setText(extractAccount());
        TextView headerRole = (TextView) navHeaderView.findViewById(R.id.headerRole);
        headerRole.setText("Employee");
    }

    private String extractAccount(){
        Employee employee = (Employee) bundle.get("Account");
        return employee.getUserName();
    }

    public void OnClickLogOut(View view){

        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivity(intent);
        finish();

    }

    public void setupNavigationMenu(){
        //Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();

                        int id = menuItem.getItemId();
                        Fragment fragment = null;
                        Bundle bundle1 = new Bundle();



                        if (id == R.id.nav_sp_profile){
                            fragment = new EmployeeProfileFragment();
                        } else if (id == R.id.nav_sp_services){
                            fragment = new EmployeeServicesFragment();
                        } else if (id == R.id.nav_sp_available){
                            fragment = new EmployeeAvailableFragment();
                        } else if (id == R.id.nav_sp_logout){
                            OnClickLogOut(findViewById(R.id.drawer_layout));
                            return true;
                        }

                        if (fragment != null){
                            FragmentTransaction ft = getFragmentManager().beginTransaction();
                            ft.replace(R.id.content_frame_sp, fragment);
                            ft.commit();

                        }
                        return true;
                    }
                }
        );
        navigationView.getMenu().getItem(0).setChecked(true);
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

    public void onClickEditAvailability(View view) {
        Intent intent = new Intent(getApplicationContext(), EmployeeAvailable.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}


