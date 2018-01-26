/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.dao.UsuarioDao;
import br.com.container.dao.UsuarioDaoImpl;
import br.com.container.modelo.Usuario;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.hibernate.Session;

/**
 *
 * @author Senac PLC
 */
@ManagedBean(name = "usuarioLogadoC")
@SessionScoped
public class UsuarioLogadoControle implements Serializable {

    private Usuario usuario;
    private UsuarioLogado logado;
    private UsuarioDao usuarioDao;
    private Session sessao;
    private String senhaAtual;
    private String senhaNova;
    private Session session;
    private long totalSala;
    
    @PostConstruct
    public void inicializar() {
        //Carrega obj's iniciais do banco de dados
        session = HibernateUtil.abreSessao();
        SalaDao salaDao = new SalaDaoImpl();
        totalSala = salaDao.totalSala(session);
        session.close();
    }

    private void abreSessao() {
        if (sessao == null) {
            sessao = HibernateUtil.abreSessao();
        } else if (!sessao.isOpen()) {
            sessao = HibernateUtil.abreSessao();
        }
    }

    public void usuarioAtual() {
        abreSessao();
        try {
            logado = new UsuarioLogado();
            usuario = logado.usuarioLogadoSpring(sessao);
        } catch (Exception e) {
            System.out.println("Erro ao pegar o usuario atual -> " + e.getMessage());
        } finally {
            sessao.close();
        }
    }

    public void salvar() {
        usuarioDao = new UsuarioDaoImpl();
        abreSessao();
        if (!senhaNova.equals(usuario.getSenha())) {
            usuario.setSenha(senhaNova);
            try {
                usuarioDao.salvarOuAlterar(usuario, sessao);
            } catch (Exception e) {
                System.out.println("Erro ao salvar alteração de senha de usuário "
                        + e.getMessage());
            } finally {
                sessao.close();
                Mensagem.salvar("Alteração de senha ");
                limpaSenhas();
            }
        } else {
            Mensagem.senhaNovaIgualSenhaAtual();
            senhaNova = null;
        }
    }

    public void verificaSenhaAtual() {
        if (!senhaAtual.equals(usuario.getSenha())) {
            Mensagem.senhaAtualNaoConfere();
            senhaAtual = null;
        }
    }

    public void limpaSenhas() {
        if ((senhaNova != null) | (senhaAtual != null)) {
            senhaNova = null;
            senhaAtual = null;
        }
    }

    public void chamaEdicaoPerfil() throws IOException {
        limpaSenhas();
        usuarioAtual();
        FacesContext ctx = FacesContext.getCurrentInstance();
        String url = ctx.getExternalContext().encodeResourceURL("../logado/alterausuario.faces");
        ctx.getExternalContext().redirect(url);
    }

    //--- Getters e setters --------//    
    public Usuario getUsuario() {
        return usuario;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
    }

    public long getTotalSala() {
        return totalSala;
    }

}
