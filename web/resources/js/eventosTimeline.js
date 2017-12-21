function addEventoTimeline() {
    var reservas;
    reservas = document.getElementsByClassName("manha");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("tarde");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("noite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaTarde");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaNoite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("tardeNoite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaTardeNoite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
}

function removeEventoTimeline() {
    var reservas;
    reservas = document.getElementsByClassName("manha");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("tarde");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("noite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaTarde");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaNoite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("tardeNoite");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
    reservas = document.getElementsByClassName("manhaTardeNoite");
}

function eventoTimeline(evento) {
    var ids = this.className.substring(this.className.indexOf(",") + 1, this.className.lastIndexOf(","));
    document.getElementById("form_pesquisa:idsReserva").value = ids;
    document.getElementById("form_pesquisa:enviaIdsParaPesquisa").click();
}