/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//var atual;
window.onload = function contador() {
    
    var atual = new Date();
    var atualAno = atual.getYear();
    if (atualAno < 1000)
        atualAno += 1900;
    var atualMes = (atual.getMonth() + 1) < 10 ? "0" + (atual.getMonth() + 1) : (atual.getMonth() + 1);
    var atualDia = atual.getDate() < 10 ? "0" + atual.getDate() : atual.getDate();
    var atualHora = atual.getHours() < 10 ? "0" + atual.getHours() : atual.getHours();
    var atualMinuto = atual.getMinutes() < 10 ? "0" + atual.getMinutes() : atual.getMinutes();

    var diaSemana = atual.getDay();
    var semana = new Array(6);
    semana[0] = 'Domingo';
    semana[1] = 'Segunda-Feira';
    semana[2] = 'Terça-Feira';
    semana[3] = 'Quarta-Feia';
    semana[4] = 'Quinta-Feira';
    semana[5] = 'Sexta-Feira';
    semana[6] = 'Sábado';



//document.getElementById("ano").innerHTML = atual.getYear();
//document.getElementById("mes").innerHTML = atual.getMonth();
//document.getElementById("dia").innerHTML = atual.getDay();
//document.getElementById("horaR").innerHTML = atual.getHours();
//document.getElementById("minuto").innerHTML = atual.getMinutes();
    document.getElementById("ano").innerHTML = atualAno;
    document.getElementById("mes").innerHTML = atualMes;
    document.getElementById("dia").innerHTML = atualDia;
    document.getElementById("horaR").innerHTML = atualHora;
    document.getElementById("minuto").innerHTML = atualMinuto;
    document.getElementById("diaSemana").innerHTML = " - " + semana[diaSemana];

    setTimeout(contador, 1000);

};

function toggleFullScreen() {
  if ((document.fullScreenElement && document.fullScreenElement !== null) ||    
   (!document.mozFullScreen && !document.webkitIsFullScreen)) {
    if (document.documentElement.requestFullScreen) {  
      document.documentElement.requestFullScreen();  
    } else if (document.documentElement.mozRequestFullScreen) {  
      document.documentElement.mozRequestFullScreen();  
    } else if (document.documentElement.webkitRequestFullScreen) {  
      document.documentElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);  
    }  
  } else {  
    if (document.cancelFullScreen) {  
      document.cancelFullScreen();  
    } else if (document.mozCancelFullScreen) {  
      document.mozCancelFullScreen();  
    } else if (document.webkitCancelFullScreen) {  
      document.webkitCancelFullScreen();  
    }  
  }  
} 

function requestFullScreen() {

  var el = document.body;

  // Supports most browsers and their versions.
  var requestMethod = el.requestFullScreen || el.webkitRequestFullScreen 
  || el.mozRequestFullScreen || el.msRequestFullScreen;

  if (requestMethod) {

    // Native full screen.
    requestMethod.call(el);

  } else if (typeof window.ActiveXObject !== "undefined") {

    // Older IE.
    var wscript = new ActiveXObject("WScript.Shell");

    if (wscript !== null) {
      wscript.SendKeys("{F11}");
    }
  }
}
