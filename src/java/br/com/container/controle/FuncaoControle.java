/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.FuncaoDao;
import br.com.container.dao.FuncaoDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.dao.PerfilDao;
import br.com.container.dao.PerfilDaoImpl;
import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Funcao;
import br.com.container.modelo.Perfil;
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
@ManagedBean(name = "funcaoC")
@ViewScoped
public class FuncaoControle implements Serializable {

    private Funcao funcao;
    private FuncaoDao funcaoDao;
    private Session sessao;
    private DataModel<Funcao> modelFuncoes;
    private List<Funcao> funcaos;
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
        funcaoDao = new FuncaoDaoImpl();
        try {
            abreSessao();
            funcaos = funcaoDao.pesquisaPorNome(funcao.getNome(), sessao);
            modelFuncoes = new ListDataModel(funcaos);
            funcao.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar Funcao por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        funcao = new Funcao();
    }

    //metodos getts e setts

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        funcao = modelFuncoes.getRowData();
    }

    public void excluir() {
        funcao = modelFuncoes.getRowData();
        funcaoDao = new FuncaoDaoImpl();
        abreSessao();
        try {
            funcaoDao.remover(funcao, sessao);
            funcaos.remove(funcao);
            modelFuncoes = new ListDataModel(funcaos);
            Mensagem.excluir("Função " + funcao.getNome());
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        funcaoDao = new FuncaoDaoImpl();
        abreSessao();
        try {
            funcaoDao.salvarOuAlterar(funcao, sessao);
            Mensagem.salvar("Função " + funcao.getNome());
            funcao = null;

        } catch (HibernateException e) {
            boolean isLoginDuplicado = e.getCause().getMessage().contains("'nome_UNIQUE'");
            if (isLoginDuplicado) {
                Mensagem.campoExiste("Já existe essa função");
            }
            System.out.println("Erro ao salvar Funcao " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar Funcao Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
    }


    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Funcao getFuncao() {
        if (funcao == null) {
            funcao = new Funcao();
        }
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }


    public DataModel<Funcao> getModelFuncoes() {
        return modelFuncoes;
    }

    public void setModelFuncoes(DataModel<Funcao> modelFuncoes) {
        this.modelFuncoes = modelFuncoes;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Funcao> getFuncaos() {
        return funcaos;
    }

}
