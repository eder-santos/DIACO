package com.diaco.del.views.login;

import com.diaco.del.util.DiacoUtil;
import com.diaco.del.util.ServiceUtil;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.IOException;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:03 PM
 */

@ManagedBean(name = "login")
@RequestScoped
public class LoginBean {

    private static final Logger logger = Logger.getLogger(LoginBean.class);

    public String loginUsingSpringAuthenticationManager() {
        LoginFormBackingBean loginFormBean =
                (LoginFormBackingBean) DiacoUtil.getBackingBean("loginFormBean");

        //authentication manager esta en Spring config: /WEB-INF/security.xml
        AuthenticationManager authenticationManager =
                (AuthenticationManager) getSpringBean("authenticationManager");

        //Token
        Authentication authenticationRequestToken = createAuthenticationToken(loginFormBean);

        try {
            //Autenticando al usuario
            Authentication authenticationResponseToken =
                    authenticationManager.authenticate(authenticationRequestToken);

            SecurityContextHolder.getContext().setAuthentication(authenticationResponseToken);
            if (authenticationResponseToken.isAuthenticated()) {
                boolean isAdmin = false;
                for (GrantedAuthority auth: authenticationResponseToken.getAuthorities()) {
                    if ("ROLE_ADMIN".equals(auth.getAuthority()))
                        isAdmin = true;
                }

                String url;
                if (isAdmin)
                    url = "/xhtml/administracion/sucursal.jsf";
                else
                    url = "/xhtml/estadisticas/consulta.jsf";
                FacesContext fc = FacesContext.getCurrentInstance();
                ExternalContext ec = fc.getExternalContext();
                try {
                    ec.redirect(url);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (BadCredentialsException badCredentialsException) {
            DiacoUtil.crearMensajeError("Usuario o password invalido", "form", FacesMessage.SEVERITY_ERROR);
        } catch (LockedException lockedException) {
            DiacoUtil.crearMensajeError("Cuenta bloqueada", "form", FacesMessage.SEVERITY_ERROR);
        } catch (DisabledException disabledException) {
            DiacoUtil.crearMensajeError("Cuenta deshabilitada", "form", FacesMessage.SEVERITY_ERROR);
        }
        return null;
    }

    private Authentication createAuthenticationToken(LoginFormBackingBean loginFormBean) {
        return new UsernamePasswordAuthenticationToken(
                loginFormBean.getUserName(),
                loginFormBean.getPassword()
        );
    }


    private Object getSpringBean(String name){
        WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(
                (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext());
        return ctx.getBean(name);
    }
}