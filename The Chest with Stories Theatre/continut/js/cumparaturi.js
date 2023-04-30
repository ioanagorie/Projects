var list=[];

var myWorker = new Worker('js/worker.js')
{
    sendToWorker= function(){
        data = localStorage.length;
        myWorker.postMessage(data);
    }
}

setInterval(sendToWorker, 3000);

function Produs(numeProdus, cantitateProdus){
    this.id = list.length+1;
    this.numeProdus= numeProdus;
    this.cantitateProdus = cantitateProdus;
}

function adauga(){
    var numeProdus= document.getElementById("nume").value;
    var cantitateProdus = document.getElementById("cantitate").value;
    localStorage.setItem(numeProdus, cantitateProdus);
    var produs = new Produs(numeProdus,cantitateProdus);
    adaugaInTable(produs);
    list.push(produs);    
}

function adaugaInTable(produs){
    var tabel = document.getElementById("tabelCumparaturi");
    var linie= tabel.insertRow();
    var coloana0 = linie.insertCell(0);
    var coloana1 = linie.insertCell(1);
    var coloana2 = linie.insertCell(2);
    coloana0.innerHTML = produs.id.toString();
    coloana1.innerHTML = produs.numeProdus.toString();
    coloana2.innerHTML = produs.cantitateProdus.toString();
}
