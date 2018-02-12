/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.AgendaDao;
import br.com.container.dao.AgendaDaoImpl;
import br.com.container.dao.DiaDaSemanaDao;
import br.com.container.dao.DiaDaSemanaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.dao.ReservaDao;
import br.com.container.dao.ReservaDaoImpl;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.modelo.Agenda;
import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import br.com.container.modelo.Usuario;
import br.com.container.util.EnviaEmail;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Silvio
 */
@ManagedBean
@ViewScoped
public class AgendaControle implements Serializable {

    private Agenda agenda;
    private ScheduleModel eventoModel;
    private List<Agenda> eventos;
    private AgendaDao agendaDao;
    private UsuarioLogado usuarioLogado;
    private Usuario usuario;
    private Session session;

    @PostConstruct
    public void inicializar() {
        session = HibernateUtil.abreSessao();
        eventoModel = new DefaultScheduleModel();
        agendaDao = new AgendaDaoImpl();
        try {
            usuarioLogado = new UsuarioLogado();
            usuario = usuarioLogado.usuarioLogadoSpring(session);
            eventos = agendaDao.procuraAgendaUsuario(session, usuario.getId());
            session.close();
        } catch (HibernateException he) {
            System.out.println("Erro ao inicializar agenda no controle " + he.getMessage());
        }

        for (Agenda ag : eventos) {
            DefaultScheduleEvent evt = new DefaultScheduleEvent();
            evt.setData(ag.getId());
            evt.setStartDate(ag.getDia_evento());
            evt.setEndDate(ag.getDia_evento());
            evt.setDescription(ag.getDescricao());
            evt.setTitle(ag.getAssunto());
            evt.setAllDay(true);
//            evt.setEditable(true);

            eventoModel.addEvent(evt);

        }

    }

    public void selecionado(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent = (ScheduleEvent) selectEvent.getObject();
        for (Agenda ev : eventos) {
            if (ev.getId() == (Long) scheduleEvent.getData()) {
                agenda = ev;
                break;
            }
        }
    }

    public void novo(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent
                = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        agenda.setDia_evento(scheduleEvent.getStartDate());
    }

//    public void movido(ScheduleEntryMoveEvent moveEvent) {
//        for (Agenda ev : eventos) {
//            if (ev.getId() == (Long) moveEvent.getScheduleEvent().getData()) {
//                agenda = ev;
//                try {
//                    session = HibernateUtil.abreSessao();
//                    agendaDao.salvarOuAlterar(ev, session);
//                    session.close();
//                    inicializar();
//                } catch (Exception e) {
//                    System.out.println("erro ao mover " + e.getMessage());
//                }
//            }
//        }
//    }

//    public void redirecionado(ScheduleEntryResizeEvent resizeEvent) {
//        for (Agenda ev : eventos) {
//            if (ev.getId() == (Long) resizeEvent.getScheduleEvent().getData()) {
//                agenda = ev;
//                try {
//                    session = HibernateUtil.abreSessao();
//                    agendaDao.salvarOuAlterar(ev, session);
//                    session.close();
//                    inicializar();
//                } catch (Exception e) {
//                    System.out.println("erro ao redirecionar " + e.getMessage());
//                }
//            }
//        }
//    }

    public void salvar() {
        session = HibernateUtil.abreSessao();
        try {
            agenda.setUsuario(usuario);
            agendaDao.salvarOuAlterar(agenda, session);
            EnviaEmail.enviaConfirmacaoEmail(agenda);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com Sucesso", ""));
            inicializar();
            agenda = new Agenda();
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro ao salvar evento", ""));
            System.out.println("Erro ao salvar evento " + e.getMessage());
        }

    }


    //Getters e Setters
    public Agenda getAgenda() {
        if (agenda == null) {
            agenda = new Agenda();
        }
        return agenda;
    }

    public void setAgenda(Agenda evento) {
        this.agenda = evento;
    }

    public ScheduleModel getEventoModel() {
        return eventoModel;
    }

    public void setEventoModel(ScheduleModel eventoModel) {
        this.eventoModel = eventoModel;
    }

    public List<Agenda> getAgendas() {
        return eventos;
    }

    public void setAgendas(List<Agenda> eventos) {
        this.eventos = eventos;
    }

}
