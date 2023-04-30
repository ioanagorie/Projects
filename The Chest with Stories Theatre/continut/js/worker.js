var lungime;
onmessage = function(x){
    if(x.data != lungime){
        this.console.log("Au fost adăugate noi valori!");
    }
    lungime = x.data;
    this.console.log("localStorage.length = " + lungime);
}