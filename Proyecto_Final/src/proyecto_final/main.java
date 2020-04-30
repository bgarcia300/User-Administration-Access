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
import javax.mail.internet.AddressException;

/**
 *
 * @author Byron Garcia
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException, AddressException {
       Metodos methods = new Metodos();
       loginInicial WindowLoginInicial = new loginInicial();
       ValidacionesIngresoDatosNuevoUsuario metodoValidacionUsuario = new ValidacionesIngresoDatosNuevoUsuario();
        
       String login = "Marco123";
       String password = "123";
       String userName = "Marco";
      
        //methods.IniciarSesion(login, password);
        //methods.RegistroNuevoLogin(login, password);
        //methods.CreacionUsername(login, userName);
        //methods.InsercionUsuario(2,"Maro", "Garcia", "Saabria", "Administrador Principal", "1987-05-20", 83768060, "marcogs15@gmail.com",
        //                        "Tejar, Cartago", login, "123", userName, "Administrador unico");
       // System.out.println(methods.NombreUsuarioSegunId(3));
       //System.out.println(methods.IdRolUsuario(3));
       //System.out.println(methods.IdUsuarioSegunLogin("E1881"));

       //WindowLoginInicial.setVisible(true);
       //WindowLoginInicial.setLocationRelativeTo(null);
       metodoValidacionUsuario.ConvertidorFechaAString("2002-08-15");
    }
    
}
