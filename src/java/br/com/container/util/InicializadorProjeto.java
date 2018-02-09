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
import br.com.container.threads.TimerEnviaEmails;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Timer;
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
    private Timer timerEmails;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.context = sce.getServletContext();
        Session sessao = HibernateUtil.abreSessao();
        DiaDaSemanaDao dao = new DiaDaSemanaDaoImpl();
        
        Calendar dataAtual = new GregorianCalendar();
        dataAtual.add(Calendar.DATE, 1);
        dataAtual.set(Calendar.HOUR_OF_DAY, 7);
        dataAtual.set(Calendar.MINUTE, 0);
        dataAtual.set(Calendar.SECOND, 0);
        dataAtual.set(Calendar.MILLISECOND, 0);
        
        
        try {
            if (dao.listaTodos(sessao).isEmpty()) {
                salvaDiasDaSemana(dao, sessao);
            }
            
            timerEmails = new Timer();
            timerEmails.schedule(new TimerEnviaEmails(), dataAtual.getTime(), 24*60*60*1000);
            
        } catch (HibernateException e) {
            System.out.println("Erro ao salvar Dias Da Semana " + e.getMessage());
        } catch (Exception e) {
            System.err.println("/n/nErro ao iniciar projeto " + e.getMessage() + "/n/n");
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
        if(timerEmails != null){
            timerEmails.cancel();
        }
        context = null;
    }

}
