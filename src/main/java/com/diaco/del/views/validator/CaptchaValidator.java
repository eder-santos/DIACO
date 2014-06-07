package com.diaco.del.views.validator;

import com.diaco.del.util.CaptchaDiaco;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletRequest;

/**
 * User: esantos
 * Date: 5/25/14
 * Time: 2:48 PM
 */

public class CaptchaValidator implements Validator {

    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        String captcha = (String) o;

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Boolean isResponseCorrect = Boolean.FALSE;
        javax.servlet.http.HttpSession session = request.getSession();
        String parm = captcha;
        String c = (String) session.getAttribute(CaptchaDiaco.CAPTCHA_KEY);
        if (!parm.equals(c)) {
            FacesMessage msg = new FacesMessage();
            msg.setDetail("El captcha no coincide");
            msg.setSummary("El captcha no coincide");
            msg.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(msg);
        }

    }
}
