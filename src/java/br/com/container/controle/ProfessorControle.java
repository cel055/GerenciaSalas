/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import br.com.container.dao.HibernateUtil;
import br.com.container.dao.ProfessorDao;
import br.com.container.dao.ProfessorDaoImpl;
import br.com.container.modelo.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.hibernate.HibernateException;
import org.hibernate.Session;


/**
 *
 * @author cel05
 */
@ManagedBean(name = "profC")
@ViewScoped
public class ProfessorControle implements Serializable {

    private boolean mostraToolbar = false;
    private boolean pesquisaPorDisciplina = false;
    private String pesqNome = "";
    private String pesqDisciplina = "";

    private Session session;
    private ProfessorDao dao;

    private Professor prof;
    private List<Professor> profs;
    private DataModel<Professor> modelProfs;
    private List<String> disciplinas;

    private void abreSessao() {
        if (session == null || !session.isOpen()) {
            session = HibernateUtil.abreSessao();
        }
    }

    public void mudaToolbar() {
        prof = new Professor();
        prof.setWhatsapp(true);
        profs = new ArrayList();
        disciplinas = new ArrayList();
        pesqNome = "";
        mostraToolbar = !mostraToolbar;
    }

    public void pesquisar() {
        dao = new ProfessorDaoImpl();
        try {
            abreSessao();

//            if (!pesqNome.equals("")) {
                profs = dao.pesquisaPorNome(pesqNome, session);
//            } else {
//                profs = dao.pesqPorDisciplina(pesqDisciplina, session);
//            }

            modelProfs = new ListDataModel(profs);
        } catch (HibernateException ex) {
            System.err.println("Erro pesquisa professor:\n" + ex.getMessage());
        } finally {
            session.close();
        }
    }

    public void salvar() {
        dao = new ProfessorDaoImpl();
        try {
            abreSessao();
            prof.setDisciplinas(parseDisciplinas());
            dao.salvarOuAlterar(prof, session);
            Mensagem.salvar("Professor " + prof.getNome());
        } catch (Exception ex) {
            Mensagem.mensagemError("Erro ao salvar\nTente novamente");
            System.err.println("Erro pesquisa professor:\n" + ex.getMessage());
        } finally {
            prof = new Professor();
            prof.setWhatsapp(true);
            disciplinas = new ArrayList();
            session.close();
        }
    }
    
    public void alterarProf(){
        mostraToolbar = !mostraToolbar;
        prof = modelProfs.getRowData();
        parseDisciplinas(prof.getDisciplinas());
    }

    private String parseDisciplinas() {
        StringBuilder builder = new StringBuilder();
        for (String disciplina : disciplinas) {
            if(disciplina.equals("")){
                continue;
            }
            builder.append(disciplina);
            builder.append(";");
        }
        return builder.toString();
    }
    
    private void parseDisciplinas(String disciplinas) {
        this.disciplinas = new ArrayList(Arrays.asList(disciplinas.split(";")));
    }
    
    public void adicionarDisciplina(){
        disciplinas.add("");
    }
    
    public void removerDisciplina(int index){
        disciplinas.remove(index);
    }
    
    public void excluir(){
        prof = modelProfs.getRowData();
        dao = new ProfessorDaoImpl();
        try{
            abreSessao();
            dao.remover(prof, session);
            Mensagem.excluir("Professor " + prof.getNome());
            prof = new Professor();
        }catch(Exception ex){
            System.err.println("Erro ao excluir professor:\n" + ex.getMessage());
        }finally{
            session.close();
        }
    }

    //Getters e Setters
    public boolean isMostraToolbar() {
        return mostraToolbar;
    }

    public void setMostraToolbar(boolean mostraToolbar) {
        this.mostraToolbar = mostraToolbar;
    }

    public boolean isPesquisaPorDisciplina() {
        return pesquisaPorDisciplina;
    }

    public void setPesquisaPorDisciplina(boolean pesquisaPorDisciplina) {
        this.pesquisaPorDisciplina = pesquisaPorDisciplina;
    }

    public String getPesqNome() {
        return pesqNome;
    }

    public void setPesqNome(String pesqNome) {
        this.pesqNome = pesqNome;
    }

    public String getPesqDisciplina() {
        return pesqDisciplina;
    }

    public void setPesqDisciplina(String pesqDisciplina) {
        this.pesqDisciplina = pesqDisciplina;
    }

    public Professor getProf() {
        if(prof == null){
            prof = new Professor();
            prof.setWhatsapp(true);
        }
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public List<Professor> getProfs() {
        if(profs == null){
            profs = new ArrayList();
        }
        return profs;
    }

    public void setProfs(List<Professor> profs) {
        this.profs = profs;
    }

    public DataModel<Professor> getModelProfs() {
        return modelProfs;
    }

    public void setModelProfs(DataModel<Professor> modelProfs) {
        this.modelProfs = modelProfs;
    }

    public List<String> getDisciplinas() {
        if(disciplinas == null){
            disciplinas = new ArrayList();
        }
        return disciplinas;
    }

    public void setDisciplinas(List<String> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
