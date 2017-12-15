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
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.hibernate.Session;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author maodeobra
 */
@ManagedBean
@ViewScoped
public class OcupacaoControle implements Serializable{

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
    private UsuarioLogado usuarioLogado;
    
    private List<Reserva> reservas;
    private List<Sala> salasParaPesquisa;
    private List<Sala> salasSelecionadas;
    private List<DiaDaSemana> diasDaSemana;
    
    private Date dataInicioPesquisa;
    private Date dataFimPesquisa;
    
    @PostConstruct
    public void inicializar(){
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
    
    public void iniciaTimeline(){
        timeline = new TimelineModel();
        //este é a data inicial e final vista na timeline na primeira vez, depois o usuario pode mudar
        Calendar calendario = new GregorianCalendar();
        calendario.add(Calendar.DAY_OF_YEAR, -5); 
        inicioTimeline = calendario.getTime();
        calendario.add(Calendar.DAY_OF_YEAR, 30);
        fimTimeline = calendario.getTime();
        //atribui o zoom minimo para a timeline, 1 mes
        zoomMin = 1000l * 60 * 60 * 24 * 30;
        //atribui o zoom maximo para a timeline, 4 anos, cursos de faculdade
        zoomMax = 1000l * 60 * 60 * 24 * 30 * 12 * 4;
        //aqui coloca as salasParaPesquisa e suas reservas
        montaTimeLine();
    }
    
    public void montaTimeLine(){
        if(salasSelecionadas == null){
            salasSelecionadas = new ArrayList<>();
        }
        for (Sala s : salasSelecionadas) {
//            timelineEvent = new TimelineEvent(null, null, null, false, s.getNome(), null);
//            timeline.add(timelineEvent);
        }
    }
    
    public void pesquisa(){
        if(salasSelecionadas.isEmpty()){
            Mensagem.mensagemError("Selecione pelo menos uma sala");
            return;
        }
        session = HibernateUtil.abreSessao();
        reservaDao = new ReservaDaoImpl();
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

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    public List<Sala> getSalasParaPesquisa() {
        return salasParaPesquisa;
    }

    public List<Sala> getSalasSelecionadas() {
        if(salasSelecionadas == null){
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
    
}
