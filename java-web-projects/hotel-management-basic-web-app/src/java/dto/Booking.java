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
public class Booking implements Serializable {
    private int bookingId;
    private int guestId;
    private int roomId;
    private String checkInDate;
    private String checkOutDate;
    private String bookingDate;
    private String status;
    private String guestFullName; // display
    private String roomNumber; // display

    public Booking() {
    }
    
    public Booking(int bookingId, int guestId, int roomId, String checkInDate, String checkOutDate, String bookingDate, String status) {
        this.bookingId = bookingId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public Booking(int bookingId, String guestFullName, String roomNumber, String checkInDate, String checkOutDate, String bookingDate, String status) {
        this.bookingId = bookingId;
        this.guestFullName = guestFullName;
        this.roomNumber = roomNumber;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingDate = bookingDate;
        this.status = status;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String CheckInDate) {
        this.checkInDate = CheckInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String CheckOutDate) {
        this.checkOutDate = CheckOutDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String BookingDate) {
        this.bookingDate = BookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public String getGuestFullName() {
        return guestFullName;
    }

    public void setGuestFullName(String guestFullName) {
        this.guestFullName = guestFullName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
