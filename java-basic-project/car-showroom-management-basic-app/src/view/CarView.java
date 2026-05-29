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
import models.Car;
import models.CarList;
import tools.Validation;

public class CarView {
    private static final String CAR_TABLE_SEPARATOR = "------------------------------------------------------------------------------------------------------";
    
    public void displayCarList(List<Car> cars) {
        if (cars.isEmpty()) {
            Menu.printMessage("No cars available to display.");
            return;
        }
        System.out.println(CAR_TABLE_SEPARATOR);
        System.out.printf("| %-8s | %-35s | %-10s | %-10s | %-10s | %-10s |%n", "Car ID", "Brand Name", "Price", "Color", "Frame ID", "Engine ID");
        System.out.println(CAR_TABLE_SEPARATOR);
        for (Car c : cars) {
            System.out.printf("| %-8s | %-35s | %-10s | %-10s | %-10s | %-10s |%n", 
                    c.getCarID(), c.getBrand().getBrandName(), String.format("%.3fB", c.getCarPrice()), c.getColor(), c.getFrameID(), c.getEngineID());
        }
        System.out.println(CAR_TABLE_SEPARATOR);
    }
    
    // separate function to get a Brand ID for searching or updating
    public String getCarID(String action) {
        return Validation.getNonEmptyString("Enter Car ID to " + action + ": ");
    }
    
    
    public Car getNewCar(CarList carList, BrandList brandList, BrandView brandView) {
        Menu.printMessage("\n---- ADD A NEW CAR ----");
        String carID = Validation.getNewCarID("Enter new Car ID: ", carList);
        System.out.println("---------- LIST OF EXISTING BRANDS ----------");
        brandView.displayBrandList(brandList.listAllBrands());
        Brand brand = Validation.getExistingBrand("Enter existing Brand ID for this car: ", brandList);
        String color = Validation.getNonEmptyString("Enter Car Color: ").toLowerCase();
        String frameID = Validation.getNewFrameID("Enter Frame ID (F00000): ", carList);
        String engineID = Validation.getNewEngineID("Enter Engine ID (E00000): ", carList);
        return new Car(carID, brand, color, frameID, engineID);
    }
    
    public String getPartialBrandName() {
        Menu.printMessage("\n---- SEARCH CARS BY PARTIAL BRAND NAME ----");
        return Validation.getNonEmptyString("Enter partial Brand Name to search: ");
    }
    
    public String getCarColor() {
        Menu.printMessage("\n---- SEARCH CARS BY COLOR ----");
        return Validation.getNonEmptyString("Enter color to list cars: ").toLowerCase();
    }
    
    public boolean getUpdatedCar(Car car, CarList carList) {
        Menu.printMessage("\n---- UPDATE CAR: " + car.getCarID() + " ----");
        boolean updated = false;
        String newColor = Validation.getStringForUpdate("Enter new Color", car.getColor());
        if (!newColor.isEmpty() && !newColor.equalsIgnoreCase(car.getColor())) {
            car.setColor(newColor.toLowerCase());
            Menu.printMessage("Color updated.");
            updated = true;
        }
        String newFrameID = Validation.getUpdatedFrameID("Enter new Frame ID (F00000)", car.getFrameID(), carList);
        if (!newFrameID.isEmpty() && !newFrameID.equalsIgnoreCase(car.getFrameID())) {
            car.setFrameID(newFrameID.toUpperCase());
            Menu.printMessage("Frame ID updated.");
            updated = true;
        }
        String newEngineID = Validation.getUpdatedEngineID("Enter new Engine ID (E00000)", car.getEngineID(), carList);
        if (!newEngineID.isEmpty() && !newEngineID.equalsIgnoreCase(car.getEngineID())) {
            car.setEngineID(newEngineID.toUpperCase());
            Menu.printMessage("Engine ID updated.");
            updated = true;
        }
        return updated;
    }
}