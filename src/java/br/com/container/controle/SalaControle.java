/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.PerfilDao;
import br.com.container.dao.PerfilDaoImpl;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Perfil;
import br.com.container.modelo.Sala;
import br.com.container.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
@ManagedBean(name = "salaC")
@ViewScoped
public class SalaControle implements Serializable {

    private Sala sala;
    private SalaDao salaDao;
    private Session sessao;
    private DataModel<Sala> modelSalas;
    private List<Sala> salas;
    private boolean mostra_toolbar;

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    public void novo() {
        mostra_toolbar = !mostra_toolbar;

    }

    public void novaPesquisa() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void preparaAlterar() {
        mostra_toolbar = !mostra_toolbar;
    }

    public void pesquisar() {
        salaDao = new SalaDaoImpl();
        try {
            abreSessao();
            salas = salaDao.pesquisaPorNome(sala.getNome(), sessao);
            modelSalas = new ListDataModel(salas);
            sala.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar sala por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        sala = new Sala();
    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        sala = modelSalas.getRowData();
    }

    public void excluir() {
        sala = modelSalas.getRowData();
        salaDao = new SalaDaoImpl();
        abreSessao();
        try {
            salaDao.remover(sala, sessao);
            salas.remove(sala);
            modelSalas = new ListDataModel(salas);
            Mensagem.excluir("Sala");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir sala: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        salaDao = new SalaDaoImpl();
        abreSessao();
        try {
            salaDao.salvarOuAlterar(sala, sessao);
            Mensagem.salvar("Sala " + sala.getNome());
            sala = null;
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Sala " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Sala Controle " + e.getMessage());
        } finally {
            sessao.close();
        }
    }


    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Sala getSala() {
        if (sala == null) {
            sala = new Sala();
        }
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }
    
    public DataModel<Sala> getModelSalas() {
        return modelSalas;
    }

    public void setModelSalas(DataModel<Sala> modelSalas) {
        this.modelSalas = modelSalas;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Sala> getSalas() {
        return salas;
    }

}
