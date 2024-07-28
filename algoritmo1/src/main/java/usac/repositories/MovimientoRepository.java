/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package usac.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import usac.db.Conexion;

/**
 *
 * @author Luis Monterroso
 */
public class MovimientoRepository {

    // Método para establecer el valor inicial
    public void setInitialValue(int value) throws SQLException {

        // Comprobar si ya existe un registro
        String checkSql = "SELECT COUNT(*) FROM Movimiento";
        try (PreparedStatement checkStmt
                = Conexion.conexion.prepareStatement(checkSql); ResultSet rs = checkStmt.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                // Si existe, actualizar el valor
                String updateSql = "UPDATE Movimiento SET valor = ? WHERE id = 1";
                try (PreparedStatement updateStmt = Conexion.conexion.prepareStatement(updateSql)) {
                    updateStmt.setInt(1, value);
                    updateStmt.executeUpdate();
                }
            } else {
                // Si no existe, insertar un nuevo registro
                String insertSql = "INSERT INTO Movimiento (id, valor) VALUES (1, ?)";
                try (PreparedStatement insertStmt = Conexion.conexion.prepareStatement(insertSql)) {
                    insertStmt.setInt(1, value);
                    insertStmt.executeUpdate();
                }
            }
        }

    }

    public int getValue() throws SQLException {
        String sql = "SELECT valor FROM Movimiento WHERE id = 1";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("valor");
            }
        }
        return 0;
    }

    public boolean incrementValue(int increment) throws SQLException {
        String sql = "UPDATE Movimiento SET valor = valor + ? WHERE id = 1";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(sql)) {
            stmt.setInt(1, increment);
            int executeUpdate = stmt.executeUpdate();
            if (executeUpdate >= 1) {
                return true;
            }
        }
        return false;
    }

    public boolean decrementValue(int decrement) throws SQLException {
        String sql = "UPDATE Movimiento SET valor = valor - ? WHERE id = 1";
        try (PreparedStatement stmt = Conexion.conexion.prepareStatement(sql)) {
            stmt.setInt(1, decrement);
            int executeUpdate = stmt.executeUpdate();
            if (executeUpdate >= 1) {
                return true;
            }
        }
        return false;
    }
}
