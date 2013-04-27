/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import Clases.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import DBMS.*;
import java.util.Random;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;

/**
 *
 * @author caponte
 */
public class PreRegistroUsuario extends org.apache.struts.action.Action {

    /* Patrones a Validar */
    private static final String patronEmail = "^([_A-Za-z0-9-\\+])+@([A-Za-z0-9-])+\\.([A-Za-z0-9-])+$";
    private static final String SUCCESS = "success";
    private static final String FAIL = "failure";
    private static final String ERROR = "error";
    private Pattern patron;
    private Matcher match;

    public PreRegistroUsuario() {
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

        Usuario u = (Usuario) form;
        ActionErrors error = new ActionErrors();
        HttpSession session = request.getSession();
        boolean huboError = false;
        String pswd = u.generarContrasena();
        String mail = u.getEmail();
        u.setContrasena(pswd);
        u.setConfirmar(pswd);

        Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
        request.setCharacterEncoding("UTF-8");
        String answer = request.getParameter("answer");
        if (!captcha.isCorrect(answer)) {
            huboError = true;
        }


        //Verifica que el username no  sea vacio.
        if (u.getNombreusuario().equals("")) {
            error.add("nombreusuario", new ActionMessage("error.nombreusuario.required"));
            saveErrors(request, error);
            huboError = true;
        }

        //Verifica que el nombre propio del usuario no sea vacio/.
        if (u.getNombre().equals("")) {
            error.add("nombre", new ActionMessage("error.nombre.required"));
            saveErrors(request, error);
            huboError = true;
        }

        //Verifica que el email no sea vacio y que este estructurado correctamente.
        if (u.getEmail().equals("")) {
            error.add("email", new ActionMessage("error.email.required"));
            saveErrors(request, error);
            huboError = true;
        } else if (validate(mail) == false) {
            error.add("email", new ActionMessage("error.email.malformulado"));
            saveErrors(request, error);
            huboError = true;
        }

        // Si hubo error lo notifica, si no, procede a agregar en la BD.
        if (huboError) {
            return mapping.findForward(ERROR);

        } else if (DBMS.getInstance().preRegistroUsuario(u)) {
            
            boolean boo = DBMS.getInstance().registrar(u.getNombreusuario(), "Se ha pregistrado en el sistema");
            return mapping.findForward(SUCCESS);

        } else {
            return mapping.findForward(FAIL);
        }

    }
}
