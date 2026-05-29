/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author trang
 */
import tools.Validation;

public class Menu {
    private static final String SEPARATOR = "=====================================================";
    private static final String HEADER = "             CAR SHOWROOM MANAGEMENT MENU             ";

    public static void displayMenu() {
        System.out.println("\n" + SEPARATOR);
        System.out.println(HEADER);
        System.out.println(SEPARATOR);
        System.out.println("  1. List all brands (ascending by name)");
        System.out.println("  2. Add a new brand");
        System.out.println("  3. Search a brand by ID");
        System.out.println("  4. Remove a brand by ID");
        System.out.println("  5. Update a brand by ID");
        System.out.println("  6. List all brands with prices <= input value");
        System.out.println("  7. List all cars (ascending by brand name)");
        System.out.println("  8. Search cars by partial brand name");
        System.out.println("  9. Add a new car");
        System.out.println("  10. Remove a car by ID");
        System.out.println("  11. Update a car by ID");
        System.out.println("  12. List all cars by a specific color");
        System.out.println("  13. List all customers (ascending by ID)");
        System.out.println("  14. Add a new customer");
        System.out.println("  15. Update a customer by ID");
        System.out.println("  16. Save data to files");
        System.out.println("  17. Quit program");
        System.out.println(SEPARATOR);
    }

    public static int getChoice() {
        return Validation.getChoiceInt("Enter your choice (1-17): ", 1, 17);
    }
    
    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printError(String message) {
        System.err.println("Error: " + message);
    }
}