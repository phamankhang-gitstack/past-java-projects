/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author trang
 */
public class Mountain {
    private String code;
    private String name;
    private String province;
    private String description;
    public Mountain() {}

    public Mountain(String code, String name, String province, String description) {
        this.code = code;
        this.name = name;
        this.province = province;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getProvince() {
        return province;
    }

    public String getDescription() {
        return description;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}