document.addEventListener("DOMContentLoaded", function () {
    initialChange();
})

function throwAction(prodid) {
    var btn = document.getElementById('btn-hi-' + prodid);
    console.log(btn.attributes);
    if (btn.getAttribute('data-hightlight') != "Highlighted") {
        document.body.innerHTML += "<form id='dynForm' method='POST' action='/admin/product/highlight/" + prodid + "'></form>";
        document.getElementById("dynForm").submit()
    }
    else{
        var client = new XMLHttpRequest();
        client.responseType = "json";
        client.addEventListener('load', function (response) {
            location.reload();

        });
        client.open("DELETE", "/admin/product/highlight/" + prodid);
        client.setRequestHeader("Content-type", "application/json");
        client.send();
    }
}

function initialChange() {
    var btns = document.getElementsByName('btn-highlight');
    console.log(btns.length);
    for (let i = 0; i < btns.length; i++) {
        if (btns[i].getAttribute('data-hightlight') == "Highlighted") {
            btns[i].classList.remove('btn-outline-info');
            btns[i].classList.add('btn-info');
        }
    }
}