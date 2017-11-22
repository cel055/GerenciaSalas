/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Perfil;
import br.com.container.modelo.Usuario;
import br.com.container.util.GeradorLetraNumero;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author silvio
 */
public class UsuarioDaoImplTest {

    private Usuario usuario;
    private UsuarioDao dao;
    private Session sessao;

    public UsuarioDaoImplTest() {
        dao = new UsuarioDaoImpl();
        sessao = HibernateUtil.abreSessao();
    }

    @Test
    public void testSalvar() {
        System.out.println("Teste salvar usuário");
        usuario = new Usuario(null, "Usuario teste", "usuario email" + GeradorLetraNumero.geradorLetraNumero(3), "usuario senha");
        usuario.setPerfil(new Perfil());
        usuario.getPerfil().setId(1L);
        try {
            dao.salvarOuAlterar(usuario, sessao);
            assertNotNull(usuario.getId());
        } catch (Exception e) {
            System.out.println("Erro ao salvar teste " + e.getMessage());
        }
    }

    @Test
    public void testAlterar() {
        System.out.println("Teste alterar usuário");
        verificaUsuarioExistenteBanco();
        String nomeAlterado = GeradorLetraNumero.geradorLetraNumero(7);
        usuario.setNome(nomeAlterado);
        try {
            dao.salvarOuAlterar(usuario, sessao);
            assertEquals(usuario.getNome(), nomeAlterado);
        } catch (Exception e) {
            System.out.println("Erro ao salvar teste " + e.getMessage());
        }
    }

    @Test
    public void testPesquisaEntidadeId() {
        System.out.println("Teste pesquisar por id usuário");
        usuario = dao.pesquisaEntidadeId(1l, sessao);
        assertNotNull(usuario.getId());
    }

    @Test
    public void testListaTodos() {
        System.out.println("Teste listar todos os usuário");
        verificaUsuarioExistenteBanco();
        List<Usuario> usuarios = dao.listaTodos(sessao);
        assertTrue(!usuarios.isEmpty());
    }

    @Test
    public void testPesquisaPorNome() {
        System.out.println("Teste pesquisa por nome usuário");
        verificaUsuarioExistenteBanco();
        List<Usuario> usuarios = dao.pesquisaPorNome(usuario.getNome(), sessao);
        assertTrue(!usuarios.isEmpty());
    }

    //metodos auxiliares
    private void verificaUsuarioExistenteBanco() {
        Long id;
        try {
            Query consulta = sessao.createQuery("select max(id) from Usuario");
            id = (Long) consulta.uniqueResult();
            if (id == null) {
                testSalvar();
            } else {
                usuario = dao.pesquisaEntidadeId(id, sessao);
            }
        } catch (HibernateException e) {
            System.out.println("Erro teste usuario " + e.getMessage());
        }
    }

}
