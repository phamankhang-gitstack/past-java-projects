/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author trang
 */
import java.util.Scanner;
import models.Brand;
import models.BrandList;
import models.CarList;
import models.Customer;
import models.CustomerList;

public class Validation {
    private static final Scanner sc = new Scanner(System.in);
    
    // get a non-empty string input
    public static String getNonEmptyString(String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = sc.nextLine().trim();
            if (input.isEmpty()) System.out.println("Empty input. Please re-enter.");
        } while (input.isEmpty());
        return input;
    }

    // get other types of string
    public static String getOtherString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // getInt for choices
    public static int getChoiceInt(String prompt, int min, int max) {
        int result = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                String input = sc.nextLine().trim();
                result = Integer.parseInt(input);
                if (result >= min && result <= max) {
                    validInput = true;
                } else {
                    System.out.println("Inaccessible choice input.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        return result;
    }
    
    public static int getInt(String prompt, int number) {
        int result = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt);
            try {
                String input = sc.nextLine().trim();
                result = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        return result;
    }
    
    // validate new brand ID + prompt blueprint
    public static String getNewBrandID(String prompt, BrandList list) {
        String id;
        do {
            id = getNonEmptyString(prompt).toUpperCase();
            if (list.searchBrandByID(id) != null) System.out.println("This brand ID already exists.");
        } while (list.searchBrandByID(id) != null);
        return id;
    }

    // validate new car ID + prompt blueprint
    public static String getNewCarID(String prompt, CarList list) {
        String id;
        do {
            id = getNonEmptyString(prompt).toUpperCase();
            if (list.searchCarByID(id) != null) System.out.println("This car ID already exists.");
        } while (list.searchCarByID(id) != null);
        return id;
    }

    // validate new frame ID + prompt blueprint
    public static String getNewFrameID(String prompt, CarList list) {
        String id;
        do {
            id = getNonEmptyString(prompt).toUpperCase();
            // Check format (starts with 'F' and length 6)
            if (!id.matches("^F\\d{5}$")) {
                System.out.println("Invalid frame ID format.");
                continue;
            }
            // Check uniqueness
            if (list.searchCarByFrameID(id) != null) {
                System.out.println("This frame ID already exists.");
                continue;
            }
            break;
        } while (true);
        return id;
    }
    
    // validate new engine ID + prompt blueprint
    public static String getNewEngineID(String prompt, CarList list) {
        String id;
        do {
            id = getNonEmptyString(prompt).toUpperCase();
            // Check format (starts with 'E' and length 6)
            if (!id.matches("^E\\d{5}$")) {
                System.out.println("Invalid engine ID format.");
                continue;
            }
            // Check uniqueness
            if (list.searchCarByEngineID(id) != null) {
                System.out.println("This engine ID already exists.");
                continue;
            }
            break;
        } while (true);
        return id;
    }

    // check if brand exists for new car + prompt blueprint
    public static Brand getExistingBrand(String prompt, BrandList list) {
        String id;
        Brand brand = null;
        do {
            id = getNonEmptyString(prompt);
            brand = list.searchBrandByID(id);
            if (brand == null) System.out.println("Brand ID not found.");
        } while (brand == null);
        return brand;
    }
    
    // validate new price + prompt blueprint
    public static double getNewPrice(String prompt) {
        double price = 0.0;
        boolean validInput = false;
        while (!validInput) {
            String input = getNonEmptyString(prompt);
            // Remove 'B' if present and convert to double
            if (input.toUpperCase().endsWith("B")) input = input.substring(0, input.length() - 1);
            try {
                price = Double.parseDouble(input);
                if (price > 0) {
                    validInput = true;
                } else {
                    System.out.println("Invalid input: Price must be a positive number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format.");
            }
        }
        return price;
    }
    
    public static String getNewCustomerID(String prompt, CustomerList list) {
        String id;
        do {
            id = getNonEmptyString(prompt).toUpperCase();
            if (!id.matches("^CUS\\d{2}$")) {
                System.out.println("Invalid customer ID format.");
                continue;
            }
            // Check uniqueness
            if (list.searchCustomerByID(id) != null) {
                System.out.println("This customer ID already exists.");
                continue;
            }
            break;
        } while (true);
        return id;
    }
    
    public static int getNewPhone(String prompt, CustomerList list) {
        int phone = 0;
        do {
            String input = getNonEmptyString(prompt);
            if (input.length() != 10) {
                System.out.println("Invalid phone number length.");
                continue;
            }
            try {
                phone = Integer.parseInt(input);
                if (phone < 0) {
                    System.out.println("Invalid phone number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                continue;
            }
            break;
        } while (true);
        return phone;
    }

    // get input for update
    public static String getStringForUpdate(String prompt, String currentValue) {
        System.out.print(prompt + " (Current: " + currentValue + ", leave blank to skip): ");
        return sc.nextLine();
    }
    
    // validate price for update + prompt blueprint
    public static double getUpdatedPrice(String prompt, double currentValue) {
        String currentStr = String.format("%.3fB", currentValue);
        double newPrice = -1.0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " (Current: " + currentStr + ", leave blank to skip): ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                newPrice = -1.0;
                validInput = true; 
            } else {
                if (input.toUpperCase().endsWith("B")) input = input.substring(0, input.length() - 1);
                try {
                    newPrice = Double.parseDouble(input);
                    if (newPrice > 0) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid input: Price must be a positive number.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid price format.");
                }
            }
        }
        return newPrice;
    }

    // validate frame ID for update + prompt blueprint
    public static String getUpdatedFrameID(String prompt, String currentValue, CarList list) {
        String newID = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " (Current: " + currentValue + ", leave blank to skip): ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                newID = "";
                validInput = true;
            } else {
                if (!input.matches("^F\\d{5}$")) {
                    System.out.println("Invalid frame ID format.");
                    continue;
                }
                if (!input.equalsIgnoreCase(currentValue) && list.searchCarByFrameID(input) != null) {
                    System.out.println("This frame ID already exists.");
                    continue;
                }
                newID = input;
                validInput = true;
            }
        }
        return newID;
    }
    
    // validate engine ID for update + prompt blueprint
    public static String getUpdatedEngineID(String prompt, String currentValue, CarList list) {
        String newID = "";
        boolean validInput = false;
        while (!validInput) {
            System.out.print(prompt + " (Current: " + currentValue + ", leave blank to skip): ");
            String input = sc.nextLine().trim().toUpperCase();
            if (input.isEmpty()) {
                newID = "";
                validInput = true;
            } else {
                if (!input.matches("^E\\d{5}$")) {
                    System.out.println("Invalid engine ID format.");
                    continue;
                }
                if (!input.equalsIgnoreCase(currentValue) && list.searchCarByEngineID(input) != null) {
                    System.out.println("This engine ID already exists.");
                    continue;
                }
                newID = input;
                validInput = true;
            }
        }
        return newID;
    }
    
    // get Y/N input for confirmation prompts
    public static boolean getConfirmation(String prompt) {
        String input;
        do {
            System.out.print(prompt + " (Y/N): ");
            input = sc.nextLine().trim().toUpperCase();
            if (!input.equals("Y") && !input.equals("N")) {
                System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        } while (!input.equals("Y") && !input.equals("N"));
        return input.equals("Y");
    }
}