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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author silvio
 */
public class EnviaEmail implements Serializable {

    public static void enviaConfirmacaoEmail(Agenda agenda) {
        List<String> emailsParaEnvio = listaContatos(agenda);
        emailsParaEnvio.add(agenda.getUsuario().getLogin());

        String assunto = "Evento Senac Palhoça";

        StringBuilder builderCorpoEmail = new StringBuilder();
        builderCorpoEmail.append("<h1>.:Senac Palhoça:.</h1>");
        builderCorpoEmail.append(montaCorpoParaAgenda(agenda));

        String corpoEmail = builderCorpoEmail.toString();

        enviaEmail(assunto, corpoEmail, emailsParaEnvio);
    }

    private static List<String> listaContatos(Agenda agenda) {
        List<String> emailsParaEnvio;
        if (!agenda.getConvidado().isEmpty()) {
            emailsParaEnvio = new ArrayList<>(Arrays.asList(agenda.getConvidado().split(";")));
        } else {
            emailsParaEnvio = new ArrayList<>();
        }
        return emailsParaEnvio;
    }

    /**
     *
     * @param agenda Esse método é para enviar os e-mails de lembrete, que seram
     * pesquisado na tabela agenda e se tiver lembrete para essa data será
     * acionado esse metódo
     */
    public static void enviaLembreteEmail(Agenda agenda) {

        List<String> emailsParaEnvio = listaContatos(agenda);
        emailsParaEnvio.add(agenda.getUsuario().getLogin());

        String assunto = "Lembrete de Evento Senac Palhoca";

        StringBuilder builderCorpoEmail = new StringBuilder();
        builderCorpoEmail.append("<h1>.:Senac Palhoca - Lembrete:.</h1>");
        builderCorpoEmail.append(montaCorpoParaAgenda(agenda));

        String corpoEmail = builderCorpoEmail.toString();

        enviaEmail(assunto, corpoEmail, emailsParaEnvio);
    }

    /**
     *
     * @param agenda Esse método é para enviar os e-mails de cancelamento de
     * evento
     */
    public static void enviaCancelamentoEventoEmail(Agenda agenda) {

        List<String> emailsParaEnvio = listaContatos(agenda);
        emailsParaEnvio.add(agenda.getUsuario().getLogin());

        String assunto = "Cancelamento de Evento Senac Palhoca";

        StringBuilder builderCorpoEmail = new StringBuilder();
        builderCorpoEmail.append("<h1>.:Senac Palhoca - Evento Cancelado:.</h1>");
        builderCorpoEmail.append(montaCorpoParaAgenda(agenda));

        String corpoEmail = builderCorpoEmail.toString();

        enviaEmail(assunto, corpoEmail, emailsParaEnvio);
    }

    /**
     *
     * @param agenda Esse método é para enviar os e-mails de alteração de evento
     * (eventos editados na interface gráfica)
     */
    public static void enviaAlteracaoEventoEmail(Agenda agenda) {

        List<String> emailsParaEnvio = listaContatos(agenda);
        emailsParaEnvio.add(agenda.getUsuario().getLogin());

        String assunto = "Alteracao de Evento Senac Palhoca";

        StringBuilder builderCorpoEmail = new StringBuilder();
        builderCorpoEmail.append("<h1>.:Senac Palhoca - Evento Alterado:.</h1>");
        builderCorpoEmail.append(montaCorpoParaAgenda(agenda));

        String corpoEmail = builderCorpoEmail.toString();

        enviaEmail(assunto, corpoEmail, emailsParaEnvio);
    }

    private static StringBuilder montaCorpoParaAgenda(Agenda agenda) {
        StringBuilder builderCorpoEmail = new StringBuilder();
        builderCorpoEmail.append("<p class=\"p_topo\" style=\"margin-top: 20px\">Olá!!</p>");

        builderCorpoEmail.append("<br/>");

        builderCorpoEmail.append("<p>Você possui evento ");
        builderCorpoEmail.append(agenda.getAssunto());
        builderCorpoEmail.append(", dia <b>");
        builderCorpoEmail.append(formataData(agenda.getDia_evento()));
        builderCorpoEmail.append("</b></p>");

        builderCorpoEmail.append("<p>");
        builderCorpoEmail.append(agenda.getDescricao());
        builderCorpoEmail.append("</p>");

        builderCorpoEmail.append("<br />");

        builderCorpoEmail.append("<p>Atenciosamente;<br/>");
        builderCorpoEmail.append(agenda.getUsuario().getNome());
        builderCorpoEmail.append("<br/>Senac Palhoca<br/>");
        builderCorpoEmail.append("+55(48) 3341-9100<br/>");
        builderCorpoEmail.append("palhoca@sc.senac.br | https://www.facebook.com/FaculdadeSenacPalhoca/</p>");
        builderCorpoEmail.append("<p>").append(formataData(new Date())).append("</p>");
        return builderCorpoEmail;
    }

    private static String formataData(Date dia_evento) {
        SimpleDateFormat formatBra = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return formatBra.format(dia_evento);
    }

    private static void enviaEmail(String assunto, String corpo, List<String> e_mails) {
        String emailDeEnvio = "pfsensepalhoca@gmail.com";
        String senha = "senacplc";

        HtmlEmail email = new HtmlEmail();
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setSSLOnConnect(true);
        email.setStartTLSEnabled(true);
        email.setStartTLSRequired(true);
        email.setDebug(true);

        try {
            email.setFrom(emailDeEnvio, "Senac Palhoca");

            for (String e_mail : e_mails) {
                email.addTo(e_mail.trim());
            }

            email.setSubject(assunto);

            email.setHtmlMsg(corpo);

            email.setAuthenticator(new DefaultAuthenticator(emailDeEnvio, senha));
            email.send();
        } catch (EmailException e) {
            System.out.println("Erro ao enviar email " + e.getMessage());
        }
    }
}
