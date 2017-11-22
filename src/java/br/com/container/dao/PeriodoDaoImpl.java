/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Periodo;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
public class PeriodoDaoImpl extends BaseDaoImpl<Periodo, Long> implements PeriodoDao, Serializable{

    @Override
    public Periodo pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Periodo) session.get(Periodo.class, id);
    }

    @Override
    public List<Periodo> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Periodo").list();
    }

    @Override
    public List<Periodo> pesquisaPorNome(String nome, Session session) throws HibernateException {
        throw new UnsupportedOperationException("Método pesquisaPorNome não implementado para o periodo");
    }
    
}
