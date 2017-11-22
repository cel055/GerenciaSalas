/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.PerfilDao;
import br.com.container.dao.PerfilDaoImpl;
import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Perfil;
import br.com.container.modelo.Usuario;
import br.com.container.util.GeradorLetraNumero;
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
@ManagedBean(name = "usuarioC")
@ViewScoped
public class UsuarioControle implements Serializable {

    private Usuario usuario;
    private UsuarioDao usuarioDao;
    private Perfil perfil;
    private Session sessao;
    private DataModel<Usuario> modelUsuarios;
    private List<Usuario> usuarios;
    private List<SelectItem> perfis;
    private boolean mostra_toolbar;

    @PostConstruct
    public void constroiTudo() {
        carregaComboPerfil();
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
        usuarioDao = new UsuarioDaoImpl();
        UsuarioLogado logado = new UsuarioLogado();
        try {
            abreSessao();
            usuarios = usuarioDao.pesquisaPorNome(usuario.getNome(), sessao);
            usuarios.remove(logado.usuarioLogadoSpring(sessao));
            modelUsuarios = new ListDataModel(usuarios);
            usuario.setNome(null);
        } catch (Exception e) {
            System.out.println("erro ao pesquisar usuário por nome: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limpar() {
        usuario = new Usuario();
        perfil = new Perfil();
    }

    //metodos getts e setts
    public void alteraStatus() {
        usuario = modelUsuarios.getRowData();
        abreSessao();
        usuarioDao = new UsuarioDaoImpl();

        if (usuario.isEnable()) {
            usuario.setEnable(false);
        } else {
            usuario.setEnable(true);
        }
        try {
            usuarioDao.salvarOuAlterar(usuario, sessao);
            limpar();
        } catch (Exception e) {
            System.out.println("Erro ao alterar status" + e.getMessage());
        } finally {
            sessao.close();
        }

    }

    public void carregarParaAlterar() {
        mostra_toolbar = !mostra_toolbar;
        usuario = modelUsuarios.getRowData();
        perfil = usuario.getPerfil();
    }

    public void excluir() {
        usuario = modelUsuarios.getRowData();
        usuarioDao = new UsuarioDaoImpl();
        abreSessao();
        try {
            usuarioDao.remover(usuario, sessao);
            usuarios.remove(usuario);
            modelUsuarios = new ListDataModel(usuarios);
            Mensagem.excluir("Usuario");
            limpar();
        } catch (Exception e) {
            System.out.println("erro ao excluir: " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        usuario.setPerfil(perfil);
        usuarioDao = new UsuarioDaoImpl();
        abreSessao();
        String senha = "12345";
        usuario.setSenha(senha);
        if (usuario.getId() == null) {
            usuario.setEnable(true);
        }
        try {

            usuarioDao.salvarOuAlterar(usuario, sessao);
//            EnviarEmails.enviaEmail(usuario);
            Mensagem.salvar("Usuario " + usuario.getNome());
            usuario = null;
            perfil = null;

        } catch (HibernateException e) {
            boolean isLoginDuplicado = e.getCause().getMessage().contains("'login_UNIQUE'");
            if (isLoginDuplicado) {
                Mensagem.campoExiste("E-mail");
            }
            System.out.println("Erro ao salvar usuario " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro no salvar usuario Controle "
                    + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    private void carregaComboPerfil() {
        List<Perfil> todosPerfis;
        try {
            abreSessao();
            perfis = new ArrayList();

            PerfilDao perfilDao = new PerfilDaoImpl();
            todosPerfis = perfilDao.todosPerfis(sessao);
            todosPerfis.stream().forEach((perf) -> {
                perfis.add(new SelectItem(perf.getId(), perf.getNome()));
            });
        } catch (HibernateException hi) {
            System.out.println("Erro ao carregar combo perfil " + hi.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void limparTela() {
        limpar();
    }

    //getters e setters
    public Usuario getUsuario() {
        if (usuario == null) {
            usuario = new Usuario();
        }
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Perfil getPerfil() {
        if (perfil == null) {
            perfil = new Perfil();
        }
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public DataModel<Usuario> getModelUsuarios() {
        return modelUsuarios;
    }

    public void setModelUsuarios(DataModel<Usuario> modelUsuarios) {
        this.modelUsuarios = modelUsuarios;
    }

    public List<SelectItem> getPerfis() {
        return perfis;
    }

    public void setPerfis(List<SelectItem> perfis) {
        this.perfis = perfis;
    }

    public boolean isMostra_toolbar() {
        return mostra_toolbar;
    }

    public void setMostra_toolbar(boolean mostra_toolbar) {
        this.mostra_toolbar = mostra_toolbar;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

}
