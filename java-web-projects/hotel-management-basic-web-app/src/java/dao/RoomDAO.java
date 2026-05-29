/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DbUtils;
import dto.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class RoomDAO {
    
    public ArrayList<Room> getRooms(){
        ArrayList<Room> result = new ArrayList<>();
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT [RoomID]\n"
                        + "      ,[RoomNumber]\n"
                        + "      ,[RoomTypeID]\n"
                        + "      ,[Status]\n"
                        + "  FROM [HotelManagement].[dbo].[ROOM]";
                st = cn.createStatement();
                rs = st.executeQuery(sql);
                if (rs != null) {
                    while (rs.next()) {
                        Room r = new Room(
                                rs.getInt("RoomID"),
                                rs.getString("RoomNumber"),
                                rs.getInt("RoomTypeID"),
                                rs.getString("Status"),
                                rs.getString("TypeName"),
                                rs.getFloat("PricePerNight")
                        );
                        result.add(r);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return result;
    }
    
    public List<Room> getAvailableRooms() {
        List<Room> result = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID, R.Status, RT.TypeName, RT.PricePerNight "
                        + "FROM ROOM R JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID "
                        + "WHERE R.Status = 'Available'";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    Room r = new Room(
                            rs.getInt("RoomID"),
                            rs.getString("RoomNumber"),
                            rs.getInt("RoomTypeID"),
                            rs.getString("Status"),
                            rs.getString("TypeName"),
                            rs.getFloat("PricePerNight")
                    );
                    result.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return result;
    }
    
    public List<Room> searchAvailableRooms(String checkInDate, String checkOutDate, int roomTypeId) {
        List<Room> result = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // SQL to find rooms that are 'Available' and are not currently booked for the specified date range and matching RoomTypeID.
                String sql = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID, R.Status, RT.TypeName, RT.PricePerNight "
                        + "FROM ROOM R JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID "
                        + "WHERE R.RoomTypeID = ? AND R.Status = 'Available' AND R.RoomID NOT IN ("
                        + "    SELECT B.RoomID FROM BOOKING B "
                        + "    WHERE B.Status IN ('Reserved', 'Checked-in') "
                        + "    AND ( "
                        + "        (B.CheckInDate < ? AND B.CheckOutDate > ?) OR " // New booking starts between existing booking
                        + "        (B.CheckInDate BETWEEN ? AND ?) OR "             // New booking covers existing booking
                        + "        (B.CheckOutDate BETWEEN ? AND ?) "               // Existing booking covers new booking
                        + "    ) "
                        + ")";
                st = cn.prepareStatement(sql);
                st.setInt(1, roomTypeId);
                st.setString(2, checkOutDate);
                st.setString(3, checkInDate);
                st.setString(4, checkInDate);
                st.setString(5, checkOutDate);
                st.setString(6, checkInDate);
                st.setString(7, checkOutDate);
                rs = st.executeQuery();
                while (rs.next()) {
                    Room r = new Room(
                            rs.getInt("RoomID"),
                            rs.getString("RoomNumber"),
                            rs.getInt("RoomTypeID"),
                            rs.getString("Status"),
                            rs.getString("TypeName"),
                            rs.getFloat("PricePerNight")
                    );
                    result.add(r);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return result;
    }
    
    public int updateRoomStatus(int roomId, String newStatus) {
        Connection cn = null;
        PreparedStatement st = null;
        int result = 0;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "UPDATE ROOM SET Status = ? WHERE RoomID = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, newStatus);
                st.setInt(2, roomId);
                result = st.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st);
        }
        return result;
    }
    
     public Room getRoomById(int roomId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Room r = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Join with ROOM_TYPE to get all necessary details
                String sql = "SELECT R.RoomID, R.RoomNumber, R.RoomTypeID, R.Status, RT.TypeName, RT.PricePerNight "
                        + "FROM ROOM R JOIN ROOM_TYPE RT ON R.RoomTypeID = RT.RoomTypeID "
                        + "WHERE R.RoomID = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, roomId);
                rs = st.executeQuery();
                if (rs.next()) {
                    r = new Room(
                            rs.getInt("RoomID"),
                            rs.getString("RoomNumber"),
                            rs.getInt("RoomTypeID"),
                            rs.getString("Status"),
                            rs.getString("TypeName"),
                            rs.getFloat("PricePerNight")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return r;
    }
}
