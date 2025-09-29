/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.olwethuab.propertyrentalapp;

/**
 *
 * @author oabhi
 */
public class PropertyModel {
    public String PropertyId;
    public String PropertyAddress;
    public double PropertyRentalAmount;
    public String AgentName;
    
    // Constructor to Role Model
    public PropertyModel(String id, String address, double amount, String agent) {
        this.PropertyId = id;
        this.PropertyAddress = address;
        this.PropertyRentalAmount = amount;
        this.AgentName = agent;
    }
}
