function insertInhabitant() {

	var inhabited = document.getElementById("inhabited").value;
	var personalNumber = document.getElementById("personalNumber").value;
	var room = document.getElementById("room").value;
	

	var data = JSON.stringify({
		"username" : inhabited,
		"personalNumber" : personalNumber,
		"room" : room,
	});

	requestHeader = [ {
		header : "Accept",
		value : "application/json"
	}, {
		header : "Content-type",
		value : "application/json"
	} ]

	createAjaxRequest(function() {
		// editDivs();
		// cleanFieldsInDiv("creationDiv");
	}, "POST", "/sos-portal/inhabitant", data, requestHeader);

}