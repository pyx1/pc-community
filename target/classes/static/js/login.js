(function ($) {
	$('#change').on('click', function () {
		var login = document.getElementById('login');
		var reg = document.getElementById('register');
		var loginc = login.classList;
		var regc = reg.classList;
		var change = document.getElementById('change');
		var t = document.getElementById('title');
		var b = document.getElementById('button');

		if (loginc.contains('hidden') && !regc.contains('hidden')) {

			regc.add('pop-out-anim');

			setTimeout(function () {
				loginc.remove('hidden');
				regc.add('hidden');
				regc.remove('pop-out-anim');
				loginc.add('pop-up-anim');
				setTimeout(function () {
					loginc.remove('pop-up-anim');
				}, 2000);
				change.innerHTML = "Registrate";
				t.innerHTML = "Inicia sesion";
				b.innerHTML = "Entra";
				b.parentElement.setAttribute('data-lgn', 'login');
			}, 2000);



		}
		else if (!loginc.contains('hidden') && regc.contains('hidden')) {

			loginc.add('pop-out-anim');

			setTimeout(function () {
				regc.remove('hidden');
				loginc.add('hidden');
				loginc.remove('pop-out-anim');
				regc.add('pop-up-anim');
				setTimeout(function () {
					regc.remove('pop-up-anim');

				}, 2000);
				change.innerHTML = "Inicia sesion";
				t.innerHTML = "Registrate";
				b.innerHTML = "Registrate ya!"
				b.parentElement.setAttribute('data-lgn', 'register');
			}, 2000);

		}
	})

})(jQuery);


function register() {
	var r = document.getElementById('register').children;
	//var cTok = document.getElementById("csrfToken").value;
	var chck = true; //Check if all parameters set
	for (let i = 0; i < r.length; i++) {
		if (!r[i].value && r[i].type != "submit") chck = false;
	}
	if (chck) {
		if (r[4].value != r[5].value) {
			alert('Contraseñas diferentes');
		}
		else if (r[2].value != r[3].value) {
			alert('Diferentes correos');
		}
		else {
			var item = { name: r[0].value, surname: r[1].value, email: r[2].value, phone: r[7].value, password: r[4].value, direction: r[6].value };
			var client = new XMLHttpRequest();
			client.responseType = "json";
			client.addEventListener('load', function () {
				alert("Registrado, inicia sesion");
			});
			client.open("POST", "/register");
			client.setRequestHeader("Content-type", "application/json");
			client.setRequestHeader(document.getElementById("_csrfHeaders").content, document.getElementById("_csrf").content);
			var body = JSON.stringify(item);
			client.send(body);
			
		}

	}
	else {
		alert('Faltan datos por definir');
	}

}

