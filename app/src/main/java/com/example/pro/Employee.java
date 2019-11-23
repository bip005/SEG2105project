package com.example.pro;

import java.util.ArrayList;


public class Employee extends Account{

    private int streetNumber;
    private String streetName;
    private String city;
    private String clinic;
    private String payment;
    private String phoneNumber;
    private String availabilities;
    private ArrayList<Service> services;

    public Employee(){
        super();
    }

    public Employee(String name, String lastName, String userName, String password, String email){
        super(name,lastName,userName, password, email);
    }



    public Employee(String name, String lastName, String userName, String password, String email, int streetNumber, String streetName, String city, String clinic, String payment, String phoneNumber, String availabilities, ArrayList<Service> services){
        super(name,lastName,userName, password, email);
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.clinic = clinic;
        this.payment = payment;
        this.phoneNumber = phoneNumber;
        this.availabilities = availabilities;
        this.services = services;
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void setStreetNumber(Integer streetNumber){
        this.streetNumber=streetNumber;
    }
    public void setStreetName(String streetName){
        this.streetName = streetName;
    }
    public void setClinic(String clinic){
        this.clinic=clinic;
    }
    public void setPayment(String payment){
        this.payment=payment;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setCity(String city){
        this.city=city;
    }
    public void setAvailabilities(String availabilities) { this.availabilities = availabilities; }
    public Integer getStreetNumber(){
        return streetNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getClinic() {
        return clinic;
    }
    public String getPayment() {
        return payment;
    }
    public String getCity() {
        return city;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public ArrayList<Service> getServices() { return services; }
    public String getAvailabilities() { return availabilities; }

}