/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Usuario;
import java.io.Serializable;
import org.hibernate.Session;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Senac PLC
 */
public class UsuarioLogado implements Serializable{

    private Usuario usuario;
    private UsuarioDao usuarioDao;
//    private Session AbreSessao;

    public Usuario usuarioLogadoSpring(Session session) {
        String login = null;
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext) {
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication) {
                login = ((User) authentication.getPrincipal()).getUsername();
            }
        }
        usuarioDao = new UsuarioDaoImpl();
        usuario = usuarioDao.pesquisaPorLogin(login, session);
        return usuario;
    }

}
