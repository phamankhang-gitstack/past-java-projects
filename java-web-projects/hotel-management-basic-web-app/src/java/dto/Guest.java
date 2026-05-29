/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author trang
 */
public class Guest implements Serializable {
    private int guestId;
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String idNumber;
    private String dateOfBirth;

    public Guest() {
    }
    
    public Guest(int guestId, String fullName, String phone, String email, String address, String idNumber, String dateOfBirth) {
        this.guestId = guestId;
        this.fullName = fullName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.idNumber = idNumber;
        this.dateOfBirth = dateOfBirth;
    }
    
    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}