/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import br.com.container.modelo.SalaLimpeza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class SalaDaoImpl extends BaseDaoImpl<Sala, Long> implements SalaDao, Serializable {

    @Override
    public Sala pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Sala) session.get(Sala.class, id);
    }

    @Override
    public List<Sala> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Sala s ORDER BY s.nome").list();
    }

    @Override
    public List<Sala> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Sala s where s.nome like :nome ORDER BY s.nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }

    @Override
    public List<Sala> pesquisaSalaComReserva(Reserva reserva, Session session) throws HibernateException {
        if (reserva.getId() != null) {
            return pesquisaSalaComReserva(reserva.getId(), reserva.getInicio(), reserva.getFim(), reserva.getDiasDaSemana(), reserva.getPeriodo(), session);
        }
        return pesquisaSalaComReserva(reserva.getInicio(), reserva.getFim(), reserva.getDiasDaSemana(), reserva.getPeriodo(), session);
    }

    @Override
    public List<Sala> pesquisaSalaComReserva(Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException {
        List<Long> idDias = new ArrayList<>();
        for (DiaDaSemana dia : dias) {
            idDias.add(dia.getId());
        }
        Query consulta = session.createQuery(
                "select r.sala.id from Reserva r join r.diasDaSemana d "
                + "where r.periodo like :periodo "
                + "and r.inicio <= :dataFinal "
                + "and r.fim >= :dataInicio "
                + "and d.id in (:idDias) "
                + "group by r.sala.id "
                + "ORDER BY r.sala.nome"
        );
        consulta.setParameter("dataFinal", fim);
        consulta.setParameter("dataInicio", inicio);
        consulta.setParameter("periodo", periodo);
        consulta.setParameterList("idDias", idDias);
        return consulta.list();
    }

    @Override
    public List<Sala> pesquisaSalaComReserva(Long id, Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException {
        List<Long> idDias = new ArrayList<>();
        for (DiaDaSemana dia : dias) {
            idDias.add(dia.getId());
        }
        Query consulta = session.createQuery(
                "select r.sala.id from Reserva r join r.diasDaSemana d "
                + "where r.periodo like :periodo "
                + "and r.inicio <= :dataFinal "
                + "and r.fim >= :dataInicio "
                + "and d.id in (:idDias) "
                + "and r.id != :idReserva "
                + "group by r.sala.id "
                + "ORDER BY r.sala.nome"
        );
        consulta.setParameter("dataFinal", fim);
        consulta.setParameter("dataInicio", inicio);
        consulta.setParameter("periodo", periodo);
        consulta.setParameter("idReserva", id);
        consulta.setParameterList("idDias", idDias);
        return consulta.list();
    }

    @Override
    public List<Sala> pesquisaSalaSemReserva(Reserva reserva, Session session) throws HibernateException {
        return pesquisaSalaSemReserva(reserva.getId(), reserva.getInicio(), reserva.getFim(), reserva.getDiasDaSemana(), reserva.getPeriodo(), session);
    }

    @Override
    public List<Sala> pesquisaSalaSemReserva(Long id, Date inicio, Date fim, List<DiaDaSemana> dias, String periodo, Session session) throws HibernateException {
        List idsSalasOcupadas;
        if (id == null) {
            idsSalasOcupadas = pesquisaSalaComReserva(inicio, fim, dias, periodo, session);
        } else {
            idsSalasOcupadas = pesquisaSalaComReserva(id, inicio, fim, dias, periodo, session);
        }
        if (idsSalasOcupadas.isEmpty()) {
            return listaTodos(session);
        }
        Query consulta = session.createQuery("from Sala s where s.id not in (:ids) ORDER BY s.nome");
        consulta.setParameterList("ids", idsSalasOcupadas);
        return consulta.list();
    }

    @Override
    public List<Sala> pesquisaSalaSemReserva(List<Reserva> reservas, Session session) throws HibernateException {
        List idsSalasOcupadas = new ArrayList();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == null) {
                idsSalasOcupadas.addAll(pesquisaSalaComReserva(reserva.getInicio(), reserva.getFim(), reserva.getDiasDaSemana(), reserva.getPeriodo(), session));
            } else {
                idsSalasOcupadas.addAll(pesquisaSalaComReserva(reserva.getId(), reserva.getInicio(), reserva.getFim(), reserva.getDiasDaSemana(), reserva.getPeriodo(), session));
            }
        }
        if (idsSalasOcupadas.isEmpty()) {
            return listaTodos(session);
        }
        Query consulta = session.createQuery("from Sala s where s.id not in (:ids) ORDER BY s.nome");
        consulta.setParameterList("ids", idsSalasOcupadas);
        return consulta.list();
    }

    @Override
    public Long totalSala(Session session) throws HibernateException {
        Query consulta = session.createQuery("Select count(id) from Sala");
        return (Long) consulta.uniqueResult();
    }

    @Override
    public List<SalaLimpeza> todasSalaLimpezaDoDia(Session session) throws HibernateException {
        Query consulta = session.createSQLQuery("SELECT nomeDoDia, nome as sala, informacao, observacao,"
                + " periodo FROM reserva_dias_da_semana rs "
                + " join reserva r on rs.reserva_id = r.id"
                + " join dia_da_semana ds on rs.dia_da_semana_id = ds.id"
                + " join sala s on r.idSala = s.id"
                + " where (curdate()  between inicio and fim) and ds.numeroDoDia = DAYOFWEEK(now()) order by nome");
        List objeto = consulta.list();
        return carregaSalaLimpeza(objeto);
    }

    @Override
    public List<SalaLimpeza> todasSalaLimpezaDiaAnterior(Session session) throws HibernateException {
        Query consulta = session.createSQLQuery("SELECT nomeDoDia, nome as sala, informacao, observacao,"
                + " periodo FROM reserva_dias_da_semana rs "
                + " join reserva r on rs.reserva_id = r.id"
                + " join dia_da_semana ds on rs.dia_da_semana_id = ds.id"
                + " join sala s on r.idSala = s.id"
                + " where (DATE_SUB(CONCAT(CURDATE(), ' 00:00:00'), INTERVAL 1 DAY)  between inicio and fim)\n"
                + " and ds.numeroDoDia = DAYOFWEEK(now()) -1 order by nome");
        List objeto = consulta.list();
        return carregaSalaLimpeza(objeto);
    }

    private List<SalaLimpeza> carregaSalaLimpeza(List objeto) {
        Object[] item;
        List<SalaLimpeza> salas = new ArrayList<>();

        for (Object objeto1 : objeto) {
            SalaLimpeza sala = new SalaLimpeza();
            item = (Object[]) objeto1;
            sala.setDia(item[0].toString());
            sala.setSala(item[1].toString());
            sala.setInformação(item[2].toString());
            sala.setObservacao(item[3].toString());
            sala.setPeriodo(item[4].toString());
            salas.add(sala);
        }
        return salas;
    }

}
