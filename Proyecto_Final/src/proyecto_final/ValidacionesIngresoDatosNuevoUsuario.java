/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyecto_final;

import java.text.SimpleDateFormat;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Byron Garcia
 */
public class ValidacionesIngresoDatosNuevoUsuario {
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
    public String ConvertidorFechaAString(Object fechaIngresada){
        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = SDF.format(fechaIngresada);
        
        return SDF.format(fechaFormateada);
    }
    
    
}
