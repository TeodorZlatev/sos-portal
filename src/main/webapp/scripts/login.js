function login(personalNumber, password) {
	var httpRequest = new XMLHttpRequest();
	
	httpRequest.open("POST", "/sos-portal/login", true);
	httpRequest.setRequestHeader("Content-type", "application/json");
	httpRequest.onreadystatechange = function() {
		redirect(this.responseText);
	}
	var data = JSON.stringify({
		"personalNumber" : personalNumber,
		"password" : password
	});
	
	httpRequest.send(data);
}

function redirect(responseData) {
	var response = JSON.parse(responseData);

	var messenger = document.getElementById("messenger");
	
	if (response.responseStatus === "SUCCESS") {
		localStorage.setItem("token", response.token);
		
		window.location = "/sos-portal/main.html";
	} else {
		messenger.setAttribute("class", "basicSpan alertSpan");
		
		var outputMessage = document.getElementById("message");
		
		outputMessage.innerHTML = response.message;
		
		messenger.style.display = "block";
	}
}