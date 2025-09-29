/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.olwethuab.propertyrentalapp;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author oabhi
 * The class file will all the methods
 */
public class Property {
    // Each Property instance has its own filing cabinet and scanner
    private ArrayList<PropertyModel> propertyList = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    
    // Method to enter a new property
    public void EnterProperty() {
        System.out.println("\nENTER A NEW PROPERTY FOR RENTAL");
        System.out.println("*******************************");
        
        // Get property ID
        System.out.print("Enter the property id: ");
        String id = scanner.nextLine();
        
        // Check if ID already exists
        for (PropertyModel property : propertyList) {
            if (property.PropertyId.equals(id)) {
                System.out.println("Property ID already exists! Please use a unique ID.");
                return;
            }
        }
        
        // Get property address
        System.out.print("Enter the property address: ");
        String address = scanner.nextLine();
        
        // Get and validate rental amount
        double rentalAmount = PropertyAmountValidation();
        if (rentalAmount == -1) return; // Validation failed
        
        // Get agent name
        System.out.print("Enter the property agent: ");
        String agent = scanner.nextLine();
        
        // Create new property and add to list
        PropertyModel newProperty = new PropertyModel(id, address, rentalAmount, agent);
        propertyList.add(newProperty);
        
        System.out.println("New property processed successfully!!!");
    }
    
    // Method to validate rental amount
    public double PropertyAmountValidation() {
        while (true) {
            System.out.print("Enter the property rental price per month: ");
            String input = scanner.nextLine();
            
            try {
                double amount = Double.parseDouble(input);
                if (amount >= 1500) {
                    return amount; // Valid amount
                } else {
                    System.out.println("You have entered an invalid amount!!!");
                    System.out.println("Please re-enter the property rental amount (your amount is too little) >> ");
                }
            } catch (NumberFormatException e) {
                System.out.println("You have entered an invalid amount!!!");
                System.out.println("Please re-enter the property rental amount using numbers only >> ");
            }
        }
    }
    
    // Method to search for a property
    public void SearchProperty(String idToFind) {
        for (PropertyModel property : propertyList) {
            if (property.PropertyId.equals(idToFind)) {
                System.out.println("---PROPERTY ID: " + property.PropertyId);
                System.out.println("PROPERTY ADDRESS: " + property.PropertyAddress);
                System.out.println("PROPERTY RENTAL AMOUNT: R" + property.PropertyRentalAmount);
                System.out.println("PROPERTY AGENT: " + property.AgentName);
                System.out.println("---");
                return;
            }
        }
        System.out.println("---Rental property with property Id: " + idToFind + " was not found!");
        System.out.println("----------");
    }
    
    // Method to update a property
    public void UpdateProperty(String idToUpdate) {
        PropertyModel propertyToUpdate = null;
        
        // Find the property
        for (PropertyModel property : propertyList) {
            if (property.PropertyId.equals(idToUpdate)) {
                propertyToUpdate = property;
                break;
            }
        }
        
        if (propertyToUpdate == null) {
            System.out.println("Property with ID " + idToUpdate + " not found!");
            return;
        }
        
        // Display current values and get updates
        System.out.print("Enter the property address [" + propertyToUpdate.PropertyAddress + "]: ");
        String newAddress = scanner.nextLine();
        if (!newAddress.isEmpty()) {
            propertyToUpdate.PropertyAddress = newAddress;
        }
        
        System.out.print("Enter the property rental amount [" + propertyToUpdate.PropertyRentalAmount + "]: ");
        String amountInput = scanner.nextLine();
        if (!amountInput.isEmpty()) {
            try {
                double newAmount = Double.parseDouble(amountInput);
                if (newAmount >= 1500) {
                    propertyToUpdate.PropertyRentalAmount = newAmount;
                } else {
                    System.out.println("Invalid amount! Must be at least R1500. Keeping old value.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Keeping old rental amount.");
            }
        }
        
        System.out.print("Enter the property agent [" + propertyToUpdate.AgentName + "]: ");
        String newAgent = scanner.nextLine();
        if (!newAgent.isEmpty()) {
            propertyToUpdate.AgentName = newAgent;
        }
        
        System.out.println("Property updated successfully!");
    }
    
    // Method to delete a property
    public void DeleteProperty(String idToDelete) {
        PropertyModel propertyToDelete = null;
        
        // Find the property
        for (PropertyModel property : propertyList) {
            if (property.PropertyId.equals(idToDelete)) {
                propertyToDelete = property;
                break;
            }
        }
        
        if (propertyToDelete == null) {
            System.out.println("Property with ID " + idToDelete + " not found!");
            return;
        }
        
        // Ask for confirmation
        System.out.print("Are you sure you want to delete property " + idToDelete + " from the system, forever ? Yes (y) to delete: ");
        String confirmation = scanner.nextLine();
        
        if (confirmation.equalsIgnoreCase("y")) {
            propertyList.remove(propertyToDelete);
            System.out.println("---Property with Property Id: " + idToDelete + " WAS deleted!");
            System.out.println("----------");
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    
    // Method to generate property report
    public void PropertyRentalReport() {
        if (propertyList.isEmpty()) {
            System.out.println("No properties found in the system.");
            return;
        }
        
        System.out.println("\nPROPERTY RENTAL REPORT - 2025");
        int propertyCount = 1;
        for (PropertyModel property : propertyList) {
            System.out.println("Property " + propertyCount);
            System.out.println("---PROPERTY ID: " + property.PropertyId);
            System.out.println("PROPERTY ADDRESS: " + property.PropertyAddress);
            System.out.println("PROPERTY RENTAL AMOUNT: R" + property.PropertyRentalAmount);
            System.out.println("PROPERTY AGENT: " + property.AgentName);
            System.out.println("----------");
            propertyCount++;
        }
    }
    
    // Method to exit application
    public void ExitPropertyApplication() {
        System.out.println("Thank you for using Property Rentals 2025. Goodbye!");
        scanner.close();
    }
    
    // Getter for propertyList (optional - for future use)
    public ArrayList<PropertyModel> getPropertyList() {
        return propertyList;
    }

    public int getPropertyCount() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
