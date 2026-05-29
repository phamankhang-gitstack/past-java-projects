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

public class StatisticRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    private String mountainCode;
    private String mountainName;
    private int participants;
    private double totalCost;

    public StatisticRecord(String mountainCode, String mountainName, int participants, double totalCost) {
        setMountainCode(mountainCode); // normalize here
        this.mountainName = mountainName;
        this.participants = participants;
        this.totalCost = totalCost;
    }

    public String getMountainCode() {
        return mountainCode;
    }

    public String getMountainName() {
        return mountainName;
    }

    public int getParticipants() {
        return participants;
    }
    
    public double getTotalCost() {
        return totalCost;
    }

    public void setMountainCode(String mountainCode) {
        if (mountainCode != null) {
            this.mountainCode = new MountainCode(mountainCode).getCode();
        } else this.mountainCode = null;
    }

    public void setMountainName(String mountainName) {
        this.mountainName = mountainName;
    }

    public void setParticipants(int participants) {
        this.participants = participants;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}