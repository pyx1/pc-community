(function ($) {

    $("#paymentMethod").on("click", function () {
        var pm = document.getElementsByName('payment');
        var button = document.getElementById('payment-button');
        if (pm[0].checked) {
            button.setAttribute('payment-method', "card");
            document.getElementById('cardLabels').classList.remove('hidden');
            document.getElementById('bizumLabels').classList.add('hidden');
            document.getElementById('paypalLabels').classList.add('hidden');
        }
        else if (pm[1].checked) {
            button.setAttribute('payment-method', "bizum");
            document.getElementById('cardLabels').classList.add('hidden');
            document.getElementById('bizumLabels').classList.remove('hidden');
            document.getElementById('paypalLabels').classList.add('hidden');
        }
        else if (pm[2].checked) {
            button.setAttribute('payment-method', "paypal");
            document.getElementById('cardLabels').classList.add('hidden');
            document.getElementById('bizumLabels').classList.add('hidden');
            document.getElementById('paypalLabels').classList.remove('hidden');

        }
    });
    function getprice() {
        var prices = document.getElementsByName('price-holder');
        var uds = document.getElementsByName("uds-tag");
        var total = 0;
        for (let i = 0; i < prices.length; i++) {
            var udsp = parseInt(uds[i].getAttribute('data-uds'));
            var price = parseInt(prices[i].getAttribute('data-price'));
            console.log(price);
            total = total + udsp * price;
        }
        document.getElementById('total').innerText = total + " €";
    };
    getprice();

    function getunits() {
        var uds = document.getElementsByName("uds-tag");
        var holder = document.getElementsByName("units");
        for (let i = 0; i < holder.length; i++) {
            var amount = parseInt(uds[i].getAttribute('data-uds'));
            holder[i].innerText = amount;
        }
    };
    getunits();

    $(".delete-btn").on('click', function(){
        var id = this.parentElement.parentElement.getAttribute('data-product-id');
        deleteProd(id);

    });

})(jQuery);


function deleteProd(id){
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener('load', function () {
        location.reload();
    });
    client.open("DELETE", "/cart");
    client.setRequestHeader("Content-type", "application/json");
    client.setRequestHeader(document.getElementById("_csrfHeaders").contentEditable, document.getElementById("_csrf").content);
    var body = JSON.stringify(id);
    client.send(body);
}


function finishShopping() {
    var today = new Date().toLocaleDateString("en-US").split("/"); // mm - dd - yyyy
    var payment = document.getElementById('payment-button').getAttribute('payment-method');
    var detailsvalue;
    switch(payment){
        case "card":
            detailsvalue = "[*]Card number: " + document.getElementById('cardNumber').value + 
            "\n[*]Card name: " + document.getElementById('cardName').value + "\n[*]Date of Expiry: " +
            document.getElementById('dateofExpiry').value;
            break;
        case "bizum":
            detailsvalue = "Phone number to bizum: " + document.getElementById('bizumNumber').value;
            break;
        case "paypal":
            detailsvalue = "Paypal account: " + document.getElementById('paypalEmail').value;
            break;
        default:
            detailsvalue = null;
            break;
    }
    var item = { paymentMethod: payment, state: "Requested", details: detailsvalue, date: today[1] + "/" + today[0] + "/" + today[2] };
    var client = new XMLHttpRequest();
    client.responseType = "json";
    client.addEventListener('load', function () {
        location.reload();
    });
    client.open("POST", "/complete");
    client.setRequestHeader("Content-type", "application/json");
    client.setRequestHeader(document.getElementById("_csrfHeaders").content, document.getElementById("_csrf").content);
    var body = JSON.stringify(item);
    client.send(body);

}