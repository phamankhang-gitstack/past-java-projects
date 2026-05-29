/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dbutils.DbUtils;
import dto.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author trang
 */
public class StaffDAO {

    public Staff checkLogin(String username, String password) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Staff staff = null;
        try {
            cn = DbUtils.getConnection();
            if (cn != null) {
                // Assuming PasswordHash stores the plain password or a simple hash (for this example).
                // A secure application would use BCrypt or similar.
                String sql = "SELECT StaffID, FullName, Role FROM STAFF WHERE Username = ? AND PasswordHash = ?";
                st = cn.prepareStatement(sql);
                st.setString(1, username);
                st.setString(2, password);
                rs = st.executeQuery();
                if (rs.next()) {
                    staff = new Staff(rs.getInt("StaffID"), rs.getString("FullName"), rs.getString("Role"), username);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.closeAll(cn, st, rs);
        }
        return staff;
    }
}
