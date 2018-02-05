/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.DiaDaSemanaDao;
import br.com.container.dao.DiaDaSemanaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.dao.ReservaDao;
import br.com.container.dao.ReservaDaoImpl;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import br.com.container.modelo.Usuario;
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
public class EventoControle implements Serializable {

    private Reserva evento;
    private ScheduleModel eventoModel;
    private List<Reserva> eventos;
    private ReservaDao eventoDao;
    private UsuarioLogado usuarioLogado;
    private List<Sala> salas;
    private Sala sala;
    private SalaDao salaDao;

    private Session session;

    private String[] semanas;
    private List<String> diasSemana;
    private List<DiaDaSemana> diasDaSemana;

    @PostConstruct
    public void inicializar() {
        session = HibernateUtil.abreSessao();
        salaDao = new SalaDaoImpl();
        DiaDaSemanaDao diaDaSemanaDao = new DiaDaSemanaDaoImpl();
        diasDaSemana = diaDaSemanaDao.listaTodos(session);
        salas = salaDao.listaTodos(session);
        session.close();

        iniciarAgenda();
        carregarDiaSemanaCheckBox();
    }

    public void iniciarAgenda() {
        session = HibernateUtil.abreSessao();
        eventoDao = new ReservaDaoImpl();
        eventoModel = new DefaultScheduleModel();
        evento = new Reserva();
        evento.setDiasDaSemana(new ArrayList<>());
        try {
            if (sala == null) {
                eventos = eventoDao.pesquisarReservaPorSala("001", session);
            } else {
                eventos = eventoDao.pesquisarReservaPorSala(sala.getNome(), session);
                if (eventos.isEmpty()) {
                    Mensagem.mensagemError("Não trouxe nenhum dados para essa consulta!");
                }
            }
            //carregar os eventos do banco
//        for (Reserva eve : eventos) {
//            DefaultScheduleEvent evt = new DefaultScheduleEvent();
//            evt.setTitle(eve.getInformacao() + " - " + eve.getPeriodo());
//            evt.setData(eve.getId());
//            evt.setEndDate(eve.getFim());
//            evt.setStartDate(eve.getInicio());
//            evt.setDescription(eve.getInformacao());
//            evt.setAllDay(true);
//            evt.setEditable(true);
//            switch (eve.getPeriodo()) {
//
//                case "Vespertino":
//                    evt.setStyleClass("corAmarela");
//                    break;
//                case "Matutino":
//                    evt.setStyleClass("corVerde");
//                    break;
//            }
//
//            eventoModel.addEvent(evt);
//
//        }

            Calendar calendarAtual = new GregorianCalendar();
            if (eventos == null || eventos.isEmpty()) {
                return;
            }
            Date dataInicial = eventos.get(0).getInicio();
            Date dataAtual = dataInicial;
            Date dataFinal = eventos.get(eventos.size() - 1).getFim();
            do {
                calendarAtual.setTime(dataAtual);
                for (Reserva eve : eventos) {
                    if (eve.getFim().compareTo(dataFinal) > 0) {
                        dataFinal.setTime(eve.getFim().getTime());
                    }
                    if (eve.getInicio().compareTo(dataAtual) <= 0 && eve.getFim().compareTo(dataAtual) >= 0) {
                        for (DiaDaSemana diaDaSemana : eve.getDiasDaSemana()) {
                            if (diaDaSemana.getNumeroDoDia() == calendarAtual.get(Calendar.DAY_OF_WEEK)) {
                                DefaultScheduleEvent evt = new DefaultScheduleEvent();
                                evt.setTitle(eve.getInformacao() + " - " + eve.getPeriodo());
                                evt.setData(eve.getId());
                                evt.setEndDate(dataAtual);
                                evt.setStartDate(dataAtual);
                                evt.setDescription(eve.getInformacao());
                                evt.setAllDay(true);
                                evt.setEditable(true);
                                switch (eve.getPeriodo()) {

                                    case "Vespertino":
                                        evt.setStyleClass("corAmarela");
                                        break;
                                    case "Matutino":
                                        evt.setStyleClass("corVerde");
                                        break;
                                }

                                eventoModel.addEvent(evt);
                            }
                        }
                    }

                }
                calendarAtual.add(Calendar.DATE, 1);
                dataAtual = calendarAtual.getTime();
            } while (dataAtual.compareTo(dataFinal) <= 0);
            session.close();
        } catch (Exception e) {
            System.out.println("Erro no controle   " + e.getMessage());
        }
    }

    private void carregarDiaSemanaCheckBox() {
        diasSemana = new ArrayList<>();
        diasSemana.add("segunda");
        diasSemana.add("terça");
        diasSemana.add("quarta");
        diasSemana.add("quinta");
        diasSemana.add("sexta");
        diasSemana.add("sábado");
    }

    public void selecionado(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent = (ScheduleEvent) selectEvent.getObject();

        for (Reserva ev : eventos) {
            if (ev.getId() == (Long) scheduleEvent.getData()) {
                evento = ev;
                break;
            }
        }
    }

    public void novo(SelectEvent selectEvent) {
        ScheduleEvent scheduleEvent
                = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
        evento.setInicio(scheduleEvent.getStartDate());
        evento.setFim(scheduleEvent.getEndDate());
//        diaSemanaEvento(evento.getInicio());
    }

