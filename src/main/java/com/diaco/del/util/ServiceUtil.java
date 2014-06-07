package com.diaco.del.util;

import com.diaco.del.dao.DaoService;
import org.springframework.security.authentication.encoding.PasswordEncoder;

/**
 * User: esantos
 * Date: 5/24/14
 * Time: 11:18 AM
 */

public class ServiceUtil {

    private static DaoService daoService;
    private static PasswordEncoder passwordEncoder;

    public static DaoService getDaoService() {
        return daoService;
    }

    public void setDaoService(DaoService daoService) {
        ServiceUtil.daoService = daoService;
    }

    public static PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        ServiceUtil.passwordEncoder = passwordEncoder;
    }
}
