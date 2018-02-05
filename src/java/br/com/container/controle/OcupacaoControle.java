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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.primefaces.context.RequestContext;
import org.primefaces.event.timeline.TimelineSelectEvent;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author maodeobra
 */
@ManagedBean
@ViewScoped
public class OcupacaoControle implements Serializable {

    private TimelineModel timeline;
    private TimelineEvent timelineEvent;
    private Date inicioTimeline;
    private Date fimTimeline;
    private long zoomMax;
    private long zoomMin;

    private Session session;
    private ReservaDao reservaDao;

    private Reserva reserva;
    private Sala sala;

    private List<Sala> salasParaPesquisa;
    private List<Sala> salasSelecionadas;
    private List<DiaDaSemana> diasDaSemana;
    private List<String> periodos;

    private Date dataInicioPesquisa;
    private Date dataFimPesquisa;

    private String idsReservaParaPesq;
    private List<Reserva> reservasSelecionadas;

    @PostConstruct
    public void inicializar() {
        //Carrega obj's iniciaias do banco de dados
        session = HibernateUtil.abreSessao();
        DiaDaSemanaDao semanaDao = new DiaDaSemanaDaoImpl();
        diasDaSemana = semanaDao.listaTodos(session);
        SalaDao salaDao = new SalaDaoImpl();
        salasParaPesquisa = salaDao.listaTodos(session);
        reservaDao = new ReservaDaoImpl();
//        reservas = reservaDao.listaTodos(session);
        session.close();

        //inicia a timeline
        iniciaTimeline();
    }

    //métodos da pesquisa inicial
    public void iniciaTimeline() {
        timeline = new TimelineModel();
        //este é a data inicial e final vista na timeline na primeira vez, depois o usuario pode mudar
        Calendar calendario = new GregorianCalendar();
        calendario.add(Calendar.DAY_OF_YEAR, -5);
        inicioTimeline = calendario.getTime();
        calendario.add(Calendar.DAY_OF_YEAR, 30);
        fimTimeline = calendario.getTime();
        //atribui o zoom minimo para a timeline, 1 mes
        zoomMin = 1000l * 60 * 60 * 24 * 15;
        //atribui o zoom maximo para a timeline, 4 anos, cursos de faculdade
        zoomMax = 1000l * 60 * 60 * 24 * 30 * 12 * 4;
    }

    public void pesquisa() {
        if (salasSelecionadas.isEmpty()) {
            Mensagem.mensagemError("Selecione pelo menos uma sala");
            return;
        }
        iniciaTimeline();
        montaTimeLine();
    }

