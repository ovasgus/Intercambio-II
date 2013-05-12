<%-- 
    Document   : sbGestUsuarios
    Created on : Nov 15, 2012, 12:16:02 AM
    Author     : kosmos
    Modified by: dreabalbas
--%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<script type="text/javascript" src="/Intercambio-II/css/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="/Intercambio-II/plantilla/sidebarsL/ddmenu.js"></script>
<script type="text/javascript">


ddmenu.init({
	headerclass: "submenuheader", 
	contentclass: "submenu", 
	collapseprev: true,
	defaultexpanded: [], 
	animatedefault: false, 
	persiststate: true, 
	toggleclass: ["", ""], 
	togglehtml: ["suffix", "<img src='/Intercambio-II/images/plus.png' class='statusicon' />", "<img src='/Intercambio-II/images/minus.png' class='statusicon' />"],
	animatespeed: "normal" 
})


</script>

<html:html>

    <script language="javascript">
        function show(target){
            document.getElementById(target).style.display = 'block';
        }

        function hide(target){
            document.getElementById(target).style.display = 'none';
        }
    </script>
    
    <div class="glossymenu">
        <a style="border-bottom: none;" ><html:link styleClass="menuitem" action="/accionesAdmin">Inicio</html:link></a>
   
        <a class="menuitem submenuheader">Gestión de usuarios   </a>
        <div class="submenu">
            <ul>
                <li><a> <html:link action="/agregUsuario">Agregar usuario</html:link></a></li>
                <li><a> <html:link action="/ListarUsuarios">Consultar usuarios</html:link></a></li>
            </ul>
        </div>
        
        <a style="border-bottom: none;"> <html:link styleClass="menuitem" action="/modPerfilAdm">Modificar perfil</html:link></a>
	<a class="menuitem submenuheader">Gestión de anuncios</a>
        <div class ="submenu">
            <ul>
                <li><a> <html:link action="/BuscarDestinatarios">Redactar anuncio</html:link></a></li>
                <li><a> <html:link action="/Anuncios">Anuncios enviados</html:link></a></li>
            </ul>
        </div>
        
        <a style="border-bottom: none"> <html:link styleClass="menuitem" action="/LogAuditoria">Log de Auditoría</html:link></a>
        <a style="border-bottom: solid"> <html:link styleClass="menuitem" action="/CerrarSesion">Cerrar sesión</html:link></a>
            
   </div>
    
</html:html>