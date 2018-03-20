/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Agenda;
import br.com.container.util.Estaticos;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class AgendaDaoImpl extends BaseDaoImpl<Agenda, Long> implements AgendaDao, Serializable {

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
        List<Agenda> todosUsuarioPorId = consulta.list();

        if (!todosUsuarioPorId.isEmpty()) {
            consulta = session.createQuery("from Agenda a where a.convidado like :login");
            consulta.setParameter("login", "%" + todosUsuarioPorId.get(0).getUsuario().getLogin() + "%");
            List<Agenda> todosComConvidados = consulta.list();
            todosUsuarioPorId.addAll(todosComConvidados);
        }

        return todosUsuarioPorId;
    }

    @Override
    public List<Agenda> procuraAgendaDoDia(Session session, Date dia) throws HibernateException {
        Query consulta = session.createQuery("from Agenda a where DATE(a.dia_evento) = :dia or DATE(a.email2) = :dia or DATE(a.email3) = :dia");
        consulta.setParameter("dia", dia);
        return consulta.list();
    }


    public static void main(String[] args) {
        AgendaDao dao = new AgendaDaoImpl();
        Calendar data = new GregorianCalendar(Estaticos.horaProj);
        data.set(2018, 01, 22);
        Session session = null;
        try {
            session = HibernateUtil.abreSessao();
            List<Agenda> agendas = dao.procuraAgendaDoDia(session, data.getTime());
            for (Agenda agenda : agendas) {
                System.out.println("Id agenda = " + agenda.getId());
            }
        } catch (HibernateException e) {
            System.out.println("Erro hibernate: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro generico: " + e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

}
