/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Date;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author caponte
 */
public class Usuario extends org.apache.struts.validator.ValidatorForm {

    private String nombreusuario;
    private String contrasena;
    private String nuevacontra;
    private String confirmar;
    private String nombre;
    private String email;
    private Integer privilegio;
    private static final String alphanumeric = "G12HIJdefgPQRSTUVWXYZabc56hijklmnopqAB78CDEF0KLMNO3rstu4vwxyz9";
 

    public Usuario(){
        super();
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombreusuario=" + nombreusuario + ", contrasena=" + contrasena + ", confirmar=" + confirmar + ", nombre=" + nombre + ", email=" + email + ", privilegio=" + privilegio + '}';
    }
    
  /*  @Override
     public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

        ActionErrors errors = new ActionErrors();

        if (getNombreusuario() == null) {
            errors.add("nombreusuario", new ActionMessage("error.nombreusuario.required"));
        }
        if(getContrasena() == null) {
            errors.add("contrasena", new ActionMessage("error.contrasena.required"));
        }
        if (getEmail() == null) {
            errors.add("email", new ActionMessage("error.email.required "));
        }

        return errors;
     }*/
    
    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNuevacontra() {
        return nuevacontra;
    }

    public void setNuevacontra(String nuevacontra) {
        this.nuevacontra = nuevacontra;
    }
    
    
    public String getConfirmar() {
        return confirmar;
    }

    public void setConfirmar(String confirmar) {
        this.confirmar = confirmar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Integer privilegio) {
        this.privilegio = privilegio;
    }
    
    public String generarContrasena(){
        int length = 8;
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = alphanumeric.charAt(rng.nextInt(alphanumeric.length()));
        }
        return new String(text);
    }
}