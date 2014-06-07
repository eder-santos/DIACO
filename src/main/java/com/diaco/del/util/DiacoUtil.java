package com.diaco.del.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 5:37 PM
 */

public class DiacoUtil {

    public static void crearMensajeError(String txt, String componente, FacesMessage.Severity severity) {
        FacesMessage msg = new FacesMessage();
        msg.setDetail(txt);
        msg.setSummary(txt);
        msg.setSeverity(severity);
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(componente, msg);
    }

    public static Object getBackingBean(String beanName) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ELContext elContext = facesContext.getELContext();
        ExpressionFactory expressionFactory = facesContext.getApplication().getExpressionFactory();
        ValueExpression valueExpression =
                expressionFactory.createValueExpression(elContext, convertToElSyntax(beanName), Object.class);
        return valueExpression.getValue(elContext);
    }

    private static String convertToElSyntax(String value) {
        return new StringBuffer("#{").append(value).append("}").toString();
    }
}
