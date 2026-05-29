/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author trang
 */
import java.util.List;
import models.Brand;
import models.BrandList;
import models.CarList;
import tools.Validation;

public class BrandView {
    private static final String BRAND_TABLE_SEPARATOR = "----------------------------------------------------------------------------------------------";
    private CarList carList; 
    
    public BrandView(CarList carList) { // NEW CONSTRUCTOR
        this.carList = carList;
    }
    
    public void displayBrandList(List<Brand> brands) {
        if (brands.isEmpty()) {
            Menu.printMessage("No brands available to display.");
            return;
        }
        System.out.println(BRAND_TABLE_SEPARATOR);
        System.out.printf("| %-8s | %-35s | %-15s | %-10s | %-10s |\n", "ID", "Name", "Sound Brand", "Price", "Car Count");
        System.out.println(BRAND_TABLE_SEPARATOR);
        for (Brand b : brands) {
            System.out.printf("| %-8s | %-35s | %-15s | %-10s | %-10s |\n", 
                    b.getBrandID(), b.getBrandName(), b.getSoundBrand(), String.format("%.3fB", b.getPrice()), carList.searchCarsByBrandID(b.getBrandID()).size());
        }
        System.out.println(BRAND_TABLE_SEPARATOR);
    }
    
    public void displayBrand(Brand brand) {
        System.out.println("\n------------ BRAND FOUND ------------");
        String priceStr = String.format("%.3fB", brand.getPrice());
        System.out.printf("  Brand ID:    %s%n", brand.getBrandID());
        System.out.printf("  Brand Name:  %s%n", brand.getBrandName());
        System.out.printf("  Sound Brand: %s%n", brand.getSoundBrand());
        System.out.printf("  Price:       %s%n", priceStr);
        System.out.println("-------------------------------------");
    }
    
    
    public Brand getNewBrand(BrandList brandList) {
        Menu.printMessage("\n---- ADD A NEW BRAND ----");
        String id = Validation.getNewBrandID("Enter new Brand ID: ", brandList);
        String name = Validation.getNonEmptyString("Enter Brand Name: ");
        String soundBrand = Validation.getNonEmptyString("Enter Sound Brand: ");
        double price = Validation.getNewPrice("Enter Price (e.g., 3.749B or 3.749): ");
        return new Brand(id, name, soundBrand, price);
    }
    
    // separate function to get a Brand ID for searching or updating
    public String getBrandID(String action) {
        return Validation.getNonEmptyString("Enter Brand ID to " + action + ": ");
    }
    
    public boolean getUpdatedBrand(Brand brand) {
        Menu.printMessage("\n---- UPDATE BRAND: " + brand.getBrandID() + " ----");
        boolean updated = false;
        String newName = Validation.getStringForUpdate("Enter new Brand Name", brand.getBrandName());
        if (!newName.isEmpty() && !newName.equals(brand.getBrandName())) {
            brand.setBrandName(newName);
            Menu.printMessage("Brand Name updated.");
            updated = true;
        }
        String newSoundBrand = Validation.getStringForUpdate("Enter new Sound Brand", brand.getSoundBrand());
        if (!newSoundBrand.isEmpty() && !newSoundBrand.equals(brand.getSoundBrand())) {
            brand.setSoundBrand(newSoundBrand);
            Menu.printMessage("Sound Brand updated.");
            updated = true;
        }
        double newPrice = Validation.getUpdatedPrice("Enter new Price", brand.getPrice());
        if (newPrice != -1.0 && Math.abs(newPrice - brand.getPrice()) > 0.00001) {
            brand.setPrice(newPrice);
            Menu.printMessage("Price updated to: " + String.format("%.3fB", newPrice));
            updated = true;
        }
        return updated;
    }
    
    public double getMaxPriceForFilter() {
        Menu.printMessage("\n---- FILTER BRANDS BY MAX PRICE ----");
        return Validation.getNewPrice("Enter maximum price (in billions, for example 2.7 or 2.7B): ");
    }
}