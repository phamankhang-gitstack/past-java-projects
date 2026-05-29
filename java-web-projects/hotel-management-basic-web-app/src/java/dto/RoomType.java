/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author trang
 */
public class RoomType implements Serializable {
    private int roomTypeId;
    private String typeName;
    private int capacity;
    private BigDecimal pricePerNight;

    public RoomType() {
    }

    public RoomType(int roomTypeId, String typeName, int capacity, BigDecimal pricePerNight) {
        this.roomTypeId = roomTypeId;
        this.typeName = typeName;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
    }
    
    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(BigDecimal pricePerNight) {
        this.pricePerNight = pricePerNight;
    }
}