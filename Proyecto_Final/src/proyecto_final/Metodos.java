/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Byron Garcia
 */
public class Metodos {
    public void PruebaConexion(String nombreUsuario, String contrasena) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=locker_control;user=usuarioSQL;password=dbLockerControl;";
        Connection con = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada");
    }
    
}
