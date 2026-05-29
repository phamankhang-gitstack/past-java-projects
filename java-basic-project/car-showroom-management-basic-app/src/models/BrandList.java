/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BrandList {
    private static final String BRAND_FILE_PATH = "brands.txt";
    private static final String TEMP_BRAND_FILE_PATH = "brands_temp.txt";
    private List<Brand> list;
    private boolean modified = false;
    
    public BrandList() {
        this.list = new ArrayList<>();
    }

    public boolean getModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
    public Brand searchBrandByID(String ID) {
        for (Brand brand : list) { 
            if (brand.getBrandID().equalsIgnoreCase(ID)) return brand;
        }
        return null;
    }

    public boolean addBrand(Brand brand) {
        if (searchBrandByID(brand.getBrandID()) != null) return false;
        this.list.add(brand);
        this.modified = true;
        return true;
    }
    
    public boolean remove(Brand brand) {
        if (list.remove(brand)) {
            this.modified = true;
            return true;
        }
        return false;
    }

    public List<Brand> listAllBrands() {
        List<Brand> sortedList = new ArrayList<>(this.list);
        Collections.sort(sortedList);
        return sortedList;
    }
    
    public List<Brand> filterBrandsByMaxPrice(double maxPrice) {
        List<Brand> filteredList = this.list.stream().filter(brand -> brand.getPrice() <= maxPrice).collect(Collectors.toList());
        Collections.sort(filteredList);
        return filteredList;
    }
    
    public Brand findBrandWithMaxPrice() {
        if (list.isEmpty()) return null;
        return Collections.max(list, Comparator.comparingDouble(Brand::getPrice));
    }
    
    public List<Brand> filterBrandsBySound(String partialSound) {
        return this.list.stream().filter(b -> b.getSoundBrand().toLowerCase().contains(partialSound.toLowerCase())).collect(Collectors.toList());
    }
    
    public void sortByBrandName() {
        Collections.sort(list);
    }
    
    public double calculateAveragePrice() {
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToDouble(Brand::getPrice).average().orElse(0.0);
    }
    
    public List<Brand> getList() {
        return list;
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }

    private void load(String filePath) {
        // clear existing list to prevent duplicate loading
        this.list.clear(); 
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                line = line.trim();
                if (line.isEmpty()) continue; 
                try {
                    String[] parts = line.split(":", 2);
                    if (parts.length < 2)  throw new IllegalArgumentException("Missing price separator ':'");
                    String priceStr = parts[1].trim().toUpperCase();
                    if (priceStr.isEmpty()) throw new IllegalArgumentException("Price value cannot be empty.");
                    if (priceStr.endsWith("B")) priceStr = priceStr.substring(0, priceStr.length() - 1);
                    double price = Double.parseDouble(priceStr);
                    if (price <= 0) throw new IllegalArgumentException("Price must be a positive number.");
                    String brandInfo = parts[0].trim();
                    String[] brandParts = brandInfo.split(",", 3);
                    if (brandParts.length < 3) throw new IllegalArgumentException("Missing required fields separated by ','.");
                    String brandID = brandParts[0].trim();
                    String brandName = brandParts[1].trim();
                    String soundBrand = brandParts[2].trim();
                    if (brandID.isEmpty() || brandName.isEmpty() || soundBrand.isEmpty()) {
                         throw new IllegalArgumentException("One or more fields found to be empty.");
                    }
                    if (searchBrandByID(brandID) != null) {
                        throw new IllegalArgumentException("Duplicate Brand ID: " + brandID);
                    }
                    list.add(new Brand(brandID, brandName, soundBrand, price));
                } catch (NumberFormatException e) {
                    System.out.printf("Skipping faulty line in %s (line %d). Reason: %s%n", 
                                      Paths.get(filePath).getFileName(), lineNumber, e.getMessage());
                } catch (IllegalArgumentException e) {
                    System.out.printf("Skipping faulty line in %s (line %d). Reason: %s%n", 
                                      Paths.get(filePath).getFileName(), lineNumber, e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading brands from file: " + filePath + " - " + e.getMessage());
        }
    }
    
    private void save(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Brand brand : list) writer.println(brand.toString());
        } catch (IOException e) {
            System.err.println("Error saving brands to file: " + filePath + " - " + e.getMessage());
        }
    }
    
    // Public accessors for loading and saving
    public void loadFromFile() { 
        load(BRAND_FILE_PATH);
    }
    public void loadFromTempFile() { 
        load(TEMP_BRAND_FILE_PATH);
    }

    public void saveToFile() {
        if (!modified) return;
        save(BRAND_FILE_PATH);
        modified = false;
        System.out.println("Brands saved successfully to " + BRAND_FILE_PATH);
    }
    
    public void saveToTempFile() {
        try {
            save(TEMP_BRAND_FILE_PATH);
            // no modified = false here, as the main list remains modified until a manual save.
        } catch (Exception e) {
            System.err.println("Error autosaving to temporary brand file: " + e.getMessage());
        }
    }
    
    public boolean discardTempFiles() {
        try {
            return Files.deleteIfExists(Paths.get(TEMP_BRAND_FILE_PATH));
        } catch (IOException e) {
            System.err.println("Error deleting temporary brand file: " + e.getMessage());
            return false;
        }
    }
    
    public boolean hasTempFiles() {
        Path tempPath = Paths.get(TEMP_BRAND_FILE_PATH);
        try {
            return Files.exists(tempPath) && Files.size(tempPath) > 0;
        } catch (IOException e) {
            return false;
        }
    }
}