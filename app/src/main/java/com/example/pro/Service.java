package com.example.pro;

import java.io.Serializable;

public class Service implements Serializable {
    private String name;
    private Category category;

    public Service(String name, String categorySpin) {
        this.name = name;
        this.category = AdminServiceEditor.catMap.get(categorySpin);

    }

    public String getName(){
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

