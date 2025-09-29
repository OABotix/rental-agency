/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.olwethuab.propertyrentalapp;

import java.util.Scanner;

/**
 *
 * @author oabhi
 */
public class PropertyRentalApp {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("PROPERTY RENTALS - 2025");
        System.out.println("*********************************************************************");
        System.out.print("Enter (1) to launch menu or any other key to exit: ");
        String choice = scanner.nextLine();
        
        if (!choice.equals("1")) {
            System.out.println("Thank you for using Property Rentals 2025. Goodbye!");
            scanner.close();
            return;
        }
        
        // Create the Property object - this is the "engine" of our application
        Property propertyManager = new Property();
        
        // Main menu loop
        boolean continueRunning = true;
        while (continueRunning) {
            displayMenu();
            String menuChoice = scanner.nextLine();
            
            switch (menuChoice) {
                case "1":
                    propertyManager.EnterProperty();
                    break;
                case "2":
                    System.out.print("Enter the property id to search: ");
                    String searchId = scanner.nextLine();
                    propertyManager.SearchProperty(searchId);
                    break;
                case "3":
                    System.out.print("Enter the property id to update: ");
                    String updateId = scanner.nextLine();
                    propertyManager.UpdateProperty(updateId);
                    break;
                case "4":
                    System.out.print("Enter the property id to delete: ");
                    String deleteId = scanner.nextLine();
                    propertyManager.DeleteProperty(deleteId);
                    break;
                case "5":
                    propertyManager.PropertyRentalReport();
                    break;
                case "6":
                    propertyManager.ExitPropertyApplication();
                    continueRunning = false;
                    break;
                default:
                    System.out.println("Invalid option! Please try again.");
            }
            
            if (continueRunning) {
                System.out.print("Enter (1) to launch menu or any other key to exit: ");
                String continueChoice = scanner.nextLine();
                if (!continueChoice.equals("1")) {
                    continueRunning = false;
                    System.out.println("Thank you for using Property Rentals 2025. Goodbye!");
                }
            }
        }
        scanner.close();
    }
    
    // Display the main menu
    private static void displayMenu() {
        System.out.println("\nPlease select one of the following menu items:");
        System.out.println("(1) Enter new property.");
        System.out.println("(2) Search for property.");
        System.out.println("(3) Update property.");
        System.out.println("(4) Delete a property.");
        System.out.println("(5) Print property report - 2025");
        System.out.println("(6) Exit Application.");
        System.out.print("Enter your choice: ");
    }
}
