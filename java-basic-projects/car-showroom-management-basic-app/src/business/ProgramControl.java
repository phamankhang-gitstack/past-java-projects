/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

/**
 *
 * @author trang
 */
import java.util.List;
import models.Brand;
import models.Car;
import models.BrandList;
import models.CarList;
import models.CustomerList;
import tools.Validation;
import tools.Auxiliary;
import view.Menu;
import view.BrandView;
import view.CarView;
import view.CustomerView;

public class ProgramControl {
    private BrandList brandList;
    private CarList carList;
    private CustomerList customerList;
    private BrandView brandView;
    private CarView carView;
    private CustomerView customerView;
    private CustomerManager customerManager;

    public ProgramControl() {
        this.brandList = new BrandList();
        this.carList = new CarList(this.brandList); 
        this.customerList = new CustomerList();
        this.brandView = new BrandView(this.carList);
        this.carView = new CarView();
        this.customerView = new CustomerView();
        this.customerManager = new CustomerManager(customerList, customerView);
        loadData();
    }
    
    // load initial data from files, prioritizing temporary autosave files.
    private void loadData() {
        boolean intentionalQuit = Auxiliary.checkAndClearQuitFlag();
        boolean tempFilesExist = brandList.hasTempFiles() || carList.hasTempFiles();
        if (tempFilesExist && !intentionalQuit) {
            Menu.printMessage("\n=== TEMPORARY AUTOSAVED DATA DETECTED ===");
            Menu.printMessage("It appears the program terminated unexpectedly during the last session.");
            boolean integrate = Validation.getConfirmation("Do you want to load the unsaved recovery data and integrate into current session?");
            if (integrate) {
                boolean recovered = false;
                if (brandList.hasTempFiles()) {
                    brandList.loadFromTempFile();
                    brandList.setModified(true);
                    Menu.printMessage("Brands data recovered from temporary file.");
                    recovered = true;
                } else brandList.loadFromFile();
                if (carList.hasTempFiles()) {
                    carList.loadFromTempFile();
                    carList.setModified(true);
                    Menu.printMessage("Cars data recovered from temporary file.");
                    recovered = true;
                } else carList.loadFromFile();
                if (recovered) Menu.printMessage("\nRecovery data loaded successfully. Initiating new session with unsaved changes.");
            } else {
                Menu.printMessage("Recovery data discarded. Initiating brand new session.");
                brandList.discardTempFiles();
                carList.discardTempFiles();
                Menu.printMessage("Loading data from files...");
                brandList.loadFromFile();
                carList.loadFromFile();
            }
        } else {
            Menu.printMessage("Loading data from files...");
            brandList.loadFromFile();
            carList.loadFromFile();
        }
        Menu.printMessage("Data load complete. Brands: " + brandList.size() + ", Cars: " + carList.size()); 
    }
    
    private void saveAction() {
        Menu.printMessage("Saving modified data...");
        brandList.saveToFile();
        carList.saveToFile();
        // discard temporary files after a successful manual save, and ensure quit flag is cleared
        brandList.discardTempFiles();
        carList.discardTempFiles();
        Auxiliary.checkAndClearQuitFlag();
    }

    // manual save function, calling above action
    private void saveData() {
        if (!brandList.getModified() && !carList.getModified()) {
            Menu.printMessage("No unsaved changes to save.");
            return;
        }
        if (!Validation.getConfirmation("Do you want to save changes to files now?")) {
            Menu.printMessage("Manual save aborted. Resuming unsaved session.");
            return;
        }
        saveAction();
    }

