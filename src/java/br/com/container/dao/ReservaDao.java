/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public interface ReservaDao extends BaseDao<Reserva, Long>{
    
    List<Reserva> pesquisarReservaPorSala(String sala, Session session) throws HibernateException;
    List<Reserva> pesquisarReservaPorSala(Sala sala, Session session) throws HibernateException;
    List<Reserva> pesquisarReservaPorSala(Sala sala, Date inicio, Date fim, Session session) throws HibernateException;
    List<Reserva> pesquisarReservaPorSala(List<Sala> salas, Session session) throws HibernateException;
    List<Reserva> pesquisarReservaPorSala(List<Sala> sala, Date inicio, Date fim, Session session) throws HibernateException;
}
