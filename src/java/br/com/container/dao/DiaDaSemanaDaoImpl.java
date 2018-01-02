/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.DiaDaSemana;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
public class DiaDaSemanaDaoImpl extends BaseDaoImpl<DiaDaSemana, Long> implements DiaDaSemanaDao{

    @Override
    public DiaDaSemana pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (DiaDaSemana) session.get(DiaDaSemana.class, id);
    }

    @Override
    public List<DiaDaSemana> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from DiaDaSemana").list();
    }

    @Override
    public List<DiaDaSemana> pesquisaPorNome(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Método pesquisaPorNome não implementado para o DiaDaSemana");
    }

    @Override
    public List<DiaDaSemana> pesquisaPelaReserserva(Long reserva, Session session) throws HibernateException {
        Query consulta = session.createQuery("select r.diasDaSemana from Reserva r where r.id = :id");
        consulta.setParameter("id", reserva);
        return consulta.list();
    }
    
}
