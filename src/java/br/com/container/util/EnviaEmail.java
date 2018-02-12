/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.util;

//import br.com.garantiatecv.entidade.Cliente;
import br.com.container.modelo.Agenda;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author silvio
 */
public class EnviaEmail implements Serializable {

    public static void enviaConfirmacaoEmail(Agenda agenda) {

        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        email.setStartTLSRequired(true);

        try {
            email.setFrom("tjunior103@gmail.com", "Senac Palhoça");
            if (!agenda.getConvidado().isEmpty()) {
                String[] e_mails = agenda.getConvidado().split(";");
                for (String e_mail1 : e_mails) {
                    email.addTo(e_mail1.trim());
                }
            }
            email.setDebug(true);
            email.setSubject("Evento Senac Palhoça ");

            StringBuilder builder = new StringBuilder();
            builder.append("<h1>.:Senac Palhoça :. ").append("</h1>");
            builder.append("<p class=\"p_topo\" style=\"margin-top: 20px\">Olá!! ");
            builder.append("<br /><br/>");
            builder.append("Você possui evento ").append(agenda.getAssunto())
                    .append(", dia ")
                    .append("<b> ")
                    .append(formataData(agenda.getDia_evento())).append("</p>");
            builder.append("</b> ");
            builder.append("<p>");
            builder.append(agenda.getDescricao());
            builder.append("</p>");
            builder.append("<br />");
            builder.append("<p>Atenciosamente;<br />");
            builder.append(agenda.getUsuario().getNome());
            builder.append("<br />Senac Palhoça<br />"
                    + "+55(48) 3341-9100 <br />"
                    + "palhoca@sc.senac.br | https://www.facebook.com/FaculdadeSenacPalhoca/</p>");

            email.setHtmlMsg(builder.toString());

            email.addTo(agenda.getUsuario().getLogin());
            email.setAuthenticator(new DefaultAuthenticator("tjunior103@gmail.com", "nemvemquenãotem"));
            email.send();
        } catch (EmailException e) {
            System.out.println("Erro ao enviar email " + e.getMessage());
        }
    }
    
    /**
     * 
     * @param agenda 
     * Esse método é para enviar os e-mails de lembrete, que seram pesquisado 
     * na tabela agenda e se tiver lembrete para 
     * essa data será acionado esse metódo
     */
    public static void enviaLembreteEmail(Agenda agenda) {

        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        email.setStartTLSRequired(true);

        try {
            email.setFrom("tjunior103@gmail.com", "Senac Palhoça");
            if (!agenda.getConvidado().isEmpty()) {
                String[] e_mails = agenda.getConvidado().split(";");
                for (String e_mail1 : e_mails) {
                    email.addTo(e_mail1.trim());
                }
            }
            email.setDebug(true);
            email.setSubject("Lembrete de Evento Senac Palhoça ");

            StringBuilder builder = new StringBuilder();
            builder.append("<h1>.:Senac Palhoça - Lembrete - :. ").append("</h1>");
            builder.append("<p class=\"p_topo\" style=\"margin-top: 20px\">Olá!! ");
            builder.append("<br /><br/>");
            builder.append("Você possui evento ").append(agenda.getAssunto())
                    .append(", dia ")
                    .append("<b> ")
                    .append(formataData(agenda.getDia_evento())).append("</p>");
            builder.append("</b> ");
            builder.append("<p>");
            builder.append(agenda.getDescricao());
            builder.append("</p>");
            builder.append("<br />");
            builder.append("<p>Atenciosamente;<br />");
            builder.append(agenda.getUsuario().getNome());
            builder.append("<br />Senac Palhoça<br />"
                    + "+55(48) 3341-9100 <br />"
                    + "palhoca@sc.senac.br | https://www.facebook.com/FaculdadeSenacPalhoca/</p>");

            email.setHtmlMsg(builder.toString());

            email.addTo(agenda.getUsuario().getLogin());
            email.setAuthenticator(new DefaultAuthenticator("tjunior103@gmail.com", "nemvemquenãotem"));
            email.send();
        } catch (EmailException e) {
            System.out.println("Erro ao enviar email " + e.getMessage());
        }
    }

    private static String formataData(Date dia_evento) {
        SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatBra.format(dia_evento);
    }
}
