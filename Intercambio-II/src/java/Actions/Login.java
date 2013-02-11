/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;

import Clases.Usuario;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import DBMS.*;
import nl.captcha.Captcha;

/**
 *
 * @author caponte
 */
public class Login extends org.apache.struts.action.Action {

    /* forward name="success" path="" */
    private static final String ADMIN = "administrador";
    private static final String GESTOR = "gestor";
    private static final String COORD = "coordinacion";
    private static final String univExt = "univExt";
    private static final String estUSB = "estUSB";
    private static final String estExt = "estExt";
    private static final String FAIL = "failure";
    private static final String ERROR = "error";

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
        HttpSession session = request.getSession(true);

        ActionErrors error = new ActionErrors();
        boolean huboError = false;

        Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);
        request.setCharacterEncoding("UTF-8");
        String answer = request.getParameter("answer");
        if (!captcha.isCorrect(answer)) {
            huboError = true;
        }

        if (u.getNombreusuario().equals("")) {
            error.add("nombreusuario", new ActionMessage("error.nombreusuario.required"));
            saveErrors(request, error);
            huboError = true;
        }

        if (u.getContrasena().equals("")) {
            error.add("contrasena", new ActionMessage("error.contrasena.required"));
            saveErrors(request, error);
            huboError = true;
        }

        if (huboError) {
            return mapping.findForward(ERROR);
        } else {

            Usuario tmp = DBMS.getInstance().consultarUsuario(u);

            if (tmp.getNombreusuario() != null) {

                if (tmp.getPrivilegio() == 1) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(ADMIN);
                } else if (tmp.getPrivilegio() == 2) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(GESTOR);
                } else if (tmp.getPrivilegio() == 3) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(COORD);
                } else if (tmp.getPrivilegio() == 4) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(univExt);
                } else if (tmp.getPrivilegio() == 5) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(estUSB);
                } else if (tmp.getPrivilegio() == 6) {
                    request.setAttribute("usuario", tmp);
                    session.setAttribute("nombre", tmp.getNombre());
                    session.setAttribute("nombreusuario", tmp.getNombreusuario());
                    return mapping.findForward(estExt);
                } else {
                    return mapping.findForward(FAIL);
                }
            } else {
                error.add("contrasena", new ActionMessage("error.contrasena.missmatch"));
                return mapping.findForward(ERROR);
            }
        }
    }
}