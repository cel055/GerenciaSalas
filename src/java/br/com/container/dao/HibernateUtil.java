/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.dao;

import br.com.container.modelo.Agenda;
import br.com.container.modelo.DiaDaSemana;
import br.com.container.modelo.Funcao;
import br.com.container.modelo.Funcionario;
import br.com.container.modelo.Perfil;
import br.com.container.modelo.Professor;
import br.com.container.modelo.Reserva;
import br.com.container.modelo.Sala;
import br.com.container.modelo.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author silvio
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration cfg = new Configuration();
            cfg.addAnnotatedClass(Perfil.class);
            cfg.addAnnotatedClass(Usuario.class);
            cfg.addAnnotatedClass(Sala.class);
            cfg.addAnnotatedClass(DiaDaSemana.class);
            cfg.addAnnotatedClass(Reserva.class);
            cfg.addAnnotatedClass(Funcao.class);
            cfg.addAnnotatedClass(Funcionario.class);
            cfg.addAnnotatedClass(Agenda.class);
            cfg.addAnnotatedClass(Professor.class);

            cfg.configure("/br/com/container/dao/hibernate.cfg.xml");

            ServiceRegistry serviceRegistry =
                    new StandardServiceRegistryBuilder().
                    applySettings(cfg.getProperties()).build();

            sessionFactory = cfg.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            System.err.println("Erro ao construir session factory." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session abreSessao() {
        return sessionFactory.openSession();
    }
}
