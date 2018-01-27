package br.com.container.controle;

import br.com.container.dao.FuncaoDao;
import br.com.container.dao.FuncaoDaoImpl;
import br.com.container.dao.FuncionarioDao;
import br.com.container.dao.FuncionarioDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Funcao;
import br.com.container.modelo.Funcionario;
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
@ManagedBean(name = "funcionarioC")
@ViewScoped
public class FuncionarioControle implements Serializable {

    private Funcionario funcionario;
    private FuncionarioDao funcionarioDao;
    private Funcao funcao;
    private Session sessao;
    private DataModel<Funcionario> modelFuncionarios;
    private List<Funcionario> funcionarios;
    private List<SelectItem> funcoes;
    private boolean mostra_toolbar;

    @PostConstruct
    public void constroiTudo() {
        carregaComboFuncaoes();
    }

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
        funcionarioDao = new FuncionarioDaoImpl();
        try {
            abreSessao();
            funcionarios = funcionarioDao.pesquisaPorNome(funcionario.getNome(), sessao);
            modelFuncionarios = new ListDataModel(funcionarios);
            funcionario.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar funcionario por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        funcionario = new Funcionario();
        funcao = new Funcao();
    }

    //metodos getts e setts

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        funcionario = modelFuncionarios.getRowData();
        funcao = funcionario.getFuncao();
    }

    public void excluir() {
        funcionario = modelFuncionarios.getRowData();
        funcionarioDao = new FuncionarioDaoImpl();
        abreSessao();
        try {
            funcionarioDao.remover(funcionario, sessao);
            funcionarios.remove(funcionario);
            modelFuncionarios = new ListDataModel(funcionarios);
            Mensagem.excluir("Funcionário");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        funcionario.setFuncao(funcao);
        funcionarioDao = new FuncionarioDaoImpl();
        abreSessao();
        try {

            funcionarioDao.salvarOuAlterar(funcionario, sessao);
            Mensagem.salvar("Funcionário " + funcionario.getNome());
            funcionario = null;
            funcao = null;

        } catch (HibernateException e) {
            boolean isLoginDuplicado = e.getCause().getMessage().contains("'email_UNIQUE'");
            if (isLoginDuplicado) {
                Mensagem.campoExiste("E-mail");
            }
            System.out.println("Erro ao salvar funcionario " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar funcionarioDao Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    private void carregaComboFuncaoes() {
        List<Funcao> todasFuncoes;
        try {
            abreSessao();
            funcoes = new ArrayList();

            FuncaoDao funcaoDao = new FuncaoDaoImpl();
            todasFuncoes = funcaoDao.listaTodos(sessao);
            todasFuncoes.stream().forEach((perf) -> {
                funcoes.add(new SelectItem(perf.getId(), perf.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar função " + hi.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Funcionario getFuncionario() {
        if (funcionario == null) {
            funcionario = new Funcionario();
        }
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Funcao getFuncao() {
        if (funcao == null) {
            funcao = new Funcao();
        }
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public DataModel<Funcionario> getModelFuncionarios() {
        return modelFuncionarios;
    }

    public void setModelFuncionarios(DataModel<Funcionario> modelUsuarios) {
        this.modelFuncionarios = modelUsuarios;
    }

    public List<SelectItem> getFuncoes() {
        return funcoes;
    }

    public void setFuncoes(List<SelectItem> funcoes) {
        this.funcoes = funcoes;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

}
