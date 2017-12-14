/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author maodeobra
 */
@Entity
@Table(name = "dia_da_semana")
public class DiaDaSemana implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private int numeroDoDia;
    private String nomeDoDia;

    public DiaDaSemana() {
    }

    public DiaDaSemana(int numeroDoDia, String nomeDoDia) {
        this.numeroDoDia = numeroDoDia;
        this.nomeDoDia = nomeDoDia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumeroDoDia() {
        return numeroDoDia;
    }

    public void setNumeroDoDia(int numeroDoDia) {
        this.numeroDoDia = numeroDoDia;
    }

    public String getNomeDoDia() {
        return nomeDoDia;
    }

    public void setNomeDoDia(String nomeDoDia) {
        this.nomeDoDia = nomeDoDia;
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
        if (!(object instanceof DiaDaSemana)) {
            return false;
        }
        DiaDaSemana other = (DiaDaSemana) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.container.modelo.DiaDaSemana[ id=" + id + " ]";
    }
    
}
