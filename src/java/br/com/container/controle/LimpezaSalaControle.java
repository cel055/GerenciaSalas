/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.modelo.SalaLimpeza;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
@ManagedBean
@ViewScoped
public class LimpezaSalaControle implements Serializable {

    private SalaLimpeza salaLimpeza;
    private SalaDao salaDao;
    private Session session;
    private DataModel<SalaLimpeza> dataModel;
    private int totalSala;

    private void abreSessao() {
        if (session == null) {
            session = HibernateUtil.abreSessao();
        } else if (!session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    @PostConstruct
    public void inicializar() {
        limpezaDiaAtual();
    }
    
    public void limpezaDiaAtual(){
        abreSessao();
        salaDao = new SalaDaoImpl();
        List<SalaLimpeza> todasSalas = salaDao.todasSalaLimpezaDoDia(session);
        totalSala = todasSalas.size();
        dataModel = new ListDataModel(todasSalas);
        session.close();
    }
    
    public void limpezaDiaAnterior(){
        abreSessao();
        salaDao = new SalaDaoImpl();
        List<SalaLimpeza> todasSalas = salaDao.todasSalaLimpezaDiaAnterior(session);
        totalSala = todasSalas.size();
        dataModel = new ListDataModel(todasSalas);
        session.close();
    }

//    getts e setts
    public SalaLimpeza getSalaLimpeza() {
        return salaLimpeza;
    }

    public void setSalaLimpeza(SalaLimpeza salaLimpeza) {
        this.salaLimpeza = salaLimpeza;
    }

    public DataModel<SalaLimpeza> getDataModel() {
        return dataModel;
    }

    public int getTotalSala() {
        return totalSala;
    }
    
}