    public void run() {
        boolean running = true;
        int choice;
        while (running) {
            Menu.displayMenu();
            choice = Menu.getChoice();
            switch (choice) {
                case 1:
                    handleListAllBrands();
                    break;
                case 2:
                    handleAddBrand();
                    break;
                case 3:
                    handleSearchBrandByID();
                    break;
                case 4:
                    handleRemoveBrandByID();
                    break;
                case 5:
                    handleUpdateBrandByID();
                    break;
                case 6:
                    handleFilterBrandsByPrice();
                    break;
                case 7:
                    handleListAllCars();
                    break;
                case 8:
                    handleSearchCarsByPartialBrandName();
                    break;
                case 9:
                    handleAddCar();
                    break;
                case 10:
                    handleRemoveCarByID();
                    break;
                case 11:
                    handleUpdateCarByID();
                    break;
                case 12:
                    handleListCarsByColor();
                    break;
                case 13:
                    customerManager.showAll();
                    break;
                case 14:
                    customerManager.add();
                    break;
                case 15:
                    break;
                case 16:
                    saveData();
                    break;
                case 17:
                    running = quitProgram();
                    break;
                default:
                    Menu.printMessage("This function is not available.");
            }
        }
        Menu.printMessage("Exiting program.");
    }
    
    private void handleListAllBrands() {
        System.out.println("\n---------- LIST OF ALL BRANDS ----------");
        brandView.displayBrandList(brandList.listAllBrands());
    }

    private void handleAddBrand() {
        Brand newBrand = brandView.getNewBrand(brandList);
        if (brandList.addBrand(newBrand)) {
            Menu.printMessage("Brand added successfully!");
            brandList.saveToTempFile();
        } else {
            Menu.printMessage("Error encountered adding this brand. Please re-check and try again.");
        }
    }

    private void handleSearchBrandByID() {
        Menu.printMessage("\n---- SEARCH A BRAND BY ID ----");
        String id = brandView.getBrandID("search");
        Brand brand = brandList.searchBrandByID(id); 
        if (brand == null) {
            Menu.printMessage("This brand does not exist!");
        } else {
            brandView.displayBrand(brand);
        }
    }

    // new function not from the original requirements
    private void handleRemoveBrandByID() {
        Menu.printMessage("\n---- REMOVE A BRAND BY ID ----");
        String id = brandView.getBrandID("remove");
        Brand brand = brandList.searchBrandByID(id);
        if (brand == null) {
            Menu.printMessage("This brand does not exist!");
            return;
        }
        List<Car> dependentCars = carList.searchCarsByBrandID(id);
        int carCount = dependentCars.size();
        String confirmPrompt;
        if (carCount > 0) {
            confirmPrompt = String.format("Brand %s has %d registered car(s). Confirm removal will remove all associated cars."
                    + "\nDo you really want to remove that brand now?", brand.getBrandName(), carCount);
        } else confirmPrompt = "Do you really want to remove Brand " + brand.getBrandName() + " now?";
        boolean confirm = Validation.getConfirmation(confirmPrompt);
        if (confirm) {
            boolean carsRemoved = false;
            if (carCount > 0) {
                carsRemoved = carList.removeCarsByBrandID(id);
                Menu.printMessage("Removed " + carCount + " associated car(s) from " + brand.getBrandName() + ".");
            }
            if (brandList.remove(brand)) {
                Menu.printMessage("Brand removed successfully!");
                if (carsRemoved) {
                    carList.setModified(true);
                    carList.saveToTempFile();
                }
                brandList.setModified(true);
                brandList.saveToTempFile();
            } else Menu.printMessage("Brand removal failed (unexpected error).");
        } else Menu.printMessage("Brand removal aborted.");
    }
    
    private void handleUpdateBrandByID() {
        Menu.printMessage("\n---- UPDATE A BRAND BY ID ----");
        String id = brandView.getBrandID("update");
        Brand brand = brandList.searchBrandByID(id);
        if (brand == null) {
            Menu.printMessage("This brand does not exist!");
            return;
        }
        boolean updated = brandView.getUpdatedBrand(brand);
        if (updated) {
            Menu.printMessage("Brand updated successfully!");
            brandList.setModified(true);
            brandList.saveToTempFile();
        } else {
            Menu.printMessage("No changes were made to the brand.");
        }
    }

