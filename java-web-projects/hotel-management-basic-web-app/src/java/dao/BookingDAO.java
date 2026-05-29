/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DbUtils;
import dto.Booking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class BookingDAO {
    
    public ArrayList<Booking> findBooking(String bookingDate) {
        ArrayList<Booking> result = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet table = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Join with GUEST and ROOM tables to get display information (Name, RoomNo)
                String sql = "SELECT B.BookingID, B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status, G.FullName, R.RoomNumber "
                        + "FROM BOOKING B JOIN GUEST G ON B.GuestID = G.GuestID JOIN ROOM R ON B.RoomID = R.RoomID "
                        + "WHERE B.BookingDate = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, bookingDate);
                table = st.executeQuery();
                while (table.next()) {
                    Booking b = new Booking (
                            table.getInt("BookingID"),
                            table.getString("FullName"),      // Display field
                            table.getString("RoomNumber"),   // Display field
                            table.getString("CheckInDate"),
                            table.getString("CheckOutDate"),
                            table.getString("BookingDate"),
                            table.getString("Status")
                    );
                    result.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, table);
        }
        return result;
    }
    
    public ArrayList<Booking> findBookingByCheckInDate(String checkInDate) {
        ArrayList<Booking> result = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet table = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Join with GUEST and ROOM tables to get display information (Name, RoomNo)
                String sql = "SELECT B.BookingID, B.CheckInDate, B.CheckOutDate, B.BookingDate, B.Status, G.FullName, R.RoomNumber "
                        + "FROM BOOKING B JOIN GUEST G ON B.GuestID = G.GuestID JOIN ROOM R ON B.RoomID = R.RoomID "
                        + "WHERE B.CheckInDate = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, checkInDate);
                table = st.executeQuery();
                while (table.next()) {
                    Booking b = new Booking (
                            table.getInt("BookingID"),
                            table.getString("FullName"),      // Display field
                            table.getString("RoomNumber"),   // Display field
                            table.getString("CheckInDate"),
                            table.getString("CheckOutDate"),
                            table.getString("BookingDate"),
                            table.getString("Status")
                    );
                    result.add(b);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, table);
        }
        return result;
    }
    
    public Booking getBookingById(int bookingId) {
        Booking b = null;
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT B.*, R.RoomNumber FROM BOOKING B JOIN ROOM R ON B.RoomID = R.RoomID WHERE B.BookingID = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, bookingId);
                rs = st.executeQuery();
                if (rs.next()) {
                    b = new Booking();
                    b.setBookingId(rs.getInt("BookingID"));
                    b.setRoomId(rs.getInt("RoomID"));
                    b.setRoomNumber(rs.getString("RoomNumber")); // to display
                    b.setCheckInDate(rs.getString("CheckInDate"));
                    b.setCheckOutDate(rs.getString("CheckOutDate"));
                    b.setStatus(rs.getString("Status"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return b;
    }
    
    public int cancelBooking(int bookingId) {
        int result = 0;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Only update the status to 'Canceled'
                String sql = "UPDATE BOOKING SET Status = 'Canceled' WHERE BookingID = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, bookingId);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st);
        }
        return result;
    }
    
    public int insertBooking(int guestId, int roomId, String checkIn, String checkOut, String bookingDate, String status) {
        int result = 0;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
               // The DB uses auto-generated BookingID, and BookingDate defaults to GETDATE()
               // We only need to insert GuestID, RoomID, CheckInDate, CheckOutDate, and Status.
               String sql = "INSERT INTO BOOKING (GuestID, RoomID, CheckInDate, CheckOutDate, Status) VALUES (?,?,?,?,?)";
               st = cn.prepareStatement(sql);
               st.setInt(1, guestId);
               st.setInt(2, roomId);
               st.setString(3, checkIn);
               st.setString(4, checkOut);
               st.setString(5, status);
               result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st);
        } 
        return result;
    }
    
    public int updateBooking(int bookingId, int roomId, String checkInDate, String checkOutDate, String status) {
        int result = 0;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
               String sql = "UPDATE BOOKING SET RoomID = ?, CheckInDate = ?, CheckOutDate = ?, Status = ? WHERE BookingID = ?";
               st = cn.prepareStatement(sql);
               st.setInt(1, roomId);
               st.setString(2, checkInDate);
               st.setString(3, checkOutDate);
               st.setString(4, status);
               st.setInt(5, bookingId);
               result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st);
        } 
        return result;
    }
    
    private int updateBookingStatus(int bookingId, String status) {
        int result = 0;
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE BOOKING SET Status = ? WHERE BookingID = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, status);
                st.setInt(2, bookingId);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st);
        }
        return result;
    }
    
    public int checkInBooking(int bookingId) {
        return updateBookingStatus(bookingId, "Checked-in");
    }
    
    public int checkOutBooking(int bookingId) {
        return updateBookingStatus(bookingId, "Checked-out");
    }
}
