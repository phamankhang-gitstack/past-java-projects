/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbutils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author trang
 */
public class DbUtils {
    
    // NOTE: Please replace the database information below with your own SQL Server connection details.
    private static final String DB_NAME = "<YOUR_DB_NAME>";
    private static final String DB_USER_NAME = "<YOUR_USERNAME>";
    private static final String DB_PASSWORD = "<YOUR_PASSWORD>";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=" + DB_NAME;
        conn = DriverManager.getConnection(url, DB_USER_NAME, DB_PASSWORD);
        return conn;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Extra methods to close JDBC resources safely. Click on the + sign on the left to edit.">
    public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void closeAll(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (st != null) st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Overload for closing only connection and statement/prepared statement
    public static void closeAll(Connection conn, PreparedStatement ps) {
        closeAll(conn, ps, null);
    } // </editor-fold>
}
