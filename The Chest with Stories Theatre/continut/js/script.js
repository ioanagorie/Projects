function data() {
    document.getElementById("data").innerHTML = Date();
}

function url() {
    var url = window.location.href;
    document.getElementById("url").innerHTML = url;
}

function showPosition(position) {
    document.getElementById("location").innerHTML = "Latitude: " + position.coords.latitude +
        "<br>Longitude: " + position.coords.longitude;
}
function getLocation() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(showPosition);
    } else {
        document.getElementById("location").innerHTML = "Geolocation is not supported by this browser.";
    }
}

function versiuneBrowser() {
    document.getElementById("version").innerHTML = navigator.appCodeName + " " + navigator.appVersion;
}

function SistemdeOperare() {
    var os = "Unknown OS";
    if (navigator.appVersion.indexOf("Win") != -1) os = "Windows";
    if (navigator.appVersion.indexOf("Mac") != -1) os = "MacOS";
    if (navigator.appVersion.indexOf("Linux") != -1) os = "Linux";
    document.getElementById("sistem").innerHTML = os;
}



function loading(){
    data();

    url();
    getLocation();
    versiuneBrowser();
    SistemdeOperare();
}

var point = null;
var ok = true;
function getMousePosition(event) {
    return {
        x: event.offsetX,
        y: event.offsetY
    };
}

function draw(event) {
    var canvas = document.getElementById("Canvas");
    var position = getMousePosition(event);
    var posx = position.x;
    var posy = position.y;
    if (ok != true) {
        ok= true;
        var ctx = canvas.getContext("2d");
        ctx.beginPath();
        ctx.rect(point.x,point.y, Math.abs(posx - point.x), Math.abs(posy - point.y));
        ctx.lineWidth = 3;
        ctx.strokeStyle = document.getElementById("contur").value;
        ctx.stroke();
        ctx.fillStyle = document.getElementById("umplere").value;
        ctx.fill();
    }
    else{
        ok = false;
        point = position;
    }

}

function inserareLinie() {
    var tabel = document.getElementById("tabelSectiunea3");
    var linie = tabel.insertRow(document.getElementById("index").value);
    var casuta;
    for (i = 0; i <= tabel.rows[0].cells.length -1; i++) {
        casuta = linie.insertCell(i);
        casuta.style.backgroundColor = document.getElementById("culoare-linie").value;
    }
}

function inserareColoana() {
    var tabel = document.getElementById("tabelSectiunea3");
    var coloana;
    for (i = 0; i <= tabel.rows.length-1; i++) {
        coloana = tabel.rows[i].insertCell(document.getElementById("index").value);
        coloana.style.backgroundColor = document.getElementById("culoare-linie").value;
    }
}

function schimbaContinut(resursa,jsFisier,jsFunctie) {
    const xhttp = new XMLHttpRequest();   
    console.log('print');
     xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
        document.getElementById("continut").innerHTML = this.responseText;
        if (jsFisier) {
          var elementScript = document.createElement('script');
          elementScript.onload = function() {
              console.log("hello");
              if (jsFunctie) {
                  window[jsFunctie]();
              }
          };
          elementScript.src = jsFisier;
          document.head.appendChild(elementScript);
      } else {
              if (jsFunctie) {
              window[jsFunctie]();
              }
             }
      }
    };
    xhttp.open("GET",  resursa + ".html", true);
    xhttp.send();
  }

  function validare() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var object = JSON.parse(this.responseText);
            if (document.getElementById("user").value == object[0].utilizator && document.getElementById("parola").value == object[0].parola) {
                document.getElementById("rezultat").innerHTML = "Numele de utilizator si parola au fost introduse CORECT";
            } else if (document.getElementById("user").value != object[0].utilizator && document.getElementById("parola").value != object[0].parola) {
                document.getElementById("rezultat").innerHTML = "Numele de utilizator si parola au fost introduse GREȘIT";
            } else if (document.getElementById("user").value != object[0].utilizator) {
                document.getElementById("rezultat").innerHTML = "Numele de utilizator a fost introdus GREȘIT";
            } else if (document.getElementById("parola").value != object[0].parola) {
                document.getElementById("rezultat").innerHTML = "Parola a fost introdusă GREȘIT";
            }
        }
    };

    xhttp.open("GET", "../../resurse/utilizatori.json", true);
    xhttp.send();
    
}


function adaugaInJson(){
    let user = document.querySelector('#user'); 
    let pass = document.querySelector('#pass'); 
    let xhr = new XMLHttpRequest(); 
    let url = "/resurse/utilizatori.json"; 
  
    xhr.open("POST", url, true); 
    xhr.setRequestHeader("Content-Type", "application/json"); 
  
    xhr.onreadystatechange = function () { 
        if (xhr.readyState === 4 && xhr.status === 200) { 
          var jsonObj = JSON.parse(xhr.responseText);
          console.log(jsonObj.utilizator + ", " + jsonObj.parola);
        } 
    }; 
    var data = JSON.stringify({ "utilizator": user.value, "parola": pass.value });  
    data= "["+ data+ "]";
    xhr.send(data); 
  }






