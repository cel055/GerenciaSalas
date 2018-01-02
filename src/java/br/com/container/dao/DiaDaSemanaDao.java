/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.DiaDaSemana;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
public interface DiaDaSemanaDao extends BaseDao<DiaDaSemana, Long>{
    List<DiaDaSemana> pesquisaPelaReserserva(Long reserva, Session session) throws HibernateException;
}
