package com.example.pro;

import java.util.HashMap;
import java.io.Serializable;
import java.util.Map;

public class Category implements Serializable {
    private String label;
    private Map<String, Service> services;

    public Category(String label){
        this.label = label;
        this.services = new HashMap<String, Service>();

    }


    public void addService(Service service){

        this.services.put(service.getName(), service);
    }




    public Map<String, Service> getAllServices(){
        return services;
    }

    public Service getService(String name){
        return services.get(name);
    }

    public boolean containsService(Service service){
        return services.containsValue(service);
    }

    public String getLabel() {
        return this.label;
    }

}