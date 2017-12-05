/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Perfil;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
public class PerfilDaoImpl implements PerfilDao{

    @Override
    public List<Perfil> todosPerfis(Session session) throws HibernateException {
        return session.createQuery("from Perfil p").list();
    }
    
}
