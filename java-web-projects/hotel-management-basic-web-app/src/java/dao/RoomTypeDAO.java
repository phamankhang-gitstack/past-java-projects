/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DbUtils;
import dto.RoomType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author trang
 */
public class RoomTypeDAO {
    
    public ArrayList<RoomType> getAllRoomTypes() {
        ArrayList<RoomType> list = new ArrayList<>();
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT RoomTypeID, TypeName, Capacity, PricePerNight FROM ROOM_TYPE";
                st = cn.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    RoomType rt = new RoomType(
                            rs.getInt("RoomTypeID"),
                            rs.getString("TypeName"),
                            rs.getInt("Capacity"),
                            rs.getBigDecimal("PricePerNight")
                    );
                    list.add(rt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return list;
    }
    
     /**
     * Retrieves a single room type by ID.
     * @param roomTypeId The ID of the room type.
     * @return RoomType DTO if found, null otherwise.
     */
    public RoomType getRoomTypeById(int roomTypeId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        RoomType rt = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT RoomTypeID, TypeName, Capacity, PricePerNight FROM ROOM_TYPE WHERE RoomTypeID = ?";
                st = cn.prepareStatement(sql);
                st.setInt(1, roomTypeId);
                rs = st.executeQuery();
                if (rs.next()) {
                    rt = new RoomType(
                            rs.getInt("RoomTypeID"),
                            rs.getString("TypeName"),
                            rs.getInt("Capacity"),
                            rs.getBigDecimal("PricePerNight")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return rt;
    }
}