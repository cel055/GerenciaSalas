/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.container.util;

/**
 *
 * @author Senac PLC
 */
public class GeradorLetraNumero {
    public static String geradorLetraNumero(int numeroDigito) {
        String[] alfanumericos = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
            "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "U", "V", "W", "X", "Y", "Z"};
        
        String senha = "";
        int tamanho = alfanumericos.length;
        int caracter;
        for (int i = 0; i< numeroDigito; i ++) {
            caracter = (int) (Math.random() * tamanho);
            senha += alfanumericos[caracter];
        }
        return senha;
    }
    
    public static String geraNumero(int quantidade) {
        String senha = "";
        for (int i = 0; i < quantidade; i++) {
            senha += (int) (Math.random() * 10);
        }
        return senha;
    }
    //001.002.006-98
    public static String numeroCpf(){
        String cpf = "";
        int indice;
        for (int i = 0; i < 11; i++) {
            indice = (int) (Math.random() * 10);
            cpf += indice;
            if(i == 2 || i == 5){
                cpf += ".";
            }
            if(i == 8){
                cpf += "-";
            }
        }
        
        return cpf;
    }
}
