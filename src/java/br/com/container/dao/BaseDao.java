/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author silvio
 * @param <T>
 * @param <ID>
 */
public interface BaseDao<T, ID> {
    
    void salvarOuAlterar(T entidade, Session session) throws HibernateException;

    void remover(T entidade, Session session) throws HibernateException;
    
    T pesquisaEntidadeId(Long id, Session session) throws HibernateException;

    List<T> listaTodos(Session session) throws HibernateException;

    List<T> pesquisaPorNome(String nome, Session session) throws HibernateException;
    
}
