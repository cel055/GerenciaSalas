/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Agenda;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class AgendaDaoImpl extends BaseDaoImpl<Agenda, Long> implements AgendaDao, Serializable{

    @Override
    public Agenda pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Agenda) session.get(Agenda.class, id);
    }

    @Override
    public List<Agenda> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Agenda").list();
    }

    @Override
    public List<Agenda> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Agenda where assunto like :assunto");
        consulta.setParameter("assunto", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Agenda> procuraAgendaUsuario(Session session, Long idUsuario) throws HibernateException {
        Query consulta = session.createQuery("from Agenda a where a.usuario.id = :id");
        consulta.setParameter("id", idUsuario);        
        return consulta.list();
    }
    
}
