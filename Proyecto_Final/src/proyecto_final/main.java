/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.CallableStatement;

/**
 *
 * @author Byron Garcia
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
       Metodos methods = new Metodos();
        
       String login = "E1881";
       String password = "123";
       String userName = "Esgrato";
      
       //methods.PruebaConexion(login, password);
        //methods.CreacionLogin(login, password);
        //methods.CreacionUsername(login, userName);
       // methods.InsercionUsuario(3,"Estif", "Granados", "Torres", "Administrador de proyectos", "2002-09-16", 61059876, "estifgranados15@gmail.com",
       //                         "Cot, Cartago", "E1881", "123", "Esgrato", "Administra los usuarios de los proyectos");
       // System.out.println(methods.NombreUsuarioSegunId(3));
       //System.out.println(methods.IdRolUsuario(3));
       System.out.println(methods.IdUsuarioSegunLogin("E1881"));
    }
    
}