    public void movido(ScheduleEntryMoveEvent moveEvent) {
        for (Reserva ev : eventos) {
            if (ev.getId() == (Long) moveEvent.getScheduleEvent().getData()) {
                evento = ev;
                try {
                    session = HibernateUtil.abreSessao();
                    eventoDao.salvarOuAlterar(ev, session);
                    session.close();
                    inicializar();
                } catch (Exception e) {
                    System.out.println("erro ao mover " + e.getMessage());
                }
            }
        }
    }

    public void redirecionado(ScheduleEntryResizeEvent resizeEvent) {
        for (Reserva ev : eventos) {
            if (ev.getId() == (Long) resizeEvent.getScheduleEvent().getData()) {
                evento = ev;
                try {
                    session = HibernateUtil.abreSessao();
                    eventoDao.salvarOuAlterar(ev, session);
                    session.close();
                    inicializar();
                } catch (Exception e) {
                    System.out.println("erro ao redirecionar " + e.getMessage());
                }
            }
        }
    }

    public void salvar() {
        if (semanas.length == 0) {
            salvarEvento(0);
        } else {
            int diaSemanaEvento = diaSemanaEvento(evento.getInicio());
            int diferenca = 0;
            for (String semana : semanas) {
                switch (semana) {
                    case "segunda":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 2);
                        break;
                    case "terça":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 3);
                        break;
                    case "quarta":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 4);
                        break;
                    case "quinta":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 5);
                        break;
                    case "sexta":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 6);
                        break;
                    case "sábado":
                        diferenca = diferencaDiaSemana(diaSemanaEvento, 7);
                        break;
                }
                salvarEvento(diferenca);
            }
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Salvo com Sucesso", "Sucesso"));
        inicializar();
        evento = new Reserva();

    }

    private int diferencaDiaSemana(int diaSemanaEvento, int diaSemanaIntervalor) {
        int diferenca;
        if (diaSemanaEvento > diaSemanaIntervalor) {
            diferenca = 7 - diaSemanaEvento;
            diferenca += diaSemanaIntervalor;
        } else {
            diferenca = diaSemanaIntervalor - diaSemanaEvento;
        }

        return diferenca;
    }

    private int diaSemanaEvento(Date date) {
        Locale locale = new Locale("pt", "BR");
        SimpleDateFormat sdfEntrada = new SimpleDateFormat("yyyy-MM-dd", locale);
        sdfEntrada.setLenient(false);
        try {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            System.out.println("Erro ao pegar dia da semana " + e.getMessage());
            return -1;
        }
    }

    private void salvarEvento(int diaEvento) {
        long dt = (evento.getFim().getTime() - evento.getInicio().getTime()) + 3600000; // 1 hora para compensar horário de verão
        dt = dt / 86400000L;
        Reserva novo = eventoAuxiliar();
        session = HibernateUtil.abreSessao();
        acrescentaDiaDataEvento(novo, diaEvento);
        eventoDao.salvarOuAlterar(novo, session);
        session.clear();
        for (int i = 1; i <= dt; i++) {
            if (i % 7 == 0) {
                novo = eventoAuxiliar();
                acrescentaDiaDataEvento(novo, (i + diaEvento));
                eventoDao.salvarOuAlterar(novo, session);
                session.clear();
            }
        }

        session.close();

    }

    private Reserva eventoAuxiliar() {
        usuarioLogado = new UsuarioLogado();
        Usuario usuario = usuarioLogado.usuarioLogadoSpring(session);
        evento.setUsuario(usuario);
        evento.setSala(sala);
        if (semanas.length == 0) {
            return evento;
        } else {
            Reserva novo = new Reserva();
            novo.setInformacao(evento.getInformacao());
            novo.setPeriodo(evento.getInformacao());
            novo.setInicio(evento.getInicio());
            novo.setFim(evento.getFim());
            novo.setUsuario(usuario);
            novo.setSala(sala);
            return novo;
        }
    }

    private void acrescentaDiaDataEvento(Reserva evento, int dia) {
        Calendar c = Calendar.getInstance();
        c.setTime(evento.getInicio());
        c.add(Calendar.DATE, +dia);
        // Obtemos a data alterada
        evento.setInicio(c.getTime());
        if (semanas.length != 0) {
            evento.setFim(c.getTime());
        }
    }

    //Getters e Setters
    public Reserva getEvento() {
        if (evento == null) {
            evento = new Reserva();
        }
        return evento;
    }

    public void setEvento(Reserva evento) {
        this.evento = evento;
    }

    public ScheduleModel getEventoModel() {
        return eventoModel;
    }

    public void setEventoModel(ScheduleModel eventoModel) {
        this.eventoModel = eventoModel;
    }

    public List<Reserva> getEventos() {
        return eventos;
    }

    public void setEventos(List<Reserva> eventos) {
        this.eventos = eventos;
    }

    public String[] getSemanas() {
        return semanas;
    }

    public void setSemanas(String[] semanas) {
        this.semanas = semanas;
    }

    public List<String> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<String> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public List<Sala> getSalas() {
        return salas;
    }

    public void setSalas(List<Sala> salas) {
        this.salas = salas;
    }

    public Sala getSala() {
        if (sala == null) {
            sala = new Sala();
        }
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<DiaDaSemana> getDiasDaSemana() {
        return diasDaSemana;
    }

}
