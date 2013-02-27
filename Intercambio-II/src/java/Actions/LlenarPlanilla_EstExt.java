/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import Clases.PlanillaExt;
import Clases.Usuario;

import DBMS.DBMS;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author caponte
 */
public class LlenarPlanilla_EstExt extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String patronEmail = "^([_A-Za-z0-9-\\.\\+])+@([A-Za-z0-9-])+\\.([A-Za-z0-9-])+$";
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private static final String ERROR = "error";
    private static final String MODIFICAR = "modificar";
    private Pattern patron;
    private Matcher match;

    public boolean validate(final String username) {
        match = patron.matcher(username);
        return match.matches();
    }

    /**
     * This is the action called from the Struts framework.
     *
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {


        PlanillaExt p = (PlanillaExt) form;
        ActionErrors error = new ActionErrors();
        boolean huboError = false;

        // ####################################
        //        Validacion de datos.
        // ####################################

//        //Verifica que los apellidos no  esten vacios.
//        if (p.getApellidos().equals("")) {
//            error.add("apellidos", new ActionMessage("error.apellidos.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        //Verifica que los nombres no  esten vacios.
//        if (p.getNombres().equals("")) {
//            error.add("nombres", new ActionMessage("error.nombres.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        // Verificar escogencia de Sexo
//        if (p.getSexo().equals("")) {
//            error.add("sexo", new ActionMessage("error.sexo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        //Verifica que la nacionalidad.
//        if (p.getNacionalidad().equals("")) {
//            error.add("nacionalidad", new ActionMessage("error.nacionalidad.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//
//        // Cedula no vacia y bien estructurada (solo numeros)
//        if (p.getCedula().equals("")) {
//            error.add("cedula", new ActionMessage("error.cedula.required"));
//            saveErrors(request, error);
//            huboError = true;
//        } else if (!p.getCedula().matches("^[0 - 9]+")) {
//
//            error.add("cedula", new ActionMessage("error.cedula.malestructurada"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // Carnet no  vacio y bien estructurado
//        if (p.getCarnet().equals("")) {
//            error.add("carnet", new ActionMessage("error.carnet.required"));
//            saveErrors(request, error);
//            huboError = true;
//        } else if (p.getCarnet().matches("[0-9]{2}\\-[0-9]{5}")) {
//            error.add("carnet", new ActionMessage("error.carnet.malestructurado"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // Pasaporte no  vacio.
//        if (p.getPasaporte().equals("")) {
//            error.add("pasaporte", new ActionMessage("error.pasaporte.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        // Calle no  vacio.
//        if (p.getCalle().equals("")) {
//            error.add("calle", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // Ciudad no  vacio.
//        if (p.getCiudad().equals("")) {
//            error.add("ciudad", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // Estado no vacio.
//        if (p.getEstado().equals("")) {
//            error.add("estado", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        // Codigo postal no vacio
//        if (p.getCodPostal().equals("")){
//            error.add("codPostal", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        // Verifica que el telefono celular no sea vacio
//        if (p.getTelefonoCelular().equals("")){
//            error.add("telefonoCelular", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        // Verifica que el telefono de la casa no sea vacio
//        if (p.getTelefonoCasa().equals("")){
//            error.add("telefonoCasa", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        
//        //Verifica que el email no sea vacio y que este estructurado correctamente.
//        if (p.getEmail().equals("")) {
//            error.add("email", new ActionMessage("error.email.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // ####################################
//        //   Validacion del representante
//        // ####################################
//        
//        // Verifica que los nombres y apellidos del representante no esten vacios
//        if (p.getApellidoNombresRep().equals("")){
//            error.add("apellidoNombresRep", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }        

//        // Verificar escogencia de Nivel verbal del idioma.
//        if (p.getNivelVerbal().equals("")) {
//            error.add("nivelVerbal", new ActionMessage("error.verbal.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//        // Verificar escogencia de Nivel escrito del idioma. 
//        if (p.getNivelEscrito().equals("")) {
//            error.add("nivelEscrito", new ActionMessage("error.escrito.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//



//        //Verifica que el email del Representante no sea vacio y que este estructurado correctamente.
//        if (p.getEmailRep().equals("")) {
//            error.add("emailRep", new ActionMessage("error.email.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
////        } else if (validate(p.getEmailRep()) == false) {
////            error.add("emailRep", new ActionMessage("error.email.malformulado"));
////            saveErrors(request, error);
////            huboError = true;
////        }
//
//        //Verificaicon de nombre de universidad de destino 1 y 2
//
//        if (p.getNombreOpcion1().equals("")) {
//            error.add("nombreOpcion1", new ActionMessage("error.destino.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//        if (p.getNombreOpcion2().equals("")) {
//            error.add("nombreOpcion2", new ActionMessage("error.destino.required"));
//            saveErrors(request, error);
//            huboError = true;
//        }


//  ############### Comparar ambas fechas
//        
//        else if ((p.getFechaIni1().compareTo(p.getFechaFin1())) >= 0) {
//            error.add("fechaIni1", new ActionMessage("error.fecha.orden"));
//            saveErrors(request, error);
//            huboError = true;
//        }




// ############# Comparar ambas fechas.
//        if ((p.getFechaIni2().compareTo(p.getFechaFin2())) >= 0) {
//            error.add("fechaIni1", new ActionMessage("error.fecha.orden"));
//            saveErrors(request, error);
//            huboError = true;
//        }
//
//
//
//        // Indice no  vacio y bien estructurado
//        if (p.getIndice().equals("")) {
//            error.add("indice", new ActionMessage("error.campo.required"));
//            saveErrors(request, error);
//            huboError = true;
//        } else if (p.getCarnet().matches("[0-4]\\.[0-9]{4}|5\\.0{4}")) {
//
//            error.add("indice", new ActionMessage("error.indice.malestructurado"));
//            saveErrors(request, error);
//            huboError = true;
//        }

        Usuario u = new Usuario();
        u.setNombreusuario(p.getNombreUsuario());
        u.setConfirmar(p.getPeriodo());

        //PlanillaExt hay = DBMS.getInstance().obtenerPlanillaExt(u);


        if (huboError) {
            return mapping.findForward(ERROR);

        /*} else if (hay.getNombreUsuario() != null) {

            if (DBMS.getInstance().modificarPlanillaExt(p)) {
                return mapping.findForward(SUCCESS);
            } else {
                return mapping.findForward(FAIL);
            }*/
        } else if (DBMS.getInstance().agregarPlanillaExt(p)) {
            return mapping.findForward(SUCCESS);

        } else {
            return mapping.findForward(FAIL);

        }
    }
}