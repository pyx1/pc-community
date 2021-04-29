document.addEventListener("DOMContentLoaded", function(){
	changepath();
    setcartspan();
})

function changepath(){
    var path = window.location.pathname;
    var child = document.createElement('span');
    child.innerHTML = "<span class='sr-only'>(current)</span>";
    child = child.firstChild;
    switch(path){
        case "/":
            document.getElementById('nav-start').appendChild(child); 
            document.getElementById('nav-start').parentElement.classList.add('active');
            break;
        case "/cart":
            document.getElementById('nav-cart').appendChild(child); 
            document.getElementById('nav-cart').parentElement.classList.add('active');
            break;
        case "/catalogo":
            document.getElementById('nav-catalog').appendChild(child); 
            document.getElementById('nav-catalog').parentElement.classList.add('active');
            break;
        case "/profile":
            document.getElementById('nav-profile').appendChild(child);
            document.getElementById('nav-profile').parentElement.classList.add('active'); 
            break;
        default: break;
    }
}

function setcartspan(){
    var cart = document.getElementById('CartSpan');
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
        if(parseInt(this.response.length) > 0){
            cart.innerText = this.response.length;
            cart.classList.remove('hidden')
        }
    });
    client.open("GET", "/api/cart/items");
    client.send();
}
