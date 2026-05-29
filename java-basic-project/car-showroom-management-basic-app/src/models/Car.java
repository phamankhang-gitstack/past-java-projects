/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
import java.io.Serializable;
import java.util.Comparator;
import tools.Auxiliary;

public class Car implements Serializable, Comparable<Car> {
    private String carID;
    private Brand brand;
    private String color;
    private String frameID;
    private String engineID;
    public Car() {}

    public Car(String carID, Brand brand, String color, String frameID, String engineID) {
        this.carID = carID;
        this.brand = brand;
        this.color = color;
        this.frameID = frameID;
        this.engineID = engineID;
    }

    public String getCarID() {
        return carID;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public String getFrameID() {
        return frameID;
    }

    public String getEngineID() {
        return engineID;
    }
    
    public double getCarPrice() {
        return getBrand().getPrice();
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setFrameID(String frameID) {
        this.frameID = frameID;
    }

    public void setEngineID(String engineID) {
        this.engineID = engineID;
    }
    
    // placeholder
    public double getOptionalCarPrice() {
        return Auxiliary.calculateCarPriceByColor(this.brand, this.color);
    }

    // sort cars by ascending brand names; then by descending price; return negative/zero/positive integer for less than/equal to/greater
    @Override
    public int compareTo(Car otherCar) {
        return Comparator.comparing((Car c) -> c.getBrand().getBrandName(), String.CASE_INSENSITIVE_ORDER)
                         .thenComparing(Car::getCarPrice, Comparator.reverseOrder()).compare(this, otherCar);
        // if we do implement the optional price calculation, implement the above instead
    }

    // custom format for printing car details
    @Override
    public String toString() {
        return String.format("%s, %s (%s), %s, %s, %s", carID, brand.getBrandName(), brand.getBrandID(), color, frameID, engineID);
    }
}