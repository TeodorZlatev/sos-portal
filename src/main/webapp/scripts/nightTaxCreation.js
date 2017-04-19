window.onload = function() {
	createAjaxRequest(function(responseText) {
		var rooms = JSON.parse(responseText);
		var select = document.getElementById("rooms");
		var option = document.createElement("option");
		option.text = "№";
		option.value = "№";
		select.appendChild(option);
		for (i = 0; i < rooms.length; i++) {
			var option = document.createElement("option");
			option.text = rooms[i].number;
			option.value = rooms[i].id;
			select.appendChild(option);
		}
	}, "GET", "/sos-portal/rooms?blockId=" + 1);
}

function expandHosts() {
	var roomId = document.getElementById("rooms").value;

	createAjaxRequest(function(responseText) {
		var hosts = JSON.parse(responseText);
		var select = document.getElementById("hosts");
		select.innerHTML = "";
		for (i = 0; i < hosts.length; i++) {
			var option = document.createElement("option");
			option.text = hosts[i].username;
			option.value = hosts[i].id;
			select.appendChild(option);
		}
		document.getElementById("hostsDiv").style.display = "block";
		$("#date").datepicker({
			dateFormat : 'dd-mm-yy'
		});

	}, "GET", "/sos-portal/hosts?roomId=" + roomId + "&blockId=" + 1);
}

function createNightTax() {

	var guest = document.getElementById("guest").value;
	var rooms = document.getElementById("rooms");
	var roomId = rooms.options[rooms.selectedIndex].value;
	var host = document.getElementById("hosts").value;
	var date = document.getElementById("date").value;

	var data = JSON.stringify({
		"guestName" : guest,
		"roomId" : roomId,
		"hostId" : host,
		"date" : date
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
	}, "POST", "/sos-portal/nightTax", data, requestHeader);

}
