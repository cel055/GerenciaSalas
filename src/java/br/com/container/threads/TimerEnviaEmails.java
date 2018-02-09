/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.threads;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimerTask;

/**
 *
 * @author maodeobra
 */
public class TimerEnviaEmails extends TimerTask{

    //este é o método que será executado quando a data correta chegar
    @Override
    public void run() {
        //Verifica se é domingo, se for não faz nada
        if(new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == 1){
            return;
        }
        
    }
    
}