    private void handleFilterBrandsByPrice() {
        double maxPrice = brandView.getMaxPriceForFilter();
        List<Brand> filteredBrands = brandList.filterBrandsByMaxPrice(maxPrice);
        if (filteredBrands.isEmpty()) {
            Menu.printMessage("No brands found with price less than or equal to: " + String.format("%.3fB", maxPrice));
        } else {
            System.out.println("\n---------- LIST OF FILTERED BRANDS ----------");
            brandView.displayBrandList(filteredBrands);
        }
    }

    private void handleListAllCars() {
        if (carList.isEmpty()) { 
            Menu.printMessage("No cars available to display.");
            return;
        }
        System.out.println("\n---------- LIST OF ALL CARS ----------");
        carView.displayCarList(carList.listAllCars());
    }

    private void handleSearchCarsByPartialBrandName() {
        String partialName = carView.getPartialBrandName();
        List<Car> filteredCars = carList.searchCarsByPartialBrandName(partialName);
        if (filteredCars.isEmpty()) {
            Menu.printMessage("No cars found containing brand name: " + partialName);
        } else {
            System.out.println("\n---------- LIST OF CARS FOUND ----------");
            carView.displayCarList(filteredCars);
        }
    }

    private void handleAddCar() {
        if (brandList.isEmpty()) { 
            Menu.printMessage("Cannot add a car. The brand list is empty.");
            return;
        }
        Car newCar = carView.getNewCar(carList, brandList, brandView);
        if (carList.addCar(newCar)) {
            Menu.printMessage("Car added successfully!");
            carList.saveToTempFile();
        } else {
            Menu.printMessage("Error encountered adding this car. Please re-check and try again."); 
        }
    }

    private void handleRemoveCarByID() {
        Menu.printMessage("\n---- REMOVE A CAR BY ID ----");
        String id = carView.getCarID("remove");
        Car car = carList.searchCarByID(id);
        if (car == null) {
            Menu.printMessage("This car does not exist!");
            return;
        }
        boolean confirm = Validation.getConfirmation("Do you really want to remove Car "
                + car.getCarID() + " (" + car.getBrand().getBrandName() + ") now?");
        if (confirm) {
            if (carList.removeCarByID(id)) {
                Menu.printMessage("Car removed successfully!");
                carList.setModified(true);
                carList.saveToTempFile();
            } else Menu.printMessage("Car removal failed (unexpected error).");
        } else Menu.printMessage("Car removal aborted.");
    }
    
    private void handleUpdateCarByID() {
        Menu.printMessage("\n---- UPDATE A CAR BY ID ----");
        String id = carView.getCarID("update");
        Car car = carList.searchCarByID(id);
        if (car == null) {
            Menu.printMessage("This car does not exist!");
            return;
        }
        boolean updated = carView.getUpdatedCar(car, carList);
        if (updated) {
            Menu.printMessage("Car updated successfully!");
            carList.setModified(true);
            carList.saveToTempFile();
        } else {
            Menu.printMessage("No changes were made to the car.");
        }
    }

    private void handleListCarsByColor() {
        String color = carView.getCarColor();
        List<Car> filteredCars = carList.searchCarsByColor(color);
        if (filteredCars.isEmpty()) {
            Menu.printMessage("No cars found with color: " + color);
        } else {
            System.out.println("\n---------- LIST OF FILTERED CARS ----------");
            carView.displayCarList(filteredCars);
        }
    }

    private boolean quitProgram() {
        boolean running = true;
        if (brandList.getModified() || carList.getModified()) {
            boolean confirm = Validation.getConfirmation("Unsaved changes detected. Do you want to save changes before quitting?");
            if (confirm) {
                saveAction();
                running = false;
            } else {
                Menu.printMessage("Saving aborted.");
                Auxiliary.createQuitFlag();
                running = false;
            }
        } else running = false;
        return running;
    }
}