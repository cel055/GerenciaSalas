function atualizaEventos(){
    document.getElementById("form_timeline:timelineTodasSalas").addEventListener("mouseover",function(evento){
        removeEventoTimeline();
        addEventoTimeline();
    },false);
}

function addEventoTimeline() {
    var reservas;
    reservas = document.getElementsByClassName("reserva");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].addEventListener("click", eventoTimeline, false);
    }
}

function removeEventoTimeline() {
    var reservas;
    reservas = document.getElementsByClassName("reserva");
    for (var i = 0; i < reservas.length; i++) {
        reservas[i].removeEventListener("click", eventoTimeline, false);
    }
}

function eventoTimeline(evento) {
    var ids = this.className.substring(this.className.indexOf(","), this.className.lastIndexOf(","));
    var input = document.getElementById("form_timeline:idsReserva");
    input.value = ids;
    $(input).trigger("change");
}