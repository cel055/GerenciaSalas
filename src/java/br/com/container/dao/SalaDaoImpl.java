/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Sala;
import br.com.container.modelo.Usuario;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class SalaDaoImpl extends BaseDaoImpl<Sala, Long> implements SalaDao{

    @Override
    public Sala pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Sala) session.get(Usuario.class, id);
    }

    @Override
    public List<Sala> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Sala").list();
    }

    @Override
    public List<Sala> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Sala s where s.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
