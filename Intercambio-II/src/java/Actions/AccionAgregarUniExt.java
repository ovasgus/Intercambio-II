package Actions;

import Clases.Correo;
import Clases.Postulante;
import DBMS.*;
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
 * @author dreabalbas
 */
public class AccionAgregarUniExt extends org.apache.struts.action.Action {

    private static final String patronEmail = "^([_A-Za-z0-9-\\.\\+])+@([A-Za-z0-9-])+\\.([A-Za-z0-9-])+$";
    private Pattern patron;
    private Matcher match;
    private static final String SUCCESS = "success";
    private static final String FAIL = "failure";
    private static final String ERROR = "error";
    
    public AccionAgregarUniExt() {
        patron = Pattern.compile(patronEmail);
    }

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
        
        Postulante p = (Postulante) form;
        ActionErrors error = new ActionErrors();
        boolean huboError = false;
        String pswd = p.generarContrasena();
        String confPswd = pswd;
        String mail = p.getEmail();

        p.setNombre(p.getNombreUniExt());
        p.setTipo("UnivExtranjera");
        p.setPrivilegio(4);
        p.setContrasena(pswd);
        p.setConfirmar(confPswd);
        
         if (p.getNombreusuario().equals("")) {
            error.add("nombreusuario", new ActionMessage("error.nombreusuario.required"));
            saveErrors(request, error);
            huboError = true;
        }
         
        if (p.getEmail().equals("")) {
            error.add("email", new ActionMessage("error.email.required"));
            saveErrors(request, error);
            huboError = true;
        }
        
        if (p.getNombreUniExt().equals("")) {
            error.add("nombrecarrera", new ActionMessage("error.nombrecarrera.required"));
            saveErrors(request, error);
            huboError = true;
        }
        
        if (huboError) {
            return mapping.findForward(ERROR);
        }else if (DBMS.getInstance().agregarUsuario(p)) {
            if (DBMS.getInstance().agregarUniExt(p)) {

                Correo c = new Correo();
                String asunto = "Su usuario en el Sistema de Gestión de Intercambios ha sido creado";
                String mensaje = "Su usuario en el Sistema de Gestión de Intercambios ha sido creado."
                        + "\nSu usuario es: " + p.getNombreusuario()
                        + "\nSu clave de acceso temporal es: " + p.getContrasena();
                c.setAsunto(asunto);
                c.setMensaje(mensaje);
                boolean correoEnviado = c.enviarUsuario(p.getEmail());

                if (correoEnviado) {
                    return mapping.findForward(SUCCESS);
                } else {
                    return mapping.findForward(FAIL);
                }
            }
        }else{
            return mapping.findForward(FAIL);
        }
        
        return null;
        
    }
}