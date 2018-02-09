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

    @Override
    public void run() {
        if(new GregorianCalendar().get(Calendar.DAY_OF_WEEK) == 1){
            return;
        }
        
    }
    
}
