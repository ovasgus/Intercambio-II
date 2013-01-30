/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package DBMS;

import Clases.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.jsp.jstl.sql.Result;
import java.util.Iterator;

/**
 *
 * @author gustavo
 */
public class DBMS {

    static private Connection conexion;

    protected DBMS() {
    }
    static private DBMS instance = null;

    static public DBMS getInstance() {
        if (null == DBMS.instance) {
            DBMS.instance = new DBMS();
        }
        conectar();
        return DBMS.instance;
    }

    public static boolean conectar() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/dycicle",
                    "postgres",
                    "postgres");
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Usuario consultarUsuario(Usuario u) {

        String sqlquery = "SELECT * FROM \"dycicle\".usuario"
                + " WHERE nombreusuario ='" + u.getNombreusuario() + "' " + " AND "
                + "contrasena ='" + u.getContrasena() + "'";
        try {

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {

                u.setPrivilegio(rs.getInt("privilegio"));
                u.setNombre(rs.getString("nombre"));
                u.setEmail(rs.getString("email"));
                return u;
            }


        } catch (SQLException ex) {
            Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
        }

        u.setNombreusuario(null);

        return u;
    }

    public Usuario consultarPreregistro(Usuario u) {

        String sqlquery = "SELECT * FROM \"dycicle\".preregistro"
                + " WHERE nombreusuario ='" + u.getNombreusuario() + "'";
        try {

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {

                u.setPrivilegio(rs.getInt("privilegio"));
                u.setNombre(rs.getString("nombre"));
                return u;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBMS.class.getName()).log(Level.SEVERE, null, ex);
        }

        u.setNombreusuario(null);
        return u;
    }

    public Boolean agregarUsuario(Usuario u) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".usuario VALUES ('" + u.getNombreusuario()
                    + "', '" + u.getEmail() + "', '" + u.getPrivilegio()
                    + "', '" + u.getNombre() + "','" + u.getContrasena() + "');";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean agregarGestor(Gestor g) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".gestor VALUES ('" + g.getNombreusuario()
                    + "', '" + g.getNombre() + "');";
            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean agregarCoordUSB(Postulante p) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".postulante VALUES ('" + p.getNombreusuario()
                    + "', '" + p.getCodigo() + "', '" + p.getTipo() + "', 'null', '"
                    + p.getNombreCarrera() + "');";
            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            System.out.println();
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean agregarUniExt(Postulante p) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".postulante VALUES ('" + p.getNombreusuario()
                    + "', '" + p.getCodigo() + "', '" + p.getTipo() + "',  '" + p.getNombreUniExt()
                    + "', null');";
            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean aceptarPreregistro(Usuario u) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".usuario SELECT * FROM \"dycicle\".preregistro"
                    + " WHERE  (nombreusuario='" + u.getNombreusuario() + "');";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);
            eliminarPreregistro(u);
            int tmp = i;
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean preRegistroUsuario(Usuario u) {
        try {


            String confirmacion = "SELECT * FROM \"dycicle\".usuario"
                    + " WHERE nombreusuario ='" + u.getNombreusuario() + "';";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(confirmacion);
            System.out.println(rs);
            ResultSet t = rs;
            if (rs.next()) {
                System.out.println(rs);
                return false;
            } else {

                String sqlquery = "INSERT INTO \"dycicle\".preregistro VALUES ('" + u.getNombreusuario()
                        + "', '" + u.getEmail() + "', '" + u.getPrivilegio()
                        + "', '" + u.getNombre() + "','" + u.getContrasena() + "');";
                stmt = conexion.createStatement();
                System.out.println(sqlquery);
                Integer i = stmt.executeUpdate(sqlquery);
                System.out.println(i);
                return i > 0;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean eliminarUsuario(Usuario u) {
        try {

            String nombreusuario = u.getNombreusuario();
            String sqlquery = "SELECT privilegio FROM \"dycicle\".usuario"
                    + " WHERE nombreusuario = '" + nombreusuario + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            boolean prox = rs.next();
            int privilegio = rs.getInt("privilegio");

            if (privilegio == 2) {
                sqlquery = "DELETE FROM \"dycicle\".gestor WHERE nombreusuario = '"
                        + nombreusuario + "'";
            } else if (privilegio == 3 || privilegio == 4) {
                sqlquery = "DELETE FROM \"dycicle\".postulante WHERE nombreusuario = '"
                        + nombreusuario + "'";
            } else if (privilegio == 5 || privilegio == 6) {
                sqlquery = "DELETE FROM \"dycicle\".estudiante WHERE nombreusuario = '"
                        + nombreusuario + "'";
                System.out.println(sqlquery);
            }

            stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);

            sqlquery = "DELETE FROM \"dycicle\".usuario" + " WHERE nombreusuario = '"
                    + nombreusuario + "'";
            stmt = conexion.createStatement();
            i = stmt.executeUpdate(sqlquery);

            return i > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean eliminarPreregistro(Usuario u) {
        try {
            String sqlquery = "DELETE FROM \"dycicle\".preregistro" + " WHERE nombreusuario = '"
                    + u.getNombreusuario() + "'";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean modificarUsuario(Usuario u) {
        try {
            String sqlquery = "UPDATE \"dycicle\".usuario SET email='" + u.getEmail()
                    + "', nombre='" + u.getNombre()
                    + "', privilegio='" + u.getPrivilegio()
                    + "' WHERE nombreusuario = '"
                    + u.getNombreusuario() + "'";
            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Usuario obtenerEstadoSolicitud(Usuario u) {
        try {

            String sqlquery = "SELECT estadopostulacion FROM \"dycicle\".PlanillaUSB SET WHERE"
                    + " nombreusuario = '" + u.getNombreusuario() + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            while (rs.next()) {
                u.setConfirmar(rs.getString("estadopostulacion"));
            }

            return u;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Boolean cambiarEstadoSolicitud(Usuario u) {
        try {

            String sqlquery = "UPDATE \"dycicle\".PlanillaUSB SET estadopostulacion ='"
                    + u.getConfirmar()
                    + "' WHERE nombreusuario = '"
                    + u.getNombreusuario() + "'";

            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean modificarPerfil(Usuario u) {
        try {
            String sqlquery = "UPDATE \"dycicle\".usuario SET contrasena ='" + u.getConfirmar()
                    + "', email='" + u.getEmail()
                    + "', nombre='" + u.getNombre()
                    + "', privilegio='" + u.getPrivilegio()
                    + "' WHERE nombreusuario = '"
                    + u.getNombreusuario() + "'";
            Statement stmt = conexion.createStatement();
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public ArrayList<Usuario> listarUsuarios() {

        ArrayList<Usuario> usrs = new ArrayList<Usuario>(0);

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".usuario";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombreusuario(rs.getString("nombreusuario"));
                u.setEmail(rs.getString("email"));
                usrs.add(u);
            }

        } catch (SQLException ex) {
            System.out.println("EXCEPCION");
            ex.printStackTrace();
        }
        return usrs;
    }

    public ArrayList<String> listarDocumentos(Usuario u) throws SQLException {

        String sqlquery = "SELECT * FROM \"dycicle\".archivosestudianteusb WHERE nombreusuario='"
                + u.getNombreusuario() + "';";

        Statement stmt = conexion.createStatement();
        System.out.println(sqlquery);
        ResultSet rs = stmt.executeQuery(sqlquery);
        String p = null;

        while (rs.next()) {
            p = rs.getString("direccion");
        }

        ArrayList<String> archivos = new ArrayList<String>(0);

        if (p == null) {
            return null;
        } else {

            File dir = new File(p);
            for (File child : dir.listFiles()) {
                String tmp;

                tmp = child.getAbsolutePath();
                archivos.add(tmp);
            }
            return archivos;
        }
    }

    public ArrayList<Usuario> listarEstudiantesPostulados() {

        ArrayList<Usuario> usrs = new ArrayList<Usuario>(0);

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".estudiante";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            ResultSet rs = stmt.executeQuery(sqlquery);

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombreusuario(rs.getString("nombreusuario"));
                u.setEmail(rs.getString("email"));
                usrs.add(u);
            }


//            sqlquery = "SELECT * FROM \"dycicle\".planillausb WHERE nombreusuario='"
//                    + u.getNombreusuario()+ "'";
//
//            stmt = conexion.createStatement();
//            System.out.println(sqlquery);
//            rs = stmt.executeQuery(sqlquery);
//            while (rs.next()) {
//                u.setNombreusuario(rs.getString("nombreusuario"));
//                u.setConfirmar(rs.getString("estadoPostulacion"));
//                usrs.add(u);
//            }




        } catch (SQLException ex) {
            System.out.println("EXCEPCION");
            ex.printStackTrace();
        }
        return usrs;
    }

    public ArrayList<Usuario> listarEstudiantes(Usuario u) {

        ArrayList<Usuario> usrs = new ArrayList<Usuario>(0);

        try {

//            Acomodar Query para que me devuelva los estudiantes con la misma carrera
//            que el que lo invoca
            String sqlquery = "SELECT * FROM \"dycicle\".estudiante WHERE "
                    + "carrera = '" + u.getNombreusuario() + "';";

            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {
                Usuario t = new Usuario();
                t.setNombreusuario(rs.getString("nombreusuario"));
                usrs.add(t);
            }

        } catch (SQLException ex) {
            System.out.println("EXCEPCION");
            ex.printStackTrace();
        }
        return usrs;
    }

    public ArrayList<Usuario> listarPreregistro() {

        ArrayList<Usuario> usrs = new ArrayList<Usuario>(0);

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".preregistro";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNombreusuario(rs.getString("nombreusuario"));
                u.setEmail(rs.getString("email"));
                usrs.add(u);
            }

        } catch (SQLException ex) {
            System.out.println("EXCEPCION");
            ex.printStackTrace();
        }
        return usrs;
    }

    public ArrayList<Usuario> listarIdiomas(Usuario u) {

        ArrayList<Usuario> usrs = new ArrayList<Usuario>(0);

        try {

//            Acomodar Query para que me devuelva los estudiantes con la misma carrera
//            que el que lo invoca
            String sqlquery = "SELECT * FROM \"dycicle\".idiomas WHERE "
                    + "nombreusuario = '" + u.getNombreusuario() + "'";

            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            ResultSet rs = stmt.executeQuery(sqlquery);
           
            while (rs.next()) {
                Usuario t = new Usuario();
                t.setNombre(rs.getString("idioma"));
                t.setConfirmar(rs.getString("nivelverbal"));
                t.setEmail(rs.getString("nivelescrito"));

                usrs.add(t);
            }

        } catch (SQLException ex) {
            System.out.println("EXCEPCION");
            ex.printStackTrace();
        }
        return usrs;
    }

    public Usuario obtenerDatos(Usuario u) {

        Usuario datos = new Usuario();

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".usuario"
                    + " WHERE nombreusuario ='" + u.getNombreusuario() + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {
                datos.setNombre(rs.getString("nombre"));
                datos.setNombreusuario(rs.getString("nombreusuario"));
                datos.setEmail(rs.getString("email"));
                datos.setPrivilegio(rs.getInt("privilegio"));
                datos.setContrasena(rs.getString("contrasena"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return datos;
    }

    public PlanillaUSB obtenerPlanillaUSB(Usuario u) {

        PlanillaUSB datos = new PlanillaUSB();

        try {

            String sqlquery = "SELECT * FROM \"dycicle\".planillaUSB"
                    + " WHERE nombreusuario ='" + u.getNombreusuario() + "';";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            while (rs.next()) {
                datos.setNombreUsuario(rs.getString("NombreUsuario"));
                datos.setApellidos(rs.getString("Apellido"));
                datos.setPeriodo(rs.getString("Periodo"));
                datos.setCedula(rs.getString("Cedula"));
                datos.setCarnet(rs.getString("Carnet"));
                datos.setPasaporte(rs.getString("Pasaporte"));
                datos.setNombres(rs.getString("Nombre"));
                datos.setSexo(rs.getString("Sexo"));
                datos.setCiudad(rs.getString("Ciudad"));
                datos.setCalle(rs.getString("Calle"));
                datos.setEstado(rs.getString("Estado"));
                datos.setCodPostal(rs.getString("CodPostal"));
                datos.setTelefonoCasa(rs.getString("TelefonoCasa"));
                datos.setTelefonoCelular(rs.getString("TelefonoCel"));
                datos.setFax(rs.getString("Fax"));
                datos.setEmail(rs.getString("Email"));
                datos.setFechaNacimiento(rs.getString("FechaNac"));
                datos.setNacionalidad(rs.getString("Nacionalidad"));
                datos.setApellidoNombresRep(rs.getString("apellidoNombres"));
                datos.setTlfRep(rs.getString("Telefono"));
                datos.setEmailRep(rs.getString("EmailRep"));
                datos.setRelacion(rs.getString("TipoRelacion"));
                datos.setDireccionRep(rs.getString("Direccion"));
                datos.setIndice(rs.getString("Indice"));
                datos.setCarrera(rs.getString("Carrera"));
                datos.setOpcion(rs.getString("Opcion"));
                datos.setCreditosApro(rs.getInt("CredAprob"));
                datos.setFechaIni1(rs.getString("FechaIda1"));
                datos.setFechaFin1(rs.getString("FechaRegreso1"));
                datos.setNombreOpcion1(rs.getString("Universidad1"));
                datos.setFechaIni2(rs.getString("FechaIda2"));
                datos.setFechaFin2(rs.getString("FechaRegreso2"));
                datos.setNombreOpcion2(rs.getString("Universidad2"));
                datos.setIdiomaDest(rs.getString("Idioma"));
                datos.setNivelVerbal(rs.getString("NivelVerbal"));
                datos.setNivelEscrito(rs.getString("NivelEscrito"));
                datos.setNombreProg1(rs.getString("NombreProg1"));
                datos.setNombreProg2(rs.getString("NombreProg2"));
                datos.setFuenteFinanciamiento(rs.getString("Financiamiento"));
                datos.setDescripcion(rs.getString("Descripcion"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return datos;
    }

    public boolean agregarAnuncio(Anuncio a) {

        int insert = 0;
        Integer i = 0;

        try {

            Statement stmt = conexion.createStatement();
            String sqlquery = "INSERT INTO \"dycicle\".anuncio VALUES ('" + a.getTitulo()
                    + "', '" + a.getMensaje() + "', '";
            String tmp;

            if (!a.getDRIC().equals("")) {

                insert++;
                tmp = sqlquery + a.getDRIC() + "');";
                stmt = conexion.createStatement();
                System.out.println(tmp);
                i = i + stmt.executeUpdate(tmp);
            }

            if (!a.getDecanatos().equals("")) {

                insert++;
                tmp = sqlquery + a.getDecanatos() + "');";
                stmt = conexion.createStatement();
                System.out.println(sqlquery);
                i = i + stmt.executeUpdate(tmp);
            }

            if (!a.getCoordinaciones().equals("")) {

                insert++;
                tmp = sqlquery + a.getCoordinaciones() + "');";
                stmt = conexion.createStatement();
                System.out.println(sqlquery);
                i = i + stmt.executeUpdate(tmp);
            }

            if (!a.getEstExt().equals("")) {

                insert++;
                tmp = sqlquery + a.getEstExt() + "');";
                stmt = conexion.createStatement();
                System.out.println(sqlquery);
                i = i + stmt.executeUpdate(tmp);
            }

            if (!a.getEstUSB().equals("")) {

                insert++;
                tmp = sqlquery + a.getEstUSB() + "');";
                stmt = conexion.createStatement();
                System.out.println(sqlquery);
                i = i + stmt.executeUpdate(tmp);
            }

            return (insert == i.intValue());

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public Boolean modificarPlanillaUSB(PlanillaUSB p) {

        try {
            String sqlquery = "UPDATE \"dycicle\".planillaUSB SET"
                    + " nombreusuario='" + p.getNombreUsuario() // Nombre de usuario.
                    // Datos personales
                    + "', periodo='" + p.getPeriodo()
                    + "', cedula='" + p.getCedula()
                    + "', carnet='" + p.getCarnet()
                    + "', pasaporte='" + p.getPasaporte()
                    + "', nombre='" + p.getNombres()
                    + "', apellido='" + p.getApellidos()
                    + "', sexo='" + p.getSexo()
                    + "', calle='" + p.getCalle()
                    + "', ciudad='" + p.getCiudad()
                    + "', estado='" + p.getEstado()
                    + "', codpostal='" + p.getCodPostal()
                    + "', telefonocel='" + p.getTelefonoCelular()
                    + "', telefonocasa='" + p.getTelefonoCasa()
                    + "', fax='" + p.getFax()
                    + "', email='" + p.getEmail()
                    + "', fechanac='" + p.getFechaNacimiento() //Fecha nacimiento
                    + "', nacionalidad='" + p.getNacionalidad()
                    //$$$$$$$$$$$$$$$$$
                    // Falta la foto!!
                    //$$$$$$$$$$$$$$$$$
                    // Datos del representante
                    + "', apellidonombres='" + p.getApellidoNombresRep()
                    + "', telefono='" + p.getTlfRep()
                    + "', emailrep='" + p.getEmailRep()
                    + "', tiporelacion='" + p.getRelacion()
                    + "', direccion='" + p.getDireccionRep()
                    //Antecedentes Academicos
                    + "', indice='" + p.getIndice()
                    + "', carrera='" + p.getCarrera()
                    + "', opcion='" + p.getNombreOpcion2()
                    + "', credaprob='" + p.getCreditosApro()
                    // Plan de estudio"
                    + "', fechaida1='" + p.getFechaIni1()
                    + "', fecharegreso1='" + p.getFechaFin1()
                    + "', universidad1='" + p.getNombreOpcion1()
                    + "', fechaida2='" + p.getFechaIni2()
                    + "', fecharegreso2='" + p.getFechaFin2()
                    + "', universidad2='" + p.getNombreOpcion2()
                    + "', idioma='" + p.getIdiomaDest()
                    + "', nivelverbal='" + p.getNivelVerbal()
                    + "', nivelescrito='" + p.getNivelEscrito()
                    + "', nombreprog1='" + p.getNombreProg1()
                    + "', nombreprog2='" + p.getNombreProg2()
                    // Financiamiento
                    + "', financiamiento='" + p.getFuenteFinanciamiento()
                    + "', descripcion='" + p.getDescripcion()
                    + "'  WHERE nombreusuario='" + p.getNombreUsuario()
                    + "' AND periodo='" + p.getPeriodo() + "'";

            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);

            return i > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean agregarPlanillaUSB(PlanillaUSB p) {
        try {
            String sqlquery = "INSERT INTO \"dycicle\".planillaUSB VALUES ("
                    + "'Tramitandose',"
                    + "'" + p.getNombreUsuario() // Nombre de usuario.
                    // Datos personales
                    + "', '" + p.getPeriodo()
                    + "', '" + p.getCedula()
                    + "', '" + p.getCarnet()
                    + "', '" + p.getPasaporte()
                    + "', '" + p.getNombres()
                    + "', '" + p.getApellidos()
                    + "', '" + p.getSexo()
                    + "', '" + p.getCalle()
                    + "', '" + p.getCiudad()
                    + "', '" + p.getEstado()
                    + "', '" + p.getCodPostal()
                    + "', '" + p.getTelefonoCelular()
                    + "', '" + p.getTelefonoCasa()
                    + "', '" + p.getFax()
                    + "', '" + p.getEmail()
                    + "', '" + p.getFechaNacimiento() //Fecha nacimiento
                    + "', '" + p.getNacionalidad()
                    //$$$$$$$$$$$$$$$$$
                    // Falta la foto!!
                    //$$$$$$$$$$$$$$$$$
                    // Datos del representante
                    + "', '" + p.getApellidoNombresRep()
                    + "', '" + p.getTlfRep()
                    + "', '" + p.getEmailRep()
                    + "', '" + p.getRelacion()
                    + "', '" + p.getDireccionRep()
                    //Antecedentes Academicos
                    + "', '" + p.getIndice()
                    + "', '" + p.getCarrera()
                    + "', '" + p.getNombreOpcion2()
                    + "', '" + p.getCreditosApro()
                    // Plan de estudio"
                    + "', '" + p.getFechaIni1()
                    + "', '" + p.getFechaFin1()
                    + "', '" + p.getNombreOpcion1()
                    + "', '" + p.getFechaIni2()
                    + "', '" + p.getFechaFin2()
                    + "', '" + p.getNombreOpcion2()
                    + "', '" + p.getIdiomaDest()
                    + "', '" + p.getNivelVerbal()
                    + "', '" + p.getNivelEscrito()
                    + "', '" + p.getNombreProg1()
                    + "', '" + p.getNombreProg2()
                    // Financiamiento
                    + "', '" + p.getFuenteFinanciamiento()
                    + "', '" + p.getDescripcion()
                    + "');";

            Statement stmt = conexion.createStatement();

            System.out.println(sqlquery);

            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Boolean agregarEstudiante(EstudianteUSB e) {
        try {

            String sesionActiva = e.getOrigen();
            String[] info = DBMS.getInstance().getInfoPostulante(sesionActiva);

            String sqlquery = "INSERT INTO \"dycicle\".estudiante VALUES ('" + e.getNombreusuario()
                    + "', '" + info[0] + "', '" + e.getNombre()
                    + "', '" + e.getApellidos() + "', '" + info[3]
                    + "', '" + info[1] + "', 'null', 'null', 'null', 'null', 'null', "
                    + " 'null', 'null', 'null', '" + e.getEmail() + "', '2012-11-27 23:43:11.080', 'null', "
                    + "'/home/caponte/');";
            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);

            if (i > 0) {
                PlanillaUSB p = new PlanillaUSB();
                p.setNombreUsuario(e.getNombreusuario());
                p.setPeriodo("2013-2014");
                Boolean res = DBMS.getInstance().agregarPlanillaUSB(p);

                return res;
            }

            return i > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    /* public Boolean agregarEstudianteInternacional(Estudiante e, EstudianteInternacional inter,
     AntecedentesAcademicos a, PlanDeEstudio plan, PeriodosPlan per) {
     try {
     String sqlquery = "INSERT INTO \"dycicle\".estudiante VALUES ('" + e.getNombreUsuario()
     + "', '" + e.getApellidoP() + "', '" + e.getApellidoM()
     + "', '" + e.getNombre1() + "','" + e.getNombre2() 
     + "', '" + e.getSexo() + "','" + e.getDireccionRep() 
     + "', '" + e.getTelefonoCelular() + "','" + e.getTelefonoCasa() 
     + "', '" + e.getTelefonoFax() + "','" + e.getEmail()
     + "', '" + e.getFechaNacimiento() + "','" + e.getNacionalidad()
     + "', '" + e.getPaisNac() + "','" + e.getCiudadNac() +"');";
            
     String sqlquery2 = "INSERT INTO \"dycicle\".EstudianteInternacional VALUES ('" + inter.getNombreUsuario()
     + "', '" + inter.getPasaporte() + "', '" + inter.getLenguaMaterna() + "');";
            
     String sqlquery3 = "INSERT INTO \"dycicle\".AntecedentesAcademicos VALUES ('" + a.getNombreUsuario()
     + "', '" + a.getIndice() + "', '" + a.getCarrera() 
     + "', '" + a.getDecanato() + "', '" + a.getCoordinacion() 
     + "', '" + a.getAnosCursados() + "', '" + a.getTipoEstudiante()
     + "', '" + a.getCursos() + "', '" + a.getPremios()
     + "', '" + a.getDistinciones() +"');"; 
            
     String sqlquery4 = "INSERT INTO \"dycicle\".PlanDeEstudio VALUES ('" + plan.getNombreUsuario()
     + "', '" + plan.getMotivacion() + "', '" + plan.getFechaIda() 
     + "', '" + plan.getFechaRegreso() + "', '" + plan.getUniversidad1() 
     + "', '" + plan.getUniversidad2() + "', '" + plan.getIdioma()
     + "', '" + plan.getNivelVerbal() + "', '" + plan.getNivelEscrito() + "');"; 
            
     String sqlquery5 = "INSERT INTO \"dycicle\".PeriodosPlan VALUES ('" + per.getNombreUsuario()
     + "', '" + per.getPeriodo() + "', '" + per.getCodigo()
     + "', '" + per.getCreditos() +  "');"; 
            
     Statement stmt = conexion.createStatement();
            
     System.out.println(sqlquery);
     System.out.println(sqlquery2);
     System.out.println(sqlquery3);
     System.out.println(sqlquery4);
     System.out.println(sqlquery5);
            
     Integer i = stmt.executeUpdate(sqlquery);
     Integer i2 = stmt.executeUpdate(sqlquery2);
     Integer i3 = stmt.executeUpdate(sqlquery3);
     Integer i4 = stmt.executeUpdate(sqlquery4);
     Integer i5 = stmt.executeUpdate(sqlquery5);
            
     return (i > 0)&&(i2 > 0)&&(i3 > 0)&&(i4 > 0)&&(i5 > 0);
            
     } catch (SQLException ex) {
     ex.printStackTrace();
     }
     return false;
     } */
    public int obtenerNumeroPlanilla() {

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".nroPlanillaUSB";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            while (rs.next()) {
                int numero = rs.getInt("privilegio");
                numero++;
                sqlquery = "UPDATE \"dycicle\".nroPlanillaUSB SET nro='" + numero + "'";
                stmt = conexion.createStatement();
                Integer i = stmt.executeUpdate(sqlquery);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 1;
    }

    public String[] existeEmail(String email) {

        String[] info = new String[3];

        try {
            String sqlquery = "SELECT nombreusuario, contrasena, email "
                    + "FROM \"dycicle\".usuario"
                    + " WHERE email = '" + email + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            if (rs.next()) {

                for (int i = 0; i < 1; i++) {

                    info[0] = rs.getString("nombreusuario");
                    info[1] = rs.getString("contrasena");
                    info[2] = rs.getString("email");

                }

            } else {
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return info;

    }

    public String[] existeUsuario(String usuario) {

        String[] info = new String[3];

        try {
            String sqlquery = "SELECT nombreusuario, contrasena, email "
                    + "FROM \"dycicle\".usuario"
                    + " WHERE nombreusuario = '" + usuario + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            if (rs.next()) {

                for (int i = 0; i < 1; i++) {

                    info[0] = rs.getString("nombreusuario");
                    info[1] = rs.getString("contrasena");
                    info[2] = rs.getString("email");
                }

            } else {
                return null;
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return info;

    }

    public String[] getInfoPostulante(String usuario) {

        String info[] = new String[4];

        try {
            String sqlquery = "SELECT Tipo, Codigo, NombreUniExt, NombreCarrera "
                    + "FROM \"dycicle\".postulante"
                    + " WHERE nombreusuario = '" + usuario + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);

            if (rs.next()) {

                for (int i = 0; i < 1; i++) {

                    info[0] = rs.getString("tipo");
                    info[1] = rs.getString("codigo");
                    info[2] = rs.getString("nombreuniext");
                    info[3] = rs.getString("nombrecarrera");
                }

            } else {
                return null;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return info;

    }

    public EstudianteUSB obtenerDatosUSB(Usuario u) {

        EstudianteUSB datos = new EstudianteUSB();

        try {
            String sqlquery = "SELECT * FROM \"dycicle\".estudianteusb"
                    + " WHERE nombreusuario ='" + u.getNombreusuario() + "'";

            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sqlquery);
            while (rs.next()) {
                datos.setNombreusuario(rs.getString("nombreusuario"));
                datos.setCarnet(rs.getString("carnet"));
                datos.setPasaporte(rs.getString("pasaporte"));
                datos.setCedula(rs.getString("cedula"));

            }

            return datos;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;



    }

    public boolean InsertarPath(String path, Usuario user) {
        try {

            EstudianteUSB usuario = obtenerDatosUSB(user);

            String sqlquery = "INSERT INTO \"dycicle\".archivosestudianteusb VALUES("
                    + "'" + user.getNombreusuario() + "','" + usuario.getCedula() + "','" + usuario.getCarnet()
                    + "','jo','" + path + "','jo','jo');";

            Statement stmt = conexion.createStatement();
            System.out.println(sqlquery);
            Integer i = stmt.executeUpdate(sqlquery);
            return i > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean InsertarIdioma(Idiomas idioma) {

        try {

            ArrayList escri = idioma.getListEscrito();
            ArrayList ver = idioma.getListVerbal();
            ArrayList idio = idioma.getListIdioma();
            for (int i = 0; i < idio.size(); i++) {
                String sqlquery = "INSERT INTO \"dycicle\".idiomas VALUES("
                        + "'" + idioma.getNombreusuario() + "','" + idio.get(i) + "','"
                        + ver.get(i) + "','" + escri.get(i) + "');";
                System.out.println(sqlquery);
                Statement stmt = conexion.createStatement();
                Integer j = stmt.executeUpdate(sqlquery);
            }
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public boolean InsertarPlan(PlanDeEstudio plan) {

        try {

            ArrayList codusb = plan.getListCodigoUSB();
            ArrayList coduniv = plan.getListCodigoUniv();
            ArrayList creusb = plan.getListCreditoUSB();
            ArrayList creuniv = plan.getListCreditoUniv();
            ArrayList nomusb = plan.getListMateriaUSB();
            ArrayList nomuniv = plan.getListMateriaUniv();
            String nombre = plan.getNombreUsuario();
            for (int i = 0; i < codusb.size(); i++) {
                String sqlquery = "INSERT INTO \"dycicle\".planestudio VALUES("
                        + "'" + nombre
                        + "','" + codusb.get(i)
                        + "','" + nomusb.get(i)
                        + "','" + creusb.get(i)
                        + "','" + coduniv.get(i)
                        + "','" + nomuniv.get(i)
                        + "','" + creuniv.get(i)
                        + "');";
                System.out.println(sqlquery);
                Statement stmt = conexion.createStatement();
                Integer j = stmt.executeUpdate(sqlquery);
                return true;
            }
            } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;

    }
    
     public boolean AdminPorDefecto(Usuario admin) {
          try 
          {
              String NombreUsuario = "admin";
              String Email = "email@gmail.com";
              int Privilegio = 1;
              String Nombre = "Administrador";
              String Contrasena = "admin1234";
              
              String sqlquery = "INSERT INTO \"dycicle\".USUARIO VALUES("
                        + "'"   + NombreUsuario
                        + "','" + Email
                        + "','" + Privilegio
                        + "','" + Nombre
                        + "','" + Contrasena
                        + "');";
              Statement stmt = conexion.createStatement();
              System.out.println(sqlquery);
              Integer i = stmt.executeUpdate(sqlquery);
              return i > 0;
          } catch (SQLException ex) {
              ex.printStackTrace();
          }
        return false;
     }
}

