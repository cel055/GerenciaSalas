/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author cel05
 */
public class Estaticos {
    //Criei estas para que não ocorra erro de digitação já qu isto será usado na pesquisa do banco
    public static final String MANHA = "Matutino";
    public static final String TARDE = "Vespertino";
    public static final String NOITE = "Noturno";
    
    public static final TimeZone horaProj = TimeZone.getTimeZone("America/Sao_Paulo");
    
    public static String formataData(Date data) {
        return new SimpleDateFormat("dd-MM-yyyy").format(data);
    }
    
    public static String formataData(String data) {
        return new SimpleDateFormat("dd-MM-yyyy").format(data);
    }
}
