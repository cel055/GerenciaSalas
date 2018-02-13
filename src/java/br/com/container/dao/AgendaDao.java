/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Agenda;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public interface AgendaDao extends BaseDao<Agenda, Long>{
    
    List<Agenda> procuraAgendaUsuario(Session session, Long idUsuario) throws HibernateException;
    List<Agenda> procuraAgendaDoDia(Session session, Date dia) throws HibernateException;
}
