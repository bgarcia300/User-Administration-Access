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
    
    //Método que retorna el id de director segun nombre completo
    public int IdDirectorSegunNombreCompleto(String nombreCompleto) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de Id de director por nombre");
        
        Statement st = connect.createStatement();
        ResultSet SelectIdDirector = st.executeQuery("SELECT Id_Director FROM Directores WHERE Nombre_Director + ' ' + Primer_Apellido_Director + ' ' + Segundo_Apellido_Director = '" + nombreCompleto + "' ");
        int idDirector = 0;
        
        while(SelectIdDirector.next()){
            idDirector = SelectIdDirector.getInt(1);
        }
        return idDirector;
    }
    
    //Método que retorna el id de grupo sgun nombre de grupo
    public int IdGrupoSegunNombreGrupo(String nombreGrupo) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de Id de Grupo por nombre");
        
        Statement st = connect.createStatement();
        ResultSet SelectIdGrupo = st.executeQuery("SELECT Id_Grupo FROM Grupos WHERE Nombre_Grupo = '" + nombreGrupo + "' ");
        int idGrupo = 0;
        
        while(SelectIdGrupo.next()){
            idGrupo = SelectIdGrupo.getInt(1);
        }
        return idGrupo;
    }
    
    //Modelo para la tabla cuando se seleccionen usuarios por parte del admin
    public DefaultTableModel GeneracionTablaUsuariosCompleta() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de usuarios");
      
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT Id_Usuario, Nombre_Usuario, Primer_Apellido_Usuario, Segundo_Apellido_Usuario, Id_Rol, Funcion, Fecha_Nacimiento, Edad, Telefono, Correo_Electronico, Residencia, Nombre_Username, Nombre_Login, Contrasena, observaciones FROM Usuarios ");
       
        String data[][]={};
        String titulos[]={"ID Usuario","Nombre","1er Apellido","2doApellido","ID Rol","Función","Fecha de nacimento","Edad", "Telefono", "Correo eletrónico", "Residencia", "Username de Usuario", "Login", "Contraseña", "Observaciones"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectUsuario.next()){
            
           Object datos[]={SelectUsuario.getInt(1),SelectUsuario.getNString(2),SelectUsuario.getNString(3),SelectUsuario.getNString(4),SelectUsuario.getInt(5),SelectUsuario.getNString(6),SelectUsuario.getDate(7),SelectUsuario.getInt(8),SelectUsuario.getInt(9),
                            SelectUsuario.getNString(10), SelectUsuario.getNString(11), SelectUsuario.getNString(12), SelectUsuario.getNString(13), SelectUsuario.getNString(14), SelectUsuario.getNString(15)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se seleccionen usuarios restringida de algunos datos
    public DefaultTableModel GeneracionTablaUsuariosRestringida() throws ClassNotFoundException, SQLException{
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
                            SelectUsuario.getNString(10), SelectUsuario.getNString(11), SelectUsuario.getNString(12)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se seleccionen administradores
    public DefaultTableModel GeneracionTablaAdministradores() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de administradores");
      
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT * FROM Administradores ");
       
        String data[][]={};
        String titulos[]={"ID Administrador","ID Rol","ID Usuario","Nombre","1er Apellido","2doApellido", "Telefono", "Correo eletrónico", "Observaciones"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectUsuario.next()){
            
           Object datos[]={SelectUsuario.getInt(1),SelectUsuario.getInt(2),SelectUsuario.getInt(3),SelectUsuario.getNString(4),SelectUsuario.getNString(5),SelectUsuario.getNString(6),SelectUsuario.getInt(7),SelectUsuario.getNString(8),SelectUsuario.getNString(9)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    //Modelo para la tabla cuando se seleccionen directores
    public DefaultTableModel GeneracionTablaDirectores() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de directores");
      
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT * FROM Directores ");
       
        String data[][]={};
        String titulos[]={"ID Director","ID Rol","ID Usuario","Nombre","1er Apellido","2doApellido", "Telefono", "Correo eletrónico", "Observaciones"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectUsuario.next()){
            
           Object datos[]={SelectUsuario.getInt(1),SelectUsuario.getInt(2),SelectUsuario.getInt(3),SelectUsuario.getNString(4),SelectUsuario.getNString(5),SelectUsuario.getNString(6),SelectUsuario.getInt(7),SelectUsuario.getNString(8),SelectUsuario.getNString(9)};
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
        ResultSet SelectGrupo = st.executeQuery("SELECT * FROM Grupos");
       
        String data[][]={};
        String titulos[]={"ID Grupo","ID Director","ID Usuarios","Nombre del Grupo", "Descripción"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectGrupo.next()){
            
           Object datos[]={SelectGrupo.getInt(1),SelectGrupo.getInt(2),SelectGrupo.getNString(3),SelectGrupo.getNString(4),SelectGrupo.getNString(5)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se selecciones proyectos
    public DefaultTableModel GeneracionTablaProyectos() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de Proyectos");
      
        Statement st = connect.createStatement();
        ResultSet SelectGrupo = st.executeQuery("SELECT * FROM Proyectos");
       
        String data[][]={};
        String titulos[]={"ID Proyecto", "ID Grupo", "Nombre del Proyecto", "Porcentaje Avance", "Estado Avance","Descripción"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectGrupo.next()){
            
           Object datos[]={SelectGrupo.getInt(1),SelectGrupo.getInt(2),SelectGrupo.getNString(3),SelectGrupo.getInt(4),SelectGrupo.getString(5),SelectGrupo.getString(6)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se seleccione Bitacora Control Usuarios
    public DefaultTableModel GeneracionTablaBitacoraUsuarios() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de Bitacoras de Usuarios");
      
        Statement st = connect.createStatement();
        ResultSet SelectBitacora = st.executeQuery("SELECT * FROM BitacoraControlUsuarios");
       
        String data[][]={};
        String titulos[]={"Numero Bitacora","ID Usuario Modificado","Nombre Usuario","Fecha del Registro","Hora del Registro", "Tabla del Rol Alterada", "Descripción"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectBitacora.next()){
            
           Object datos[]={SelectBitacora.getInt(1),SelectBitacora.getInt(2),SelectBitacora.getString(3),SelectBitacora.getDate(4),SelectBitacora.getTime(5),
           SelectBitacora.getString(6), SelectBitacora.getString(7)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    
    //Modelo para la tabla cuando se seleccione Bitacora Control Usuarios
    public DefaultTableModel GeneracionTablaBitacoraProyectos() throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta Generacion de tabla de Bitacoras de Proyectos");
      
        Statement st = connect.createStatement();
        ResultSet SelectBitacora = st.executeQuery("SELECT * FROM BitacoraAvanceProyectos");
       
        String data[][]={};
        String titulos[]={"Numero Bitacora","ID Proyecto Modificado","Fecha del Registro","Hora del Registro", "Porcentaje de Avance", "Estado Avance","Descripción"};
        DefaultTableModel modeloTabla;
        modeloTabla = new DefaultTableModel(data, titulos);
        
        while(SelectBitacora.next()){
            
           Object datos[]={SelectBitacora.getInt(1),SelectBitacora.getInt(2),SelectBitacora.getDate(3),SelectBitacora.getTime(4),SelectBitacora.getDouble(5),
           SelectBitacora.getString(6), SelectBitacora.getString(7)};
           modeloTabla.addRow(datos);
        }
        return modeloTabla;
    }
    }






