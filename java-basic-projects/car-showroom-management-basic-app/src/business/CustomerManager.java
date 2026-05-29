/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

/**
 *
 * @author trang
 */
import models.Customer;
import models.CustomerList;
import view.CustomerView;
import view.Menu;

public class CustomerManager {
    private CustomerList customerList;
    private CustomerView customerView;

    public CustomerManager(CustomerList customerList, CustomerView customerView) {
        this.customerList = new CustomerList();
        this.customerView = new CustomerView();
    }
    
    public void add() {
        Customer newCustomer = customerView.getNewCustomer(customerList);
        if (customerList.addCustomer(newCustomer)) {
            Menu.printMessage("Customer added successfully.");
        } else {
            Menu.printMessage("Error encountered adding this customer. Please re-check and try again.");
        }
    }
    
    public void showAll() {
        System.out.println("\n---------- LIST OF ALL CUSTOMERS ----------");
        customerView.displayCustomerList(customerList.listAllCustomers());
    }
}
