document.addEventListener("DOMContentLoaded", function(){
	changepath();
    if(!document.location.pathname.startsWith("/login"))setcartspan();
})

function createSElem(element){
    var child = document.createElement('DIV');
    child.innerHTML = "<div class='row search-item' style='height: 75px; width: 100%;'> \
    <div class='col-4' style='border-right: 1px #000000 solid'> \
      <div class='row ml-1 justify-content-center align-items-center' style='height: 100%;'>\
        <img src='"+ element.imageSource +"' style='height: 50px; width: 50px;'> \
      </div> \
    </div> \
    <div class='col-8'>\
      <div class='row ml-1 justify-content-center align-items-center' style='height: 100%;'> \
        <a href='/producto/"+element.idProduct+"'>"+ element.name +"</a> \
      </div> \
    </div> \
  </div> \
  <hr>";
    child = child.firstChild;
    document.getElementById('searchPlacer').appendChild(child);
}

function search(){
    var sbox = document.getElementById('searchBar');
    var search = sbox.value;
    var prices = document.getElementsByClassName("price-limit");
    var item ={
        "name" : search,
        "max" : prices[1].value != "" ? prices[1].value : "-1",
        "min" : prices[0].value != "" ? prices[0].value : "-1"
    }; 
    var client = new XMLHttpRequest();
    client.responseType = "json";
    
    client.addEventListener("load", function(){
        let exg = document.getElementById("searchPlacer").innerHTML="";
        let pri = document.getElementById("searchPrices");
        
        for(let elem in this.response){
            createSElem(this.response[elem]);
        }
        if(search == "" && prices[0].value == "" && prices[1].value == "")pri.classList.add("hidden");
        else pri.classList.remove("hidden");
        console.log(this.response);
    });
    client.open("POST", "/catalogo");
    client.setRequestHeader("Content-Type", "application/json");
    //Prueba
    var body = JSON.stringify(item);
    client.send(body);
}
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
