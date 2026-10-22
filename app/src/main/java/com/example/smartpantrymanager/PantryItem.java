package com.example.smartpantrymanager;

public class PantryItem {
    private String name;
    private String details;

    public PantryItem(String name, String details) {
        this.name = name;
        this.details = details;
    }
    public String getName() { return name; }
    public String getDetails() { return details; }
}