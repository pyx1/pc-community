var richeditor;
ClassicEditor
			.create( document.querySelector( '.rich-editor' ), {
				
				toolbar: {
					items: [
						'heading',
						'|',
						'bold',
						'italic',
						'link',
						'|',
						'insertTable',
						'mediaEmbed',
						'undo',
						'redo'
					]
				},
				language: 'es',
				table: {
					contentToolbar: [
						'tableColumn',
						'tableRow',
						'mergeTableCells'
					]
				},
				licenseKey: '',
				
				
			} )
			.then( editor => {
				richeditor = editor;

			} )
			.catch( error => {
				console.error( 'Oops, something went wrong!' );
				console.error( error );
			} );
document.addEventListener("DOMContentLoaded", function(){
	loadItems();

})




function updatecart(id){
    var idProducto = id;
    var uds = document.getElementById("units").value;
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(response){
        if(response.target.status == 401) alert("No se pudo añadir. Logueate por favor");
        else{
            setcartspan();
            location.reload();
        }
        
    });
    var item = [idProducto.toString(), uds];
    var body = JSON.stringify(item);
    client.open("POST", "/cart");
    client.setRequestHeader("Content-type", "application/json");
    client.setRequestHeader(document.getElementById("_csrfHeaders").content, document.getElementById("_csrf").content);
    client.send(body);
    
    
}

function loadItems(){
    var select = document.getElementById("units");
    var num = select.getAttribute('data-units');
    console.log(num);
    for(let i = 1; i<=num; i++){
        var child = document.createElement('option');
        child.innerHTML = "<option>"+i+"</option>";
        child = child.firstChild;
        select.appendChild(child);
    }
}

function leaveReview(){
    var holder = document.getElementById('new-number').value;
    var review = richeditor.getData();
    var csrf = document.getElementById("csrf-token").value;
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener("load", function(){
        console.log(this.response);
        location.reload();
    });
    var item = {stars: holder, message: review};
    var body = JSON.stringify(item);
    client.open("POST", "#");
    client.setRequestHeader("Content-type", "application/json");
    client.setRequestHeader(document.getElementById("_csrfHeaders").content, document.getElementById("_csrf").content);
    client.send(body);
}