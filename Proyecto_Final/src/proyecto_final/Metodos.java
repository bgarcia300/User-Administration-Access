/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.mail.internet.AddressException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Byron Garcia
 */
public class Metodos {
    
    //Metodo para probar la conexion a la base de datos
    public void IniciarSesion(String nombreUsuario, String contrasena) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user="+nombreUsuario+";password="+contrasena+";";
        Connection con = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada con login");
    }
    
    //Con este metodo creo el login, se ingresa con sa-sa en base de datos master
    public void RegistroNuevoLogin(String loginName, String contrasena) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=master;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para Registro Login");
        
        Statement st = connect.createStatement();

        CallableStatement CrearLogin = connect.prepareCall("{call sp_addlogin(?,?)}");
        CrearLogin.setString(1, loginName);
        CrearLogin.setString(2, contrasena);
        CrearLogin.execute();
        
        
    }
    
    //Con este metodo creo el usuario, se ingresa con sa-sa, en base de datos ProyectoFinal
    public void CreacionUsername(String loginName, String nombreUsuario) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para creacion Username");
        
        Statement st = connect.createStatement();
        
        CallableStatement CrearUsuario = connect.prepareCall("{call sp_adduser(?,?)}");
        CrearUsuario.setString(1, loginName);
        CrearUsuario.setString(2, nombreUsuario);
        CrearUsuario.execute();
        
    }
    
    //Metodo para insertar Uusuario en tabla Usuarios, el trigger en loa db se encarga de la tabla Admins y Directores
    public void InsercionUsuario(int idRol, String nombre, String primerApellido, String segundoApellido, String funcion, String Fecha_Nacimiento, int telefono,
                                  String correoElectronico, String residencia, String nombreLogin, String contrasena, String username, String observaciones) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para insercion");
        
        CallableStatement ProcedInsertarUsuario = connect.prepareCall("{call InsercionUsuario(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
        
        ProcedInsertarUsuario.setInt(1, idRol);
        ProcedInsertarUsuario.setString(2, nombre);
        ProcedInsertarUsuario.setString(3, primerApellido);
        ProcedInsertarUsuario.setString(4, segundoApellido);
        ProcedInsertarUsuario.setString(5, funcion);
        ProcedInsertarUsuario.setString(6, Fecha_Nacimiento);
        ProcedInsertarUsuario.setInt(7, telefono);
        ProcedInsertarUsuario.setString(8, correoElectronico);
        ProcedInsertarUsuario.setString(9, residencia);
        ProcedInsertarUsuario.setString(10, nombreLogin);
        ProcedInsertarUsuario.setString(11, contrasena);
        ProcedInsertarUsuario.setString(12, username);
        ProcedInsertarUsuario.setString(13, observaciones);
        
        ProcedInsertarUsuario.execute();

    }
    
    //Metodo String que retorna el nombre completo del usuarios segun id en parametro
    public String NombreUsuarioSegunId(int idUsuario) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de nombre");
        
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT * FROM Usuarios WHERE Id_Usuario = '" + idUsuario + "' ");
        String nombreCompleto = null;

        while (SelectUsuario.next()) {
            String nombre = SelectUsuario.getString(3);
            String primerApellido = SelectUsuario.getString(4);
            String segundoApellido = SelectUsuario.getString(5);

            nombreCompleto = nombre + " " + primerApellido + " " + segundoApellido;
        }
        return nombreCompleto;
    }
    
    //Metodo int que retorna el id del rol del usuario seguen id del usuario ingresado en el parametro
    public int IdRolUsuario(int idUsuario) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de Id de Rol");
        
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT Id_Rol FROM Usuarios WHERE Id_Usuario = '" + idUsuario + "' ");
        int idRol = 0;

        while (SelectUsuario.next()) {
            idRol = SelectUsuario.getInt(1);
        }
        return idRol;
    }
    
    //Metodo int que retorna el id del usuario del login ingresado
    public int IdUsuarioSegunLogin(String login) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de Id de Rol");
        
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT Id_Usuario FROM Usuarios WHERE Nombre_Login = '" + login + "' ");
        int idUsuario = 0;

        while (SelectUsuario.next()) {
            idUsuario = SelectUsuario.getInt(1);
        }
        return idUsuario;
    }
    
    //Modelo para la tabla cuando se seleccionen usuarios
    public DefaultTableModel GeneracionTablaUsuarios() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de usuarios");
      
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT Id_Usuario, Nombre_Usuario, Primer_Apellido_Usuario, Segundo_Apellido_Usuario, Id_Rol, Funcion, Fecha_Nacimiento, Edad, Telefono, Correo_Electronico, Residencia, observaciones FROM Usuarios ");
       
        String data[][]={};
        String titulos[]={"ID Usuario","Nombre","1er Apellido","2doApellido","ID Rol","Función","Fecha de nacimento","Edad", "Telefono", "Correo eletrónico", "Residencia", "Observaciones"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectUsuario.next()){
            
           Object datos[]={SelectUsuario.getInt(1),SelectUsuario.getNString(2),SelectUsuario.getNString(3),SelectUsuario.getNString(4),SelectUsuario.getInt(5),SelectUsuario.getNString(6),SelectUsuario.getDate(7),SelectUsuario.getInt(8),SelectUsuario.getInt(9),
                            SelectUsuario.getNString(10), SelectUsuario.getNString(10), SelectUsuario.getNString(11), SelectUsuario.getNString(12)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se seleccionen Grupos
    public DefaultTableModel GeneracionTablaGrupos() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de Grupos");
      
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT * FROM Grupos");
       
        String data[][]={};
        String titulos[]={"ID Grupo","ID Director","ID Usuarios","Nombre del Grupo", "Descripción"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectUsuario.next()){
            
           Object datos[]={SelectUsuario.getInt(1),SelectUsuario.getInt(2),SelectUsuario.getInt(3),SelectUsuario.getNString(4),SelectUsuario.getNString(5)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    }






