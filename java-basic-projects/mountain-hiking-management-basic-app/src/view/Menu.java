/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author trang
 */
import tools.Inputter;
import business.InputManager;

public class Menu {
    private InputManager manager;
    private Inputter inputter;

    public Menu(InputManager manager, Inputter inputter) {
        this.manager = manager;
        this.inputter = inputter;
    }
    
    private void showMenu() {
        System.out.println("\nx=== FPTU MOUNTAIN HIKING REGISTRATION PROGRAM ===x");
        System.out.println("---------------------------------------------------");
        System.out.println("1. New Registration");
        System.out.println("2. Update Registration Information");
        System.out.println("3. Display Registered List");
        System.out.println("4. Delete Registration Information");
        System.out.println("5. Search Participants by Name");
        System.out.println("6. Filter Data by Campus");
        System.out.println("7. Filter Data by Mountain Code");
        System.out.println("8. Statistics of Registration Numbers by Location");
        System.out.println("9. Statistics of Location by Maximum/Minimum Participants");
        System.out.println("10. Save Data to File");
        System.out.println("11. Exit the Program");
        System.out.println("---------------------------------------------------");
    }
    
    private void enterProgram(int choice) {
        switch (choice) {
            case 1:
                manager.newRegistration();
                break;
            case 2:
                manager.updateRegistration();
                break;
            case 3:
                manager.displayRegistration();
                break;
            case 4:
                manager.deleteRegistration();
                break;
            case 5:
                manager.search();
                break;
            case 6:
                manager.filterByCampus();
                break;
            case 7:
                manager.filterByMountainCode();
                break;
            case 8:
                manager.displayStatistics();
                break;
            case 9:
                manager.displayMaxMinStatistics();
                break;
            case 10:
                manager.save();
                break;
            case 11:
                manager.exit();
                break;
            default:
                System.out.println("This function is not available.");
                break;
        }
    }
    
    public void run() {
        int choice;
        do {
            showMenu();
            choice = inputter.getChoice();
            enterProgram(choice);
        } while (choice != 11);
    }
}