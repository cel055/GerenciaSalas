/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.controle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Senac PLC
 * Ola olha eu
 */
public class Mensagem {
    
    public static void sucesso(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                msg , ""));
    }
    
    public static void salvar(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                msg + " salvo com Sucesso.", "")
                );
    }
    
    public static void campoExiste(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                msg + " já existe!!", "")
                );
    }  

    public static void excluir(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_INFO,
                                msg + " excluído com sucesso!", "")
                );
    }
    
    public static void campoVazio(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                msg + " deve(m) ser preenchido", ""));
    }
    
    public static void clienteNaoExiste(String telefone) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                telefone + " não está cadastrado!", ""));
    }

    public static void senhaAtualNaoConfere() {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_FATAL,
                                " Senha atual não confere!", ""));
    }
    
    public static void senhaNovaIgualSenhaAtual() {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN,
                                " Senha nova é igual a senha atual!", ""));
    }

    static void valorMenor(double doubleValue) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                               doubleValue + " é menor que o total do pedido!", ""));
    }
    
    public static void mensagemError(String msg) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_ERROR,
                                msg, "")
                );
    } 

}
