document.addEventListener("DOMContentLoaded", function(){
	initialSet();
    initialChange();
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
}
function initialChange() {
    var btns = document.getElementsByName('btn-admin');
    console.log(btns.length);
    for (let i = 0; i < btns.length; i++) {
        if (btns[i].getAttribute('data-admin') == "true") {
            btns[i].classList.remove('btn-outline-warning');
            btns[i].classList.add('btn-warning');
        }
    }
}