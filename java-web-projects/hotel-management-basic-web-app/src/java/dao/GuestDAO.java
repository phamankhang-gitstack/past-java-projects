/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DbUtils;
import dto.Guest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author user
 */
public class GuestDAO {
    
    public int insertGuest(String name, String phone, String email, String address, String idNumber, String dateOfBirth) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        int guestId = 0;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Return generated keys (GuestID)
                String sql = "INSERT INTO GUEST (FullName, Phone, Email, Address, IDNumber, DateOfBirth) VALUES (?,?,?,?,?,?)";
                st = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setString(1, name);
                st.setString(2, phone);
                st.setString(3, email);
                st.setString(4, address);
                st.setString(5, idNumber);
                st.setString(6, dateOfBirth); // Assuming YYYY-MM-DD string format
                int affectedRows = st.executeUpdate();
                if (affectedRows > 0) {
                    rs = st.getGeneratedKeys();
                    if (rs.next()) guestId = rs.getInt(1); // Get the auto-generated ID
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return guestId;
    } 
    
    public Guest searchGuest(String email) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Guest result = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT GuestID, FullName, Phone, Email, Address, IDNumber, DateOfBirth FROM GUEST WHERE Email=?";
                st = cn.prepareStatement(sql);
                st.setString(1, email);
                rs = st.executeQuery();
                if (rs.next()) {
                    // Populate the full Guest object
                    int guestId = rs.getInt("GuestID");
                    String name = rs.getString("FullName");
                    String phone = rs.getString("Phone");
                    String address = rs.getString("Address");
                    String idNumber = rs.getString("IDNumber");
                    String bday = rs.getString("DateOfBirth");
                    result = new Guest(guestId, name, phone, email, address, idNumber, bday);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return result;
    }
    
    public Guest getGuestById(int guestId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Guest result = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                String sql = "SELECT GuestID, FullName, Phone, Email, Address, IDNumber, DateOfBirth FROM GUEST WHERE GuestID=?";
                st = cn.prepareStatement(sql);
                st.setInt(1, guestId);
                rs = st.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("FullName");
                    String phone = rs.getString("Phone");
                    String email = rs.getString("Email");
                    String address = rs.getString("Address");
                    String idNumber = rs.getString("IDNumber");
                    String bday = rs.getString("DateOfBirth");
                    result = new Guest(guestId, name, phone, email, address, idNumber, bday);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return result;
    }
}
