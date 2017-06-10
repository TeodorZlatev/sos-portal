function login(personalNumber, password) {
	var httpRequest = new XMLHttpRequest();
	httpRequest.open("POST", "/sos-portal/login", true);
	httpRequest.setRequestHeader("Content-type", "application/json");
	httpRequest.onreadystatechange = function() {
		window.location = "/sos-portal/main.html";
	}
	var data = JSON.stringify({
		"personalNumber" : personalNumber,
		"password" : password
	});
	httpRequest.send(data);
}