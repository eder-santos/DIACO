package com.diaco.del.views.login;

import com.diaco.del.bo.Usuario;
import com.diaco.del.util.ServiceUtil;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * User: esantos
 * Date: 5/29/14
 * Time: 6:03 PM
 */

@Component
public class AuthProvider implements AuthenticationProvider{

    private static final Logger logger = Logger.getLogger(AuthProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String usuario = authentication.getName();
        String password = authentication.getCredentials().toString();
        password = ServiceUtil.getPasswordEncoder().encodePassword(password, "UMG");
        List<Usuario> lista = ServiceUtil.getDaoService().findByFields(Usuario.class, new String[]{"usuario", "password"}, new Object[] {usuario.toUpperCase(), password});

        if (lista == null || lista.size() <= 0) {
            logger.info("Usuario no existe");
            throw new BadCredentialsException("Usuario o password invalido");
        } else {
            Usuario user = lista.get(0);
            if ("A".equals(user.getEstado())) {
                //Crea autoridades segun el rol
                List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
                grantedAuths.add(new GrantedAuthorityImpl(user.getRol()));

                return new UsernamePasswordAuthenticationToken(
                        usuario,
                        password,
                        grantedAuths
                );
            } else {
                logger.info("Cuenta bloqueada");
                throw new BadCredentialsException("Usuario o password invalido");
            }
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}