function incarcaPersoane() {
    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            newTable(this);
        }
    };
    xmlhttp.open("GET", "resurse/persoane.xml", true);
    xmlhttp.send();
}

function newTable(xml) {
    var i;
    var table = "<table><tr><th>Nume</th><th>Prenume</th><th>Vârsta</th><th>Adresa</th><th>Ocupatie</th><th>Statut</th><th>Telefon</th></tr>";
    var x = (xml.responseXML).getElementsByTagName("persoana");
    var n= x.length;

    for (i = 0; i < n; i++) {
        table += "<tr><td>" + x[i].getElementsByTagName("nume")[0].childNodes[0].nodeValue +
            "</td><td>" + x[i].getElementsByTagName("prenume")[0].childNodes[0].nodeValue +
            "</td><td>" + x[i].getElementsByTagName("varsta")[0].childNodes[0].nodeValue +
            "</td><td>" + "str. " + x[i].getElementsByTagName("adresa")[0].childNodes[0].nodeValue +
            ", numar " + x[i].getElementsByTagName("numar")[0].childNodes[0].nodeValue +
            ", localitate " + x[i].getElementsByTagName("localitate")[0].childNodes[0].nodeValue +
            ", judet " + x[i].getElementsByTagName("judet")[0].childNodes[0].nodeValue +
            ", tara " + x[i].getElementsByTagName("tara")[0].childNodes[0].nodeValue +
            "</td><td>" + x[i].getElementsByTagName("ocupatie")[0].childNodes[0].nodeValue +
            "</td><td>"+ x[i].getElementsByTagName("statut")[0].childNodes[0].nodeValue +
            "</td><td>"+ x[i].getElementsByTagName("telefon")[0].childNodes[0].nodeValue +
            "</td></tr>";
        }
    table = table+ "</table>"
    document.getElementById("tabel").innerHTML = table;
}