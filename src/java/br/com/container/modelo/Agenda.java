/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Silvio
 */
@Entity
@Table(name = "agenda")
public class Agenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String assunto;
    private String convidado;
    @Column(nullable = false)
    @Lob
    private String descricao;
    @Column(nullable = false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date dia_evento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date cadastroEvento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date email2;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date email3;
    @OneToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getConvidado() {
        return convidado;
    }

    public void setConvidado(String convidado) {
        this.convidado = convidado;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDia_evento() {
        return dia_evento;
    }

    public void setDia_evento(Date dia_evento) {
        this.dia_evento = dia_evento;
    }

    public Date getCadastroEvento() {
        return cadastroEvento;
    }

    public void setCadastroEvento(Date cadastroEvento) {
        this.cadastroEvento = cadastroEvento;
    }

    public Date getEmail2() {
        return email2;
    }

    public void setEmail2(Date email2) {
        this.email2 = email2;
    }

    public Date getEmail3() {
        return email3;
    }

    public void setEmail3(Date email3) {
        this.email3 = email3;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        if (!(object instanceof Agenda)) {
            return false;
        }
        Agenda other = (Agenda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.container.modelo.Agenda[ id=" + id + " ]";
    }
    
}