    private void montaTimeLine() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("removeEventoTimeline()");
        session = HibernateUtil.abreSessao();
        reservaDao = new ReservaDaoImpl();
        for (Sala salaSelecionada : salasSelecionadas) {
            montaLinhaTimeline(reservaDao.pesquisarReservaPorSala(salaSelecionada, session));
        }
        session.close();
        context.execute("addEventoTimeline()");
    }

    private void montaLinhaTimeline(List<Reserva> rs) {
        if (rs.isEmpty()) {
            return;
        }
        String nomeSala = rs.get(0).getSala().getNome();
        String classeCss = "";
        String idsReserva;

        Date dataInicial = rs.get(0).getInicio();
        Date dataAtual = dataInicial;
        Date dataFinal = rs.get(rs.size() - 1).getFim();
        Date fimDoDia = new Date();

        boolean manha = false;
        boolean tarde = false;
        boolean noite = false;

        Calendar calendarAtual = new GregorianCalendar();

        do {
            idsReserva = "";
            calendarAtual.setTime(dataAtual);
            for (Reserva r : rs) {
                if (r.getFim().compareTo(dataFinal) > 0) {
                    dataFinal.setTime(r.getFim().getTime());
                }
                if (r.getInicio().compareTo(dataAtual) <= 0 && r.getFim().compareTo(dataAtual) >= 0) {
                    for (DiaDaSemana diaDaSemana : r.getDiasDaSemana()) {
                        if (diaDaSemana.getNumeroDoDia() == calendarAtual.get(Calendar.DAY_OF_WEEK)) {
                            if (idsReserva.equals("")) {
                                idsReserva += ",";
                            }
                            idsReserva += r.getId().toString() + ",";
                            switch (r.getPeriodo()) {
                                case Reserva.MANHA:
                                    manha = true;
                                    break;
                                case Reserva.TARDE:
                                    tarde = true;
                                    break;
                                case Reserva.NOITE:
                                    noite = true;
                                    break;
                            }
                        }
                    }
                }
            }

            calendarAtual.add(Calendar.DATE, 1);
            fimDoDia.setTime(calendarAtual.getTimeInMillis() - 60000);
            if (manha || tarde || noite) {
                if (manha && tarde && noite) {
                    classeCss = "manhaTardeNoite";
                } else if (manha && tarde) {
                    classeCss = "manhaTarde";
                } else if (manha && noite) {
                    classeCss = "manhaNoite";
                } else if (tarde && noite) {
                    classeCss = "tardeNoite";
                } else if (manha) {
                    classeCss = "manha";
                } else if (tarde) {
                    classeCss = "tarde";
                } else {
                    classeCss = "noite";
                }
                timelineEvent = new TimelineEvent(null, dataAtual, calendarAtual.getTime(), false, nomeSala, "reserva " + classeCss + " " + idsReserva);
                timeline.add(timelineEvent);
            }
            manha = false;
            tarde = false;
            noite = false;
            dataAtual = calendarAtual.getTime();
        } while (dataAtual.compareTo(dataFinal) <= 0);
    }

    //métodos para o salvaento de uma reserva
    public void iniciaSalvamento() {
        reserva = new Reserva();
        salasParaPesquisa = new ArrayList<>();
        reserva.setDiasDaSemana(new ArrayList<>());
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        periodos = new ArrayList<>();
        reserva.setUsuario(new UsuarioLogado().usuarioLogadoSpring(session));
    }

    public String pesquisaSalaParaSalvar() {
        if (reserva.getInicio() == null || reserva.getFim() == null || reserva.getDiasDaSemana().isEmpty() || periodos.isEmpty()) {
            salasParaPesquisa = new ArrayList<>();
            return "ocupacao";
        }
        System.out.println("Pesq");
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        SalaDao salaDao = new SalaDaoImpl();
        if (periodos.size() == 1) {
            reserva.setPeriodo(periodos.get(0));
            salasParaPesquisa = salaDao.pesquisaSalaSemReserva(reserva, session);
        } else {
            List<Reserva> reservas = new ArrayList<>();
            Reserva r;
            for (String periodo : periodos) {
                r = new Reserva(reserva.getId(), reserva.getInformacao(), reserva.getInicio(), reserva.getFim(), reserva.getUsuario(), null, periodo, reserva.getDiasDaSemana());
                reservas.add(r);
            }
            salasParaPesquisa = salaDao.pesquisaSalaSemReserva(reservas, session);
        }
        session.close();
        return "ocupacao";
    }

    public String salvar() {
        if (reserva.getSala() == null) {
            Mensagem.mensagemError("Selecione uma sala");
            return "";
        }
        if (reserva.getInicio().compareTo(reserva.getFim()) > 0) {
            Mensagem.mensagemError("Data do final da reserva foi antes do início");
            return "";
        }
        reservaDao = new ReservaDaoImpl();
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        if (periodos.size() == 1) {
            reserva.setPeriodo(periodos.get(0));
            reservaDao.salvarOuAlterar(reserva, session);
            Mensagem.salvar(reserva.getInformacao());
        } else {
            Reserva r;
            for (String periodo : periodos) {
                r = new Reserva(null, reserva.getInformacao(), reserva.getInicio(), reserva.getFim(), reserva.getUsuario(), reserva.getSala(), periodo, reserva.getDiasDaSemana());
                reservaDao.salvarOuAlterar(r, session);
            }
        }
        session.close();
        iniciaSalvamento();
        pesquisa();
//        inicializar();
        return "ocupacao";
    }

    //Metodo de selecionar um elemento na timeline
    public void reservaSelecionada(TimelineSelectEvent selectEvent) {
        TimelineEvent eventoSelecionado = selectEvent.getTimelineEvent();
        System.out.println("Dados evento -> /n " + eventoSelecionado.getGroup()
                + "/n" + eventoSelecionado.getStyleClass() + "/n"
                + eventoSelecionado.getStartDate().toString() + "/n"
                + eventoSelecionado.getEndDate().toString()
        );
    }

    public void pesqReservaSelecionada() {
        List<Long> idsPesq = listaIdsReservas();
        reservasSelecionadas = new ArrayList<>();
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        reservaDao = new ReservaDaoImpl();
        for (Long idReser : idsPesq) {
            reservasSelecionadas.add(reservaDao.pesquisaEntidadeId(idReser, session));
        }
        session.close();
    }

    private List<Long> listaIdsReservas() {
        List<Long> idsPesq = new ArrayList<>();
        int inicio = idsReservaParaPesq.indexOf(",") + 1;
        int fim = idsReservaParaPesq.indexOf(",", inicio);
        String id;
        while (fim > 0) {
            id = idsReservaParaPesq.substring(inicio, fim);
            idsPesq.add(Long.parseLong(id));
            inicio = idsReservaParaPesq.indexOf(",", fim) + 1;
            fim = idsReservaParaPesq.indexOf(",", inicio);
        }
        id = idsReservaParaPesq.substring(inicio);
        idsPesq.add(Long.parseLong(id));
        return idsPesq;
    }

    public String stringfyDiasDaSemana(String idReserva) {
        String diasDaSemana = "";
        Long id = Long.parseLong(idReserva);

        DiaDaSemanaDao dao = new DiaDaSemanaDaoImpl();
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        List<DiaDaSemana> dias = dao.pesquisaPelaReserserva(id, session);
        session.close();

        for (int i = 0; i < dias.size(); i++) {
            switch (dias.get(i).getNumeroDoDia()) {
                case 1:
                    diasDaSemana += "DOM";
                    break;
                case 2:
                    diasDaSemana += "2ª";
                    break;
                case 3:
                    diasDaSemana += "3ª";
                    break;
                case 4:
                    diasDaSemana += "4ª";
                    break;
                case 5:
                    diasDaSemana += "5ª";
                    break;
                case 6:
                    diasDaSemana += "6ª";
                    break;
                case 7:
                    diasDaSemana += "SAB";
                    break;
            }
            if (i < dias.size() - 1) {
                diasDaSemana += ", ";
            }
        }
        return diasDaSemana;
    }

    public String deletarReserva(String idReserva) {
        Long id = Long.parseLong(idReserva);

        reservaDao = new ReservaDaoImpl();
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
        Reserva reservaExclusao = reservaDao.pesquisaEntidadeId(id, session);
        reservaDao.remover(reservaExclusao, session);
        session.close();
        pesquisa();
        return "ocupacao";
    }

    public String alterarReserva(String idReserva) {
        Long id = Long.parseLong(idReserva);

        if(session == null || !session.isOpen()){
            session = HibernateUtil.abreSessao();
        }
        reserva = reservaDao.pesquisaEntidadeId(id, session);

        periodos = new ArrayList<>();
        periodos.add(reserva.getPeriodo());
        pesquisaSalaParaSalvar();
        if(session.isOpen()){
            session.close();
        }
        return "ocupacao";
    }

    //Getters e Setters
    public TimelineModel getTimeline() {
        return timeline;
    }

    public Date getInicioTimeline() {
        return inicioTimeline;
    }

    public Date getFimTimeline() {
        return fimTimeline;
    }

    public long getZoomMax() {
        return zoomMax;
    }

    public long getZoomMin() {
        return zoomMin;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Sala> getSalasParaPesquisa() {
        return salasParaPesquisa;
    }

    public List<Sala> getSalasSelecionadas() {
        if (salasSelecionadas == null) {
            salasSelecionadas = new ArrayList<>();
        }
        return salasSelecionadas;
    }

    public void setSalasSelecionadas(List<Sala> salasSelecionadas) {
        this.salasSelecionadas = salasSelecionadas;
    }

    public List<DiaDaSemana> getDiasDaSemana() {
        return diasDaSemana;
    }

    public List<String> getPeriodos() {
        if (periodos == null) {
            periodos = new ArrayList<>();
        }
        return periodos;
    }

    public void setPeriodos(List<String> periodos) {
        this.periodos = periodos;
    }

    public Date getDataInicioPesquisa() {
        return dataInicioPesquisa;
    }

    public void setDataInicioPesquisa(Date dataInicioPesquisa) {
        this.dataInicioPesquisa = dataInicioPesquisa;
    }

    public Date getDataFimPesquisa() {
        return dataFimPesquisa;
    }

    public void setDataFimPesquisa(Date dataFimPesquisa) {
        this.dataFimPesquisa = dataFimPesquisa;
    }

    public String getIdsReservaParaPesq() {
        return idsReservaParaPesq;
    }

    public void setIdsReservaParaPesq(String idsReservaParaPesq) {
        this.idsReservaParaPesq = idsReservaParaPesq;
    }

    public List<Reserva> getReservasSelecionadas() {
        if (reservasSelecionadas == null) {
            reservasSelecionadas = new ArrayList<>();
        }
        return reservasSelecionadas;
    }

    public void setReservasSelecionadas(List<Reserva> reservasSelecionadas) {
        this.reservasSelecionadas = reservasSelecionadas;
    }

}
