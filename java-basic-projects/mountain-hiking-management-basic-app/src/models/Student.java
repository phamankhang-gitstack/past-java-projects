/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
import java.io.Serializable; // allow I/O operations to work
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String phone;
    private String email;
    private String mountainCode;
    private double tuitionFee;
    public Student() {}

    public Student(String id, String name, String phone, String email, String mountainCode, double tuitionFee) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        setMountainCode(mountainCode); // normalize on set
        this.tuitionFee = tuitionFee;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getMountainCode() {
        if (mountainCode == null) return null;
        return new MountainCode(mountainCode).getCode();
    }

    public MountainCode getMountainCodeObj() {
        return new MountainCode(getMountainCode());
    }

    public double getTuitionFee() {
        return tuitionFee;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMountainCode(String mountainCode) {
        this.mountainCode = new MountainCode(mountainCode).getCode();
    }

    public void setTuitionFee(double tuitionFee) {
        this.tuitionFee = tuitionFee;
    }
}