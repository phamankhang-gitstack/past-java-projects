/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import models.Customer;

public class CustomerList {
    private List<Customer> list;
    private boolean modified = false;
    
    public CustomerList() {
        this.list = new ArrayList<>();
    }
    
    public boolean getModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
    
    public List<Customer> getList() {
        return list;
    }
    
    public boolean isEmpty() {
        return list.isEmpty();
    }
    
    public int size() {
        return list.size();
    }
    
    public Customer searchCustomerByID(String ID) {
        for (Customer customer : list) {
            if (customer.getID().equalsIgnoreCase(ID)) return customer;
        }
        return null;
    }
    
    public boolean addCustomer(Customer customer) {
        if (searchCustomerByID(customer.getID()) != null) return false;
        this.list.add(customer);
        this.modified = true;
        return true;
    }
    
    public List<Customer> listAllCustomers() {
        List<Customer> sortedList = new ArrayList<>(this.list);
        Collections.sort(sortedList);
        return sortedList;
    }
}
