function change1() {
    document.getElementById('profile-data').classList.remove('hidden');
    document.getElementById('profile-orders').classList.add('hidden');
    document.getElementById('profile-opinions').classList.add('hidden');

}
function change2() {
    document.getElementById('profile-data').classList.add('hidden');
    document.getElementById('profile-orders').classList.remove('hidden');
    document.getElementById('profile-opinions').classList.add('hidden');

}
function change3() {
    document.getElementById('profile-data').classList.add('hidden');
    document.getElementById('profile-orders').classList.add('hidden');
    document.getElementById('profile-opinions').classList.remove('hidden');

}

function saveChanges() {
    //Comprobar con datos de API REST antes de lanzar la actualizacion
    var r = document.getElementById('profile-data').getElementsByTagName('input');
    var id = document.getElementById('profile-data').getAttribute('data-profile-id');
    if (document.getElementById('oldPassword').value) {
        if (document.getElementById('newPassword').value && document.getElementById('newPassword').value == document.getElementById('confirmPassword').value) {
            var item = { name: r[0].value, surname: r[1].value, email: r[6].value, phone: r[2].value, password: r[4].value, direction: r[9].value };
            var client = new XMLHttpRequest();
            client.responseType = "json";
            client.addEventListener('load', function () {
                console.log("Pedido completado" + this.response);
            });
            client.open("POST", "/profile/" + id);
            client.setRequestHeader("Content-type", "application/json");
            var body = JSON.stringify(item);
            client.send(body);
        }
        else if (document.getElementById('newPassword').value && document.getElementById('newPassword').value != document.getElementById('confirmPassword').value) alert('Las contraseñas no coinciden');
        else {
            var item = { name: r[0].value, surname: r[1].value, email: r[6].value, phone: r[2].value, password: r[3].value, direction: r[9].value };
            var client = new XMLHttpRequest();
            client.responseType = "json";
            client.addEventListener('load', function () {
                console.log("Pedido completado" + this.response);
            });
            client.open("PUT", "/profile/" + id);
            client.setRequestHeader("Content-type", "application/json");
            var body = JSON.stringify(item);
            client.send(body);
        }

    }
    else {
        alert('Introduce la contraseña para hacer cambios')
    }
}