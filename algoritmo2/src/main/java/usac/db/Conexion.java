/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Luis Monterroso
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/incre";
    private static final String USER = "root";
    private static final String PASSWORD = "41288320@abc";
    public static Connection conexion = null;

    public void getConnection() throws SQLException {
        conexion = DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
