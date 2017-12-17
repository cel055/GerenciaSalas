/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.conversores;

import br.com.container.dao.DiaDaSemanaDao;
import br.com.container.dao.DiaDaSemanaDaoImpl;
import br.com.container.dao.HibernateUtil;
import br.com.container.modelo.DiaDaSemana;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.Session;

/**
 *
 * @author maodeobra
 */
@FacesConverter(value = "conversorDiaDaSemana")
public class DiaDaSemanaConverter implements Converter{

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if(value != null && !value.trim().equals("")){
            Session session = HibernateUtil.abreSessao();
            DiaDaSemanaDao dao = new DiaDaSemanaDaoImpl();
            DiaDaSemana diaDaSemana = dao.pesquisaEntidadeId(Long.parseLong(value), session);
            session.close();
            return diaDaSemana;
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object obj) {
        if(obj != null && !obj.equals("") && obj instanceof DiaDaSemana && ((DiaDaSemana)obj).getId() != null){
            return ((DiaDaSemana)obj).getId().toString();
        }
        return "";
    }
    
}
