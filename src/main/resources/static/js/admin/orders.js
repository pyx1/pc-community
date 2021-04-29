<<<<<<< HEAD
document.addEventListener("DOMContentLoaded", function(){
	initialChange();
})


function updateOrder(order_id){
    var client = new XMLHttpRequest();
    var btn = document.getElementById('btn-order-'+order_id)
    client.responseType = "json";
    client.addEventListener('load', function(){
        console.log(this.response);
        btn.disabled = true;
        btn.classList.remove('btn-outline-success');
        btn.classList.add('btn-success');
        btn.innerHTML = "Tramitado";
    });
    client.open("PUT", "/admin/order/"+order_id);
    client.setRequestHeader("Content-type", "application/json");
    client.send();
}

function initialChange(){
    var btns = document.getElementsByName('order-btn');
    console.log(btns.length);
    for(let i = 0; i<btns.length;i++){
        console.log(btns[i]);
        switch(btns[i].getAttribute('data-state')){
            case "Sent":
                btns[i].disabled = true;
                btns[i].classList.remove('btn-outline-success');
                btns[i].classList.add('btn-success');
                btns[i].innerHTML = "Tramitado";

        }
    }
=======
document.addEventListener("DOMContentLoaded", function(){
	initialChange();
})


function updateOrder(order_id){
    var client = new XMLHttpRequest();
    var btn = document.getElementById('btn-order-'+order_id)
    client.responseType = "json";
    client.addEventListener('load', function(){
        console.log(this.response);
        btn.disabled = true;
        btn.classList.remove('btn-outline-success');
        btn.classList.add('btn-success');
        btn.innerHTML = "Tramitado";
    });
    client.open("PUT", "/admin/order/"+order_id);
    client.setRequestHeader("Content-type", "application/json");
    client.send();
}

function initialChange(){
    var btns = document.getElementsByName('order-btn');
    console.log(btns.length);
    for(let i = 0; i<btns.length;i++){
        console.log(btns[i]);
        switch(btns[i].getAttribute('data-state')){
            case "Sent":
                btns[i].disabled = true;
                btns[i].classList.remove('btn-outline-success');
                btns[i].classList.add('btn-success');
                btns[i].innerHTML = "Tramitado";

        }
    }
>>>>>>> 74a060ef41211405c3b2d9186dd4e8562e6cf7cf
}