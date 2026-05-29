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
import tools.Validation;
import models.Customer;
import models.CustomerList;

public class CustomerView {
    private static final String CUSTOMER_TABLE_SEPARATOR = "-------------------------------------------------------------------------------------------";
    
    public void displayCustomerList(List<Customer> customers) {
        if (customers.isEmpty()) {
            Menu.printMessage("No customers available to display.");
            return;
        }
        System.out.println(CUSTOMER_TABLE_SEPARATOR);
        System.out.printf("| %-8s | %-35s | %-14s | %-20s |%n", "ID", "Name", "Phone", "Address");
        System.out.println(CUSTOMER_TABLE_SEPARATOR);
        for (Customer c : customers) {
            System.out.printf("| %-8s | %-35s | %-14s | %-20s |%n", 
                    c.getID(), c.getName(), c.getPhone(), c.getAddress());
        }
        System.out.println(CUSTOMER_TABLE_SEPARATOR);
    }
    
    public Customer getNewCustomer(CustomerList customerList) {
        Menu.printMessage("\n---- ADD A NEW CUSTOMER ----");
        String id = Validation.getNewCustomerID("Enter new Customer ID: ", customerList);
        String name = Validation.getNonEmptyString("Enter Customer Name: ");
        int phone = Validation.getNewPhone("Enter Customer Phone Number: ", customerList);
        String address = Validation.getNonEmptyString("Enter Customer's Address: ");
        return new Customer(id, name, phone, address);
    }
}
