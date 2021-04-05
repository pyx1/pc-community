(function ($) {
    $("#ex2").slider({});

    $("#range").on("slide", function () {
        var listProd = document.getElementsByName('fichasProductos');
        var range = $("#ex2").slider('getValue');
        for (let i = 0; i < listProd.length; i++) {
            var price = listProd[i].getAttribute('data-price');
            if (price < range[0] || price > range[1]) listProd[i].classList.add('hidden');
            else listProd[i].classList.remove('hidden');
        }
    });

    $('#stars-selector').on("click", function () {
        var listProd = document.getElementsByName('stars-holder');
        var stars = document.getElementsByName('stars');
        var marked = [];
        for (let i = 0; i < stars.length; i++) {
            if (stars[i].checked) {
                marked.push(stars[i].value);
            }
        }
        if (marked.length > 0) {
            for (let i = 0; i < listProd.length; i++) {
                if (marked.includes(listProd[i].getAttribute('stars-number'))) listProd[i].parentElement.parentElement.parentElement.classList.remove('hidden');
                else listProd[i].parentElement.parentElement.parentElement.classList.add('hidden');
            }
        }
        else {
            for (let i = 0; i < listProd.length; i++) {
                listProd[i].parentElement.parentElement.parentElement.classList.remove('hidden');
            }
        }
    });
})(jQuery);

function available() {
    var prods = document.getElementsByName('fichasProductos');
    if (document.getElementById('available').checked) {
        for (let i = 0; i < prods.length; i++) {
            if (prods[i].getAttribute('data-stock') <= 0) prods[i].classList.add('hidden');
        }
    }
    else {
        for (let i = 0; i < prods.length; i++) {
            prods[i].classList.remove('hidden');
        }
    }
}
