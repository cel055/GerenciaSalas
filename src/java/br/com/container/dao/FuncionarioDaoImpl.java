
package br.com.container.dao;

import br.com.container.modelo.Funcionario;
import br.com.container.modelo.Usuario;
import java.io.Serializable;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author silvio
 */
public class FuncionarioDaoImpl extends BaseDaoImpl<Funcionario, Long> implements FuncionarioDao, Serializable{

    @Override
    public Funcionario pesquisaEntidadeId(Long id, Session session) throws HibernateException {
        return (Funcionario) session.get(Usuario.class, id);
    }

    @Override
    public List<Funcionario> listaTodos(Session session) throws HibernateException {
        return session.createQuery("from Funcionario").list();
    }

    @Override
    public List<Funcionario> pesquisaPorNome(String nome, Session session) throws HibernateException {
        Query consulta = session.createQuery("from Funcionario u where u.nome like :nome");
        consulta.setParameter("nome", "%" + nome + "%");
        return consulta.list();
    }
    
}
