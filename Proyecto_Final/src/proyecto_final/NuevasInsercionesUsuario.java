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
import java.text.SimpleDateFormat;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Byron Garcia
 */
public class NuevasInsercionesUsuario {
    //Método para avalidar el correo electrónico
    public Boolean EmailValidator(String correo) throws AddressException{
      try{
        InternetAddress emailAddr = new InternetAddress(correo);
        emailAddr.validate();
        return true;
      }catch(Exception e){
          System.out.println(e);
          return false;
      }
    }
    
    //Método para convertir de la fecha que retorna el spinner al String para SQLServer
    public String ConvertidorFechaAString(Object valorSpinner){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        
        return SDF.format(valorSpinner);
    }
    
    //Método para ponerle nombres usuarios a combobox
    public void ComboBoxSetValueNombresUsuarios(JComboBox combobox) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de usuarios e insertar en ComboBox");
        
        Statement st = connect.createStatement();
        ResultSet SelectUsuario = st.executeQuery("SELECT Nombre_Usuario + ' '  + Primer_Apellido_Usuario + ' '  + Segundo_Apellido_Usuario FROM Usuarios");
        
        while(SelectUsuario.next()){
            combobox.addItem(SelectUsuario.getNString(1));
        }

    }
    
    //Método para ponerle nombres directores a combobox
    public void ComboBoxSetValueNombresDirectores(JComboBox combobox) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de directores e insertar en ComboBox");
        
        Statement st = connect.createStatement();
        ResultSet SelectNombreUsuario = st.executeQuery("SELECT Nombre_Director + ' '  + Primer_Apellido_Director + ' '  + Segundo_Apellido_Director FROM Directores");
        
        while(SelectNombreUsuario.next()){
            combobox.addItem(SelectNombreUsuario.getNString(1));
        }

    }
    
    //Método para ponerle nombres grupos a combobox
    public void ComboBoxSetValueNombresGrupos(JComboBox combobox) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de grupos e insertar en ComboBox");
        
        Statement st = connect.createStatement();
        ResultSet SelectNombreGrupo = st.executeQuery("SELECT Nombre_Grupo FROM Grupos");
        
        while(SelectNombreGrupo.next()){
            combobox.addItem(SelectNombreGrupo.getNString(1));
        }

    }
    
    //Método para ponerle nombres proyectos a combobox
    public void ComboBoxSetValueNombresProyectos(JComboBox combobox) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para consulta de proyectos e insertar en ComboBox");
        
        Statement st = connect.createStatement();
        ResultSet SelectNombreGrupo = st.executeQuery("SELECT Nombre_Proyecto FROM Proyectos");
        
        while(SelectNombreGrupo.next()){
            combobox.addItem(SelectNombreGrupo.getNString(1));
        }

    }
    
    //Metodo para verificar cantidad de caracteres
    public boolean StringLengthValidator(String palabra, int cantidadCaracteres){
        if (palabra.length() <= cantidadCaracteres){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Cantidad máxima de caracteres excedida");
        return false;
        }
        
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
    public void RegistroUsername(String loginName, String nombreUsuario) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para creacion Username");
        
        Statement st = connect.createStatement();
        
        CallableStatement addUser = connect.prepareCall("{call sp_adduser(?,?)}");
        addUser.setString(1, loginName);
        addUser.setString(2, nombreUsuario);
        addUser.execute();
        
    }

    //Metodo para eliminar Usuarios
     public void EliminacionUsuarioPorNombreCompleto(String nombreUsuario) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para Eliminar Usuario Por Nombre Completo");
        
        CallableStatement CrearUserName = connect.prepareCall("{call EliminacionUsuarioPorNombreCompleto(?)}");
        
        CrearUserName.setString(1, nombreUsuario);
        
        CrearUserName.execute();

    }

     //Método para Registrar nuevo proyecto en tabla proyectos
     public void RegistrarNuevoProyecto(int idGrupo, String nombreProyecto, int porcentajeAvance, String observacion) throws ClassNotFoundException, SQLException{
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para registrar Nuevo Proyecto");
        
        Statement st = connect.createStatement();
        
        CallableStatement NuevoProyecto = connect.prepareCall("{call InsercionNuevoProyecto(?,?,?,?)}");
        NuevoProyecto.setInt(1, idGrupo);
        NuevoProyecto.setString(2, nombreProyecto);
        NuevoProyecto.setInt(3, porcentajeAvance);
        NuevoProyecto.setString(4, observacion);
        NuevoProyecto.execute();
     }
     
     //Método para Registrar nuevo grupo en tabla Grupos
     public void RegistrarNuevoGrupo(int idDirector, String idUsuarios, String NombreGrupo, String observacion) throws ClassNotFoundException, SQLException{
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para registrar Nuevo Grupo");
        
        Statement st = connect.createStatement();
        
        CallableStatement CrearGrupo = connect.prepareCall("{call InsercionNuevoGrupo(?,?,?,?)}");
        CrearGrupo.setInt(1, idDirector);
        CrearGrupo.setString(2, idUsuarios);
        CrearGrupo.setString(3, NombreGrupo);
        CrearGrupo.setString(4, observacion);
        CrearGrupo.execute();
     }

     //Metodo para actualizar el porcentaje de avance del proyecto
     public void RegistrarAvanceProyecto(String nombreProyecto, int porcentajeAvance, String observacion) throws ClassNotFoundException, SQLException{
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para registrar avance de proyecto");
        
        Statement st = connect.createStatement();
        
        CallableStatement avanceProyecto = connect.prepareCall("{call ActualizacionPorcentajeAvanceProyecto(?,?,?)}");
        avanceProyecto.setString(1, nombreProyecto);
        avanceProyecto.setInt(2, porcentajeAvance);
        avanceProyecto.setString(3, observacion);
        avanceProyecto.execute();
     }
     
     //Metodo para agregar usuarios al grupo
     public void AgregarUsuariosAGrupo(String nombreGrupo, String idUsuarios, String observacion) throws ClassNotFoundException, SQLException{
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para agregar usuarios a grupo");
        
        Statement st = connect.createStatement();
        
        CallableStatement AgregarUsuario = connect.prepareCall("{call ActualizacionUsuariosGrupos(?,?,?)}");
        AgregarUsuario.setString(1, nombreGrupo);
        AgregarUsuario.setString(2, idUsuarios);
        AgregarUsuario.setString(3, observacion);
        AgregarUsuario.execute();
     }
     
     //Metodo para eliminar Grupo
     public void EliminarGrupoPorNombre(String nombreGrupo, String descripcion) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para eliminar Grupo");
        
        Statement st = connect.createStatement();
        CallableStatement eliminarProyecto = connect.prepareCall("{call EliminacionGrupoProNombre(?,?)}");
        eliminarProyecto.setString(1, nombreGrupo);
        eliminarProyecto.setString(2, descripcion);
        eliminarProyecto.execute();
    }
     
     //Metodo para eliminar Proyecto
     public void EliminarProyectoPorNombre(String nombreProyecto, String observacion) throws ClassNotFoundException, SQLException{
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionURL = "jdbc:sqlserver://PORTATI01\\SQLEXPRESS2012:1433;databaseName=ProyectoFinal;user=sa;password=sa;";
        Connection connect = DriverManager.getConnection(connectionURL);
        System.out.println("Conectada para eliminar Proyecto");
        
        Statement st = connect.createStatement();
        CallableStatement eliminarProyecto = connect.prepareCall("{call EliminacionProyectoPorNombre(?,?)}");
        eliminarProyecto.setString(1, nombreProyecto);
        eliminarProyecto.setString(2, observacion);
        eliminarProyecto.execute();

    }
}
