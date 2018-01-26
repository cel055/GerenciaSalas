package br.com.container.dao;

import br.com.container.modelo.Funcao;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Silvio
 */
public class FuncaoDaoImpl extends BaseDaoImpl<Funcao, Long> implements FuncaoDao{

    @Override
    public Funcao pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Funcao) session.get(Funcao.class, id);
    }

    @Override
    public List<Funcao> listaTodos(Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcao");
        return consulta.list();
    }

    @Override
    public List<Funcao> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcao where nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
