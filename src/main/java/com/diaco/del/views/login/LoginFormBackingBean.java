package com.diaco.del.views.login;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:03 PM
 */

@ManagedBean (name="loginFormBean")
@RequestScoped
public class LoginFormBackingBean {

    private String userName;
    private String password;
    private String redirectUrl;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
