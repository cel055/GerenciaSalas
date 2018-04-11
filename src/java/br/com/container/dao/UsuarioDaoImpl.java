/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
public class UsuarioDaoImpl extends BaseDaoImpl<Usuario, Long> implements UsuarioDao, Serializable{

    @Override
    public Usuario pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Usuario) session.get(Usuario.class, id);
    }

    @Override
    public List<Usuario> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Usuario").list();
    }

    @Override
    public List<Usuario> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Usuario u where u.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public Usuario pesquisaPorLogin(String login, Session session) throws HibernateException{
        Query consulta = session.createQuery("from Usuario u where u.login = :login");
        consulta.setParameter("login", login);
        return (Usuario) consulta.uniqueResult();
    }

    @Override
    public List<String> pesquisaPorLogin(Session session) throws HibernateException {
        Query consulta = session.createQuery("Select u.login from Usuario u");
        return consulta.list();
    }    
}
