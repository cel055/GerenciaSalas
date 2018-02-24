/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author maodeobra
 */
@Entity
@Table(name = "reserva")
public class Reserva implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String informacao;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inicio;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fim;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idSala")
    private Sala sala;
    
//    @ManyToOne
//    @JoinColumn(name = "idPeriodo", nullable = false)
//    private Periodo periodo;
    
    private String periodo; //matutino, vespertino e noturno
    
    @ManyToMany
    @JoinTable(name = "reserva_dias_da_semana", 
            joinColumns = {@JoinColumn(name = "reserva_id")}, 
            inverseJoinColumns = {@JoinColumn(name = "dia_da_semana_id")})
    private List<DiaDaSemana> diasDaSemana;

    public Reserva() {
    }

    public Reserva(Long id, String informacao, Date inicio, Date fim, Usuario usuario, Sala sala, String periodo, List<DiaDaSemana> diasDaSemana) {
        this.id = id;
        this.informacao = informacao;
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
        this.sala = sala;
        this.periodo = periodo;
        this.diasDaSemana = diasDaSemana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInformacao() {
        return informacao;
    }

    public void setInformacao(String informacao) {
        this.informacao = informacao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

//    public Periodo getPeriodo() {
//        return periodo;
//    }
//
//    public void setPeriodo(Periodo periodo) {
//        this.periodo = periodo;
//    }
//
    public List<DiaDaSemana> getDiasDaSemana() {
        return diasDaSemana;
    }

    public void setDiasDaSemana(List<DiaDaSemana> diasDaSemana) {
        this.diasDaSemana = diasDaSemana;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reserva)) {
            return false;
        }
        Reserva other = (Reserva) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.container.modelo.Reserva[ id=" + id + " ]";
    }
    
}
