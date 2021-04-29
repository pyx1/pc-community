<<<<<<< HEAD
document.addEventListener("DOMContentLoaded", function(){
	initialSet();
})

function initialSet(){
    var otag = document.getElementsByName('orders-amount');
    var rtag = document.getElementsByName('reviews-amount');
    
    for(let i = 0; i<otag.length; i++){
        var client = new XMLHttpRequest();
        client.responseType = "json";
        client.addEventListener('load', function(response){
            console.log(response.target);
            console.log(otag[i]);
            otag[i].innerHTML = this.response;
        });
        client.open("GET", "/api/admin/client/"+otag[i].parentElement.parentElement.parentElement.getAttribute('data-client-id') + "/orders/amount");
        client.send();
    }
    for(let i = 0; i<rtag.length; i++){
        var client = new XMLHttpRequest();
        client.responseType = "json";
        client.addEventListener('load', function(){
            rtag[i].innerHTML = this.response;
        });
        client.open("GET", "/api/admin/client/"+rtag[i].parentElement.parentElement.parentElement.getAttribute('data-client-id') + '/reviews/amount');
        client.send();
    }
=======
document.addEventListener("DOMContentLoaded", function(){
	initialSet();
})

function initialSet(){
    var otag = document.getElementsByName('orders-amount');
    var rtag = document.getElementsByName('reviews-amount');
    
    for(let i = 0; i<otag.length; i++){
        var client = new XMLHttpRequest();
        client.responseType = "json";
        client.addEventListener('load', function(response){
            console.log(response.target);
            console.log(otag[i]);
            otag[i].innerHTML = this.response;
        });
        client.open("GET", "/api/admin/client/"+otag[i].parentElement.parentElement.parentElement.getAttribute('data-client-id') + "/orders/amount");
        client.send();
    }
    for(let i = 0; i<rtag.length; i++){
        var client = new XMLHttpRequest();
        client.responseType = "json";
        client.addEventListener('load', function(){
            rtag[i].innerHTML = this.response;
        });
        client.open("GET", "/api/admin/client/"+rtag[i].parentElement.parentElement.parentElement.getAttribute('data-client-id') + '/reviews/amount');
        client.send();
    }
>>>>>>> 74a060ef41211405c3b2d9186dd4e8562e6cf7cf
}