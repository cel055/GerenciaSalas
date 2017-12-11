/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Reserva;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class ReservaDaoImpl extends BaseDaoImpl<Reserva, Long> implements ReservaDao {

    @Override
    public Reserva pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Reserva) session.get(Reserva.class, id);
    }

    @Override
    public List<Reserva> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Reserva").list();
    }

    @Override
    public List<Reserva> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva s where s.nome like :informacao");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Reserva> pesquisarReservaPorSala(String sala, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Reserva r where r.sala.nome = :sala");
        consulta.setParameter("sala", sala);
        return consulta.list();
    }
    
    public static void main(String[] args) {
        Session session = HibernateUtil.abreSessao();
        ReservaDaoImpl daoImpl = new ReservaDaoImpl();
        List<Reserva> todas = daoImpl.pesquisarReservaPorSala("001", session);
        System.out.println(todas);
        session.close();
    }

}
