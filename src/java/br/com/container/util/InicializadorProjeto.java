/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.util;

import br.com.container.dao.DiaDaSemanaDao;
import br.com.container.dao.DiaDaSemanaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.DiaDaSemana;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
public class InicializadorProjeto implements ServletContextListener {

    private ServletContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.context = sce.getServletContext();
        Session sessao = HibernateUtil.abreSessao();
        DiaDaSemanaDao dao = new DiaDaSemanaDaoImpl();
        try {
            if (dao.listaTodos(sessao).isEmpty()) {
                salvaDiasDaSemana(dao, sessao);
            }
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Dias Da Semana " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao salvar Dias Da Semana " + e.getMessage());
        } finally {
            sessao.close();
        }

    }

    private void salvaDiasDaSemana(DiaDaSemanaDao dao, Session sessao) throws HibernateException {
        DiaDaSemana dia;
        dia = new DiaDaSemana(1, "Domingo");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(2, "Segunda");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(3, "Terça");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(4, "Quarta");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(5, "Quinta");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(6, "Sexta");
        dao.salvarOuAlterar(dia, sessao);
        dia = new DiaDaSemana(7, "Sábado");
        dao.salvarOuAlterar(dia, sessao);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        context = null;
    }

}
