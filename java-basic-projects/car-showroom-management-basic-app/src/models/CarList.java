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

public class CarList {
    private static final String CAR_FILE_PATH = "cars.txt";
    private static final String TEMP_CAR_FILE_PATH = "cars_temp.txt";
    private BrandList brandList; 
    private List<Car> list; 
    private boolean modified = false;

    public CarList(BrandList brandList) {
        this.brandList = brandList;
        this.list = new ArrayList<>();
    }
    
    public boolean getModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
    public Car searchCarByID(String ID) {
        for (Car car : list) {
            if (car.getCarID().equalsIgnoreCase(ID)) return car;
        }
        return null;
    }
    
    public boolean addCar(Car car) {
        if (searchCarByID(car.getCarID()) != null) return false;
        this.list.add(car);
        this.modified = true;
        return true;
    }

    public boolean remove(Car car) {
        if (this.list.remove(car)) {
            this.modified = true;
            return true;
        }
        return false;
    }
    
    public boolean removeCarByID(String ID) {
        Car car = searchCarByID(ID);
        if (car == null) return false;
        if (this.list.remove(car)) {
            this.modified = true;
            return true;
        }
        return false;
    }

    public List<Car> listAllCars() {
        List<Car> sortedList = new ArrayList<>(this.list);
        Collections.sort(sortedList);
        return sortedList;
    }

    public List<Car> searchCarsByPartialBrandName(String partialBrandName) {
        List<Car> filteredList = this.list.stream().filter(car -> car.getBrand().getBrandName().toLowerCase().contains(partialBrandName.toLowerCase())).collect(Collectors.toList());
        Collections.sort(filteredList);
        return filteredList;
    }

    public List<Car> searchCarsByColor(String color) {
        List<Car> filteredList = this.list.stream().filter(car -> car.getColor().toLowerCase().equals(color.toLowerCase())).collect(Collectors.toList());
        Collections.sort(filteredList);
        return filteredList;
    }
    
    public List<Car> searchCarsByBrandID(String brandID) {
        return this.list.stream().filter(car -> car.getBrand().getBrandID().equalsIgnoreCase(brandID)).collect(Collectors.toList());
    }
    
    public Car searchCarByFrameID(String frameID) {
        for (Car car : list) {
            if (car.getFrameID().equalsIgnoreCase(frameID)) return car;
        }
        return null;
    }
    
    public Car searchCarByEngineID(String engineID) {
        for (Car car : list) {
            if (car.getEngineID().equalsIgnoreCase(engineID)) return car;
        }
        return null;
    }
    
    public boolean removeCarsByBrandID(String brandID) {
        List<Car> carsToRemove = searchCarsByBrandID(brandID);
        if (!carsToRemove.isEmpty()) {
            boolean removed = list.removeAll(carsToRemove);
            if (removed) this.modified = true;
            return removed;
        }
        return false;
    }
    
    public Car findCarWithMaxPrice() {
        if (list.isEmpty()) return null;
        return Collections.max(list, Comparator.comparingDouble(Car::getCarPrice));
    }
    
    public void sort() {
        Collections.sort(list);
    }
    
    public List<Car> getList() {
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
                    String[] parts = line.split(",", 5);
                    if (parts.length < 5) throw new IllegalArgumentException("Missing required fields separated by ','.");
                    String carID = parts[0].trim();
                    String brandID = parts[1].trim();
                    String color = parts[2].trim();
                    String frameID = parts[3].trim().toUpperCase();
                    String engineID = parts[4].trim().toUpperCase();
                    if (carID.isEmpty() || brandID.isEmpty() || color.isEmpty() || frameID.isEmpty() || engineID.isEmpty()) {
                         throw new IllegalArgumentException("One or more fields found to be empty.");
                    }
                    Brand brand = brandList.searchBrandByID(brandID);
                    if (brand == null) {
                        throw new IllegalArgumentException("Brand ID not found in brand list: " + brandID);
                    }
                    if (searchCarByID(carID) != null) {
                        throw new IllegalArgumentException("Duplicate Car ID: " + carID);
                    }
                    if (searchCarByFrameID(frameID) != null) {
                        throw new IllegalArgumentException("Duplicate Frame ID: " + frameID);
                    }
                    if (searchCarByEngineID(engineID) != null) {
                        throw new IllegalArgumentException("Duplicate Engine ID: " + engineID);
                    }
                    if (!frameID.matches("^F\\d{5}$")) {
                        throw new IllegalArgumentException("Frame ID format is invalid (Expected F00000): " + frameID);
                    }
                    if (!engineID.matches("^E\\d{5}$")) {
                        throw new IllegalArgumentException("Engine ID format is invalid (Expected E00000): " + engineID);
                    }
                    list.add(new Car(carID, brand, color, frameID, engineID));
                } catch (IllegalArgumentException e) {
                    System.out.printf("Skipping faulty line in %s (line %d). Reason: %s%n", 
                                      Paths.get(filePath).getFileName(), lineNumber, e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading cars from file: " + filePath + " - " + e.getMessage());
        }
    }

    private void save(String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (Car car : list) { 
                writer.printf("%s, %s, %s, %s, %s%n", car.getCarID(), car.getBrand().getBrandID(), 
                        car.getColor(), car.getFrameID(), car.getEngineID());
            }
        } catch (IOException e) {
            System.err.println("Error saving cars to file: " + filePath + " - " + e.getMessage());
        }
    }
    
    // Public accessors for loading and saving
    public void loadFromFile() { 
        load(CAR_FILE_PATH);
    }
    public void loadFromTempFile() { 
        load(TEMP_CAR_FILE_PATH);
    }

    public void saveToFile() {
        if (!modified) return;
        save(CAR_FILE_PATH);
        modified = false;
        System.out.println("Cars saved successfully to " + CAR_FILE_PATH);
    }
    
    public void saveToTempFile() {
        try {
            save(TEMP_CAR_FILE_PATH);
            // no modified = false here, as the main list remains modified until a manual save.
        } catch (Exception e) {
            System.err.println("Error autosaving to temporary car file: " + e.getMessage());
        }
    }
    
    public boolean discardTempFiles() {
        try {
            return Files.deleteIfExists(Paths.get(TEMP_CAR_FILE_PATH));
        } catch (IOException e) {
            System.err.println("Error deleting temporary car file: " + e.getMessage());
            return false;
        }
    }
    
    public boolean hasTempFiles() {
        Path tempPath = Paths.get(TEMP_CAR_FILE_PATH);
        try {
            return Files.exists(tempPath) && Files.size(tempPath) > 0;
        } catch (IOException e) {
            return false;
        }
    }
}