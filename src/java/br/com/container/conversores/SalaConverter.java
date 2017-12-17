/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.conversores;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.SalaDao;
import br.com.container.dao.SalaDaoImpl;
import br.com.container.modelo.Sala;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
@FacesConverter(value = "conversorSala")
public class SalaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && !value.trim().equals("")){
            Session session = HibernateUtil.abreSessao();
            SalaDao dao = new SalaDaoImpl();
            Sala sala = dao.pesquisaEntidadeId(Long.parseLong(value), session);
            session.close();
            return sala;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        if(obj != null && !obj.equals("") && obj instanceof Sala && ((Sala)obj).getId() != null){
            return ((Sala)obj).getId().toString();
        }
        return "";
    }
    
}
