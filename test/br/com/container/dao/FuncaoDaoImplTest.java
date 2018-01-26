/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Funcao;
import br.com.container.util.GeradorLetraNumero;
import java.util.List;
import org.hibernate.Session;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Silvio
 */
public class FuncaoDaoImplTest {
    private Funcao funcao;
    private FuncaoDao dao;
    private Session sessao;
    
    public FuncaoDaoImplTest() {
        sessao = HibernateUtil.abreSessao();
    }

    @Test
    public void testSalvar() {
        System.out.println("salvar...");
        dao = new FuncaoDaoImpl();
        funcao = new Funcao(null, GeradorLetraNumero.geradorLetraNumero(4) + "-função", "bla, bla, bla....");
        sessao = HibernateUtil.abreSessao();
        dao.salvarOuAlterar(funcao, sessao);
        sessao.close();
        assertNotNull(funcao.getId());
    }
    
//    @Test
    public void testPesquisaEntidadeId() {
        System.out.println("pesquisaEntidadeId");
    }

//    @Test
    public void testListaTodos() {
        System.out.println("listaTodos");
        Session session = null;
    }

//    @Test
    public void testPesquisaPorNome() {
        System.out.println("pesquisaPorNome");
        String nome = "";
        Session session = null;
    }
    
}
