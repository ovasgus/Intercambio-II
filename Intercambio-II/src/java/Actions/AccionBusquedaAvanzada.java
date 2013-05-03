/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Actions;


import Clases.Busqueda;
import Clases.Usuario;
import DBMS.DBMS;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


/**
 *
 * @author brahyam
 */
public class AccionBusquedaAvanzada extends org.apache.struts.action.Action {
    
    
    /* forward name="success" path="" */
    private static final String SUCCESS = "success";
    private static final String FAIL = "failure";
    
    
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
        HttpSession session = request.getSession();
        Busqueda busq = (Busqueda) form;
        if(busq.getCarrera().equalsIgnoreCase("n/a"))
        {
            busq.setCarrera("");
        }
        if(busq.getOpcion1().equalsIgnoreCase("n/a"))
        {
            busq.setOpcion1("");
        }
        if(busq.getPais().equalsIgnoreCase("n/a"))
        {
            busq.setPais("");
        }
        ArrayList<Usuario> users = DBMS.getInstance().listarBusquedaAvanzada(busq);
        request.setAttribute("usuarios", users);
        if(busq.getConfirmar().compareTo("busqueda") == 0){
        return mapping.findForward(SUCCESS);
        }else{
            return mapping.findForward("busqueda");
        }

    }
    
}
