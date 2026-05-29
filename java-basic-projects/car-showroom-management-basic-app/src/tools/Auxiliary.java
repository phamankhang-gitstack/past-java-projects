/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

/**
 *
 * @author trang
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import models.Brand;

public class Auxiliary {
    private static final String FLAG_FILE_NAME = "intentional_quit.flag";
    private static final Path FLAG_PATH = Paths.get(System.getProperty("user.home"), ".02P32carshowroom", FLAG_FILE_NAME);

    // create the flag file to signal an intentional quit without saving
    public static void createQuitFlag() {
        try {
            if (!Files.exists(FLAG_PATH)) {
                // ensure the path exists
                Path parent = FLAG_PATH.getParent();
                if (parent != null && !Files.exists(parent)) Files.createDirectories(parent);
                Files.createFile(FLAG_PATH);
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not create intentional quit flag file: " + e.getMessage());
        }
    }

    // search flag file and attempts to delete if found, called on program startup (in loadData) or after a successful save
    public static boolean checkAndClearQuitFlag() {
        try {
            if (Files.exists(FLAG_PATH)) {
                try {
                    Files.deleteIfExists(FLAG_PATH);
                } catch (IOException e) {
                    System.err.println("Warning: Could not delete intentional quit flag file: " + e.getMessage());
                }
                return true;
            }
        } catch (SecurityException ex) {
            System.err.println("Security exception encountered when checking quit flag: " + ex.getMessage());
        }
        return false;
    }

    // check if the flag has been set
    public static boolean checkQuitFlag() {
        return Files.exists(FLAG_PATH);
    }
    
    // placeholder static function
    public static double calculateCarPriceByColor(Brand brand, String color) {
        double basePrice = brand.getPrice();
        String lowerColor = color.toLowerCase();
        if (lowerColor.equals("white") || lowerColor.equals("grey") || lowerColor.equals("gray") || lowerColor.equals("black")) {
            return basePrice * 0.9;
        } else {
            return basePrice;
        }
    }
}
