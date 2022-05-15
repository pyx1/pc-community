function add(){
    var name = document.getElementById('name').value;
    var imagesource = document.getElementById('imagesource').value;
    var description = document.getElementById('description').value;
    var price = document.getElementById('price').value;
    var banner1 = document.getElementById('banner1').value;
    var banner2 = document.getElementById('banner2').value;
    var banner3 = document.getElementById('banner3').value;
    var stock = document.getElementById('stock').value;
    var category = document.getElementById('category').value;

    
    var item = {name: name, description: description,imageSource:imagesource, bannerSource1:banner1, bannerSource2:banner2, bannerSource3:banner3 , price: price, category:category, stock: stock};
    console.log(category);
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener('load', function(){
        window.location="/admin/";
    });
    client.open("POST", "/admin/productos");
    client.setRequestHeader("Content-type", "application/json");
    client.setRequestHeader(document.getElementById("_csrfHeaders").content, document.getElementById("_csrf").content);
    var body = JSON.stringify(item);
    client.send(body);
}