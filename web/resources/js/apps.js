/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function (){
    $('.js-toggle').bind('click', function(){
//        cria ou remove o 'is-toggle' do elemento
        $('.js-barra_lateral').toggleClass('is-toggle'); 
        $('.js-conteudo').toggleClass('is-toggle');
    });
});

