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

public class Brand implements Serializable, Comparable<Brand> {
    private String brandID;
    private String brandName;
    private String soundBrand;
    private double price;
    public Brand() {}

    public Brand(String brandID, String brandName, String soundBrand, double price) {
        this.brandID = brandID;
        this.brandName = brandName;
        this.soundBrand = soundBrand;
        this.price = price;
    }

    public String getBrandID() {
        return brandID;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getSoundBrand() {
        return soundBrand;
    }

    public double getPrice() {
        return price;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setSoundBrand(String soundBrand) {
        this.soundBrand = soundBrand;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // compare by brand name (alphabetical order), return negative/zero/positive integer for less than/equal to/greater
    @Override
    public int compareTo(Brand otherBrand) {
        return Comparator.comparing(Brand::getBrandName, String.CASE_INSENSITIVE_ORDER).compare(this, otherBrand);
    }

    // custom format for printing brand details
    @Override
    public String toString() {
        // Simple way to format the price back to the B-format for consistency in output/saving
        String priceStr = String.format("%.3fB", price);
        return String.format("%s, %s, %s: %s", brandID, brandName, soundBrand, priceStr);
    }
}