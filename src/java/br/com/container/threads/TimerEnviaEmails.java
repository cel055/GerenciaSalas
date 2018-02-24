/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.threads;

import br.com.container.dao.AgendaDao;
import br.com.container.dao.AgendaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.Agenda;
import br.com.container.util.EnviaEmail;
import br.com.container.util.Estaticos;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimerTask;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
public class TimerEnviaEmails extends TimerTask{

    //este é o método que será executado quando a data correta chegar
    @Override
    public void run() {
        Calendar hoje = new GregorianCalendar(Estaticos.horaProj);
        Session session = null;
        AgendaDao dao;
        List<Agenda> agendas;
        
        try{
            
            dao = new AgendaDaoImpl();
            session = HibernateUtil.abreSessao();
            agendas = dao.procuraAgendaDoDia(session, hoje.getTime());
            for (Agenda agenda : agendas) {
                EnviaEmail.enviaLembreteEmail(agenda);
            }
            
        }catch(HibernateException e){
            System.out.println("/nErro hibernate lista emails do dia classe TimerEnviaEmails/n" + e.getMessage());
        }catch(Exception e){
            System.out.println("/nErro generico classe TimerEnviaEmails/n" + e.getMessage());
        }finally{
            if(session != null && session.isOpen()){
                session.close();
            }
        }
    }
    
}
