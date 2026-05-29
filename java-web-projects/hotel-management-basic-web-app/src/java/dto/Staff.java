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
public class Staff implements Serializable {
    private int staffId;
    private String fullName;
    private String role;
    private String username;
    // PasswordHash is not stored in the DTO, only used in DAO for comparison

    public Staff() {
    }

    public Staff(int staffId, String fullName, String role, String username) {
        this.staffId = staffId;
        this.fullName = fullName;
        this.role = role;
        this.username = username;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}