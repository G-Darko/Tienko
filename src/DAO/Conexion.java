/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author mps35
 */
public class Conexion {
    Connection con;
    PreparedStatement stmt;
    ResultSet tabla;
    String cadena, driver;
    int sw = 0;

    public Conexion() {
        cadena = "jdbc:mysql://localhost/tienko?user=tienkouser&password=darko123";
        driver = "com.mysql.jdbc.Driver";
    }

    public Connection conecta() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(cadena);
            System.out.println("Conexion exitosa");
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return con;
    }
/*
    public static void main(String[] args) {
        Conexion mycon = new Conexion();
        mycon.conecta();
    }
*/
}
