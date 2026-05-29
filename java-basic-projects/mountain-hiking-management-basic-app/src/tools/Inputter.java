/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author trang
 */
import java.util.Map;
import java.util.List;
import java.util.Scanner;
import models.Student;
import models.Mountain;
import models.MountainCode;

public class Inputter {
    private Scanner sc;
    
    public Inputter() {
        this.sc = new Scanner(System.in);
    }

    public String getString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }
    
    public int getChoice() {
        int choice = 0;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }
        }
        return choice;
    }

    public String getValidId(List<Student> students) {
        String id;
        boolean isValid;
        do {
            id = getString("Enter Student ID (FPT University format): ");
            isValid = Validation.checkId(id);
            if (isValid) {
                if (!Validation.checkDuplicate(id, students, "id")) {
                    System.out.println("This ID is already registered.");
                    isValid = false;
                }
            } else System.out.println("Invalid student ID.");
        } while (!isValid);
        return id.toUpperCase();
    }

    public String getValidName() {
        String name;
        do {
            name = getString("Enter Name (2-20 characters): ");
            if (!Validation.checkName(name)) {
                System.out.println("Invalid name.");
            }
        } while (!Validation.checkName(name));
        return name;
    }

    public String getValidPhone(List<Student> students) {
        String phone;
        boolean isValid;
        do {
            phone = getString("Enter Phone Number (Vietnamese format): ");
            isValid = Validation.checkPhone(phone); 
            if (isValid) {
                if (!Validation.checkDuplicate(phone, students, "phone")) {
                    System.out.println("This phone number is already registered.");
                    isValid = false;
                }
            } else System.out.println("Invalid phone number format.");
        } while (!isValid);
        return phone;
    }

    public String getValidEmail(List<Student> students) {
        String email;
        boolean isValid;
        do {
            email = getString("Enter Email (standard email format): ");
            isValid = Validation.checkEmail(email);
            if (isValid) {
                if (!Validation.checkDuplicate(email, students, "email")) {
                    System.out.println("This email is already registered.");
                    isValid = false;
                }
            } else System.out.println("Invalid email format.");
        } while (!isValid);
        return email;
    }

    public String getValidMountainCode(Map<String, Mountain> mountainMap) {
        String mountainCode;
        do {
            mountainCode = getString("Enter Mountain Code: ");
            if (!Validation.checkMountainCode(mountainCode, mountainMap)) {
                System.out.println("Invalid mountain code.");
            }
        } while (!Validation.checkMountainCode(mountainCode, mountainMap));
        return new MountainCode(mountainCode).getCode();
    }
    
    public String getConfirmation(String prompt) {
        String input;
        while (true) {
            input = getString(prompt).toUpperCase();
            if (input.equals("Y") || input.equals("N")) return input;
            System.out.println("Invalid input. Please enter 'Y' for Yes or 'N' for No.");
        }
    }
}