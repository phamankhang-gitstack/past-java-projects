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

public class Customer implements Serializable, Comparable<Customer> {
    private String ID;
    private String name;
    private int phone;
    private String address;
    public Customer() {}

    public Customer(String id, String name, int phone, String address) {
        this.ID = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    @Override
    public int compareTo(Customer other) {
        return Comparator.comparing(Customer::getID, String.CASE_INSENSITIVE_ORDER).compare(this, other);
    }
}
