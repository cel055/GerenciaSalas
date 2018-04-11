/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
public interface UsuarioDao extends BaseDao<Usuario, Long>{

    public Usuario pesquisaPorLogin(String login, Session session) throws HibernateException;
    
    public List<String> pesquisaPorLogin(Session session) throws HibernateException;
    
}
