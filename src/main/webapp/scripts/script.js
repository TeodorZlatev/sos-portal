window.onload = function() {
	fetchPeopleWithNightTaxes(1, 1);
	createDivPeopleWithNightTaxes();
}

// window.onload = function() {
// fetchNightTaxesPerPerson(2, "UNPAID", 1);
// createDivNightTaxesPerPerson();
// }

// window.onload = function() {
// createDivCreateNightTax();
// }

// window.onload = function() {
// createDivInsertInhabitant();
// }

function createAjaxRequest(functionOnReady, method, url, data, requestHeader) {
	var httpRequest = new XMLHttpRequest();

	httpRequest.onreadystatechange = function() {
		if (httpRequest.readyState == XMLHttpRequest.DONE) {
			if (this.responseText) {
				functionOnReady(this.responseText);
			} else {
				functionOnReady();
			}
		}
	}
	httpRequest.open(method, url, true);

	if (requestHeader) {
		for (i = 0; i < requestHeader.length; i++) {
			httpRequest.setRequestHeader(requestHeader[i].header,
					requestHeader[i].value);
		}
	}

	// httpRequest.setRequestHeader("Authorization",
	// "8f0322d9-dfa4-4d47-92a9-24051970a110");

	if (data) {
		httpRequest.send(data);
	} else {
		httpRequest.send();
	}
}

// insert inhabitant
function createDivInsertInhabitant() {
	document.title = "Записване на живущ";

	var body = document.getElementsByTagName("body")[0];

	var div = createElement(body, "div", null, null, [ {
		name : "id",
		value : "insertInhabitant"
	} ]);

	createElement(div, "h1", "Записване на живущ");

	createElement(div, "output", "Имена:");

	createElement(div, "input", null, null, [ {
		name : "id",
		value : "inhabited"
	} ]);

	createElement(div, "br");

	createElement(div, "output", "ЕГН:");

	createElement(div, "input", null, null, [ {
		name : "id",
		value : "personalNumber"
	} ]);

	createElement(div, "br");

	createElement(div, "output", "Стая:");

	createElement(div, "input", null, null, [ {
		name : "id",
		value : "room"
	} ]);

	createElement(div, "br");

	createElement(div, "button", "Запази", insertInhabitant);
}

function createElement(destination, element, innerHTML, onclick, attributes,
		onchange) {
	var element = document.createElement(element);

	if (innerHTML) {
		element.innerHTML = innerHTML;
	}

	if (onclick) {
		element.onclick = onclick;
	}

	if (onchange) {
		element.onchange = onchange;
	}

	if (attributes) {
		for (i = 0; i < attributes.length; i++) {
			element.setAttribute(attributes[i].name, attributes[i].value);
		}
	}

	destination.appendChild(element);

	return element;
}

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

// create night tax
function createDivCreateNightTax() {

	document.title = "Вписване на нощувка";

	var body = document.getElementsByTagName("body")[0];

	var createNightTaxDiv = createElement(body, "div", null, null, [ {
		name : "id",
		value : "createNightTax"
	} ]);

	createElement(createNightTaxDiv, "h1", "Вписване на нощувка");

	createElement(createNightTaxDiv, "output", "Гост: ");

	createElement(createNightTaxDiv, "input", null, null, [ {
		name : "id",
		value : "guest"
	} ]);

	createElement(createNightTaxDiv, "br");

	createElement(createNightTaxDiv, "output", "Стая: ");

	createElement(createNightTaxDiv, "select", null, null, [ {
		name : "id",
		value : "rooms"
	} ], expandHosts);

	createElement(createNightTaxDiv, "br");

	var hostsDiv = createElement(createNightTaxDiv, "div", null, null, [ {
		name : "id",
		value : "hostsDiv"
	} ]);

	createElement(hostsDiv, "output", "Домакин:");

	createElement(hostsDiv, "select", null, null, [ {
		name : "id",
		value : "hosts"
	} ]);

	createElement(hostsDiv, "br");

	createElement(hostsDiv, "output", "Дата:");

	createElement(hostsDiv, "input", null, null, [ {
		name : "id",
		value : "date"
	}, {
		name : "type",
		value : "text"
	} ]);

	createElement(hostsDiv, "br");

	createElement(hostsDiv, "button", "Запази", createNightTax);

	loadRoomsByBlock();
}

function loadRoomsByBlock() {
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

// night taxes
function createDivNightTaxesPerPerson() {
	document.title = "Нощувки";

	var body = document.getElementsByTagName("body")[0];

	var nightTaxesPerPersonDiv = createElement(body, "div", null, null, [ {
		name : "id",
		value : "nightTaxesPerPersonDiv"
	} ]);

	createElement(nightTaxesPerPersonDiv, "h1", "Нощувки");

	var table = createElement(nightTaxesPerPersonDiv, "table", null, null, [ {
		name : "id",
		value : "nightTaxesTable"
	}, {
		name : "class",
		value : "modified"
	} ]);

	var thead = createElement(table, "thead");

	var tr = createElement(thead, "tr");

	var thRoom = createElement(tr, "th", "Стая");

	var thStatus = createElement(tr, "th", "Статус");

	var thInhabitant = createElement(tr, "th", "Живущ");

	var thGuest = createElement(tr, "th", "Гост");

	var thDate = createElement(tr, "th", "Дата");

	var thCreatedBy = createElement(tr, "th", "Вписана от");

	var thDateCreated = createElement(tr, "th", "Дата");

	createElement(table, "tbody");

	var tfoot = createElement(table, "tfoot");

	thRoom = createElement(tfoot, "th", "Стая");

	thStatus = createElement(tfoot, "th", "Статус");

	thInhabitant = createElement(tfoot, "th", "Живущ");

	thGuest = createElement(tfoot, "th", "Гост");

	thDate = createElement(tfoot, "th", "Дата");

	thCreatedBy = createElement(tfoot, "th", "Вписана от");

	thDateCreated = createElement(tfoot, "th", "Дата");

	createElement(nightTaxesPerPersonDiv, "div", null, null, [ {
		name : "id",
		value : "paginator"
	}, {
		name : "class",
		value : "pagination"
	} ]);
}

function fetchNightTaxesPerPerson(userId, status, pageNumber) {

	createAjaxRequest(function(responseText) {
		var nightTaxes = JSON.parse(responseText);

		fillInNightTaxesPerPersonTable(nightTaxes);

		fetchNightTaxesPerPersonAboutPagination(userId, status, pageNumber)

	}, "GET", "/sos-portal/nightTaxesPerUser?userId=" + userId + "&status="
			+ status + "&pageNumber=" + pageNumber);

}

function fetchNightTaxesPerPersonAboutPagination(userId, status, pageNumber) {

	createAjaxRequest(function(responseText) {
		var paginatorData = JSON.parse(responseText);

		var div = document.getElementById("paginator");
		div.innerHTML = "";

		if (paginatorData.firstPage) {
			div.appendChild(createAHrefForNightTaxes(userId, status,
					paginatorData.firstPage, "&laquo;"));
		}

		for (i = 0; i < paginatorData.previousPages.length; i++) {
			div.appendChild(createAHrefForNightTaxes(userId, status,
					paginatorData.previousPages[i],
					paginatorData.previousPages[i]));
		}

		if (paginatorData.active) {
			div.appendChild(createAHrefForNightTaxes(userId,
					paginatorData.active, status, paginatorData.active, true));
		}

		for (i = 0; i < paginatorData.nextPages.length; i++) {
			div.appendChild(createAHrefForNightTaxes(userId, status,
					paginatorData.nextPages[i], paginatorData.nextPages[i]));
		}

		if (paginatorData.lastPage) {
			div.appendChild(createAHrefForNightTaxes(userId, status,
					paginatorData.lastPage, "&raquo;"));
		}
	}, "GET", "/sos-portal/nightTaxesPerUserCount?userId=" + userId
			+ "&status=" + status + "&pageNumber=" + pageNumber);

}

function createAHrefForNightTaxes(userId, status, pageNumber, innerHTML, active) {
	var a = document.createElement("a");

	a.onclick = function() {
		fetchNightTaxesPerPerson(userId, status, pageNumber);
	};

	a.innerHTML = innerHTML;
	if (active) {
		a.setAttribute("class", "active");
	}

	return a;
}

function fillInNightTaxesPerPersonTable(nightTaxes) {
	var tbody = document.getElementById("nightTaxesTable").tBodies[0];
	tbody.innerHTML = "";

	for (index = 0; index < nightTaxes.length; index++) {
		var tr = document.createElement("tr");

		var tdRoom = document.createElement("td");
		var roomText = document.createTextNode(nightTaxes[index].roomNumber);
		tdRoom.appendChild(roomText);
		tr.appendChild(tdRoom);

		var tdStatus = document.createElement("td");
		var statusText = document.createTextNode(nightTaxes[index].status);
		tdStatus.appendChild(statusText);
		tr.appendChild(tdStatus);

		var tdHost = document.createElement("td");
		var hostText = document.createTextNode(nightTaxes[index].hostName);
		tdHost.appendChild(hostText);
		tr.appendChild(tdHost);

		var tdGuest = document.createElement("td");
		var guestText = document.createTextNode(nightTaxes[index].guestName);
		tdGuest.appendChild(guestText);
		tr.appendChild(tdGuest);

		var tdDate = document.createElement("td");
		var dateText = document.createTextNode(nightTaxes[index].date);
		tdDate.appendChild(dateText);
		tr.appendChild(tdDate);

		var tdCreator = document.createElement("td");
		var creatorText = document
				.createTextNode(nightTaxes[index].creatorName);
		tdCreator.appendChild(creatorText);
		tr.appendChild(tdCreator);

		var tdDateCreated = document.createElement("td");
		var dateCreatedText = document
				.createTextNode(nightTaxes[index].dateCreated);
		tdDateCreated.appendChild(dateCreatedText);
		tr.appendChild(tdDateCreated);

		tbody.appendChild(tr);
	}

	nightTaxesTable.appendChild(tbody);
}

// people
// window.onload = function() {
// fetchPeople(1, 1);
// fetchPeopleAboutPagination(1, 1);
// }

function createDivPeopleWithNightTaxes() {

	document.title = "Хора с нощувки";

	var body = document.getElementsByTagName("body")[0];

	var peopleWithNightTaxesDiv = createElement(body, "div", null, null, [ {
		name : "id",
		value : "peopleWithNightTaxesDiv"
	} ]);

	createElement(peopleWithNightTaxesDiv, "h1", "Хора с нощувки", null, [ {
		name : "class",
		value : "centerText"
	} ]);

	createElement(peopleWithNightTaxesDiv, "input", null, null, [ {
		name : "id",
		value : "search"
	}, {
		name : "type",
		value : "text"
	}, {
		name : "placeholder",
		value : "Търси"
	}, {
		name : "class",
		value : "centerContent"
	} ]);

	var table = createElement(peopleWithNightTaxesDiv, "table", null, null, [ {
		name : "id",
		value : "peopleWithNightTaxesTable"
	}, {
		name : "class",
		value : "modified"
	} ]);

	var thead = createElement(table, "thead");

	var tr = createElement(thead, "tr");

	var thNames = createElement(tr, "th", "Имена");

	var thPersonalNumber = createElement(tr, "th", "ЕГН");

	var thUnpaid = createElement(tr, "th", "Неплатени");

	var thPaid = createElement(tr, "th", "Платени");

	createElement(table, "tbody");

	tfoot = createElement(table, "tfoot");

	thNames = createElement(tfoot, "th", "Имена");

	thPersonalNumber = createElement(tfoot, "th", "ЕГН");

	thUnpaid = createElement(tfoot, "th", "Неплатени");

	thPaid = createElement(tfoot, "th", "Платени");

	createElement(peopleWithNightTaxesDiv, "div", null, null, [ {
		name : "id",
		value : "paginator"
	}, {
		name : "class",
		value : "pagination"
	} ]);
}

function fetchPeopleWithNightTaxes(blockId, pageNumber) {

	createAjaxRequest(function(responseText) {
		var nightTaxes = JSON.parse(responseText);

		fillInPeopleTable(nightTaxes);

		fetchPeopleWithNightTaxesAboutPagination(blockId, pageNumber);

	}, "GET", "/sos-portal/usersNightTaxesAboutBlock?blockId=" + blockId
			+ "&pageNumber=" + pageNumber);

}

function fetchPeopleWithNightTaxesAboutPagination(blockId, pageNumber) {

	createAjaxRequest(function(responseText) {
		var paginatorData = JSON.parse(responseText);

		var div = document.getElementById("paginator");
		div.innerHTML = "";

		if (paginatorData.firstPage) {
			div.appendChild(createAHrefForPeople(blockId,
					paginatorData.firstPage, "&laquo;"));
		}

		for (i = 0; i < paginatorData.previousPages.length; i++) {
			div.appendChild(createAHrefForPeople(blockId,
					paginatorData.previousPages[i],
					paginatorData.previousPages[i]));
		}

		if (paginatorData.active) {
			div.appendChild(createAHrefForPeople(blockId, paginatorData.active,
					paginatorData.active, true));
		}

		for (i = 0; i < paginatorData.nextPages.length; i++) {
			div.appendChild(createAHrefForPeople(blockId,
					paginatorData.nextPages[i], paginatorData.nextPages[i]));
		}

		if (paginatorData.lastPage) {
			div.appendChild(createAHrefForPeople(blockId,
					paginatorData.lastPage, "&raquo;"));
		}
	}, "GET", "/sos-portal/usersNightTaxesAboutBlockCount?blockId=" + blockId
			+ "&pageNumber=" + pageNumber);

}

function createAHrefForPeople(blockId, pageNumber, innerHTML, active) {
	var a = document.createElement("a");

	a.onclick = function() {
		fetchPeopleWithNightTaxes(blockId, pageNumber);
	};

	a.innerHTML = innerHTML;
	if (active) {
		a.setAttribute("class", "active");
	}

	return a;
}

function fillInPeopleTable(people) {
	var tbody = document.getElementById("peopleWithNightTaxesTable").tBodies[0];
	tbody.innerHTML = "";

	for (index = 0; index < people.length; index++) {
		var tr = document.createElement("tr");

		var tdUsername = document.createElement("td");
		var usernameText = document.createTextNode(people[index].username);
		tdUsername.appendChild(usernameText);
		tr.appendChild(tdUsername);

		var tdPersonalNumber = document.createElement("td");
		var personalNumberText = document
				.createTextNode(people[index].personalNumber);
		tdPersonalNumber.appendChild(personalNumberText);
		tr.appendChild(tdPersonalNumber);

		var tdUnpaid = document.createElement("td");
		tdUnpaid.appendChild(createAHrefNightTaxesStatus(people[index].id,
				"неплатени", "UNPAID"));
		tr.appendChild(tdUnpaid);

		var tdPaid = document.createElement("td");
		tdPaid.appendChild(createAHrefNightTaxesStatus(people[index].id,
				"платени", "PAID"));
		tr.appendChild(tdPaid);

		tbody.appendChild(tr);
	}

	peopleWithNightTaxesTable.appendChild(tbody);
}

function createAHrefNightTaxesStatus(userId, text, status) {
	var a = document.createElement("a");

	a.onclick = function() {
		document.getElementById("peopleWithNightTaxesDiv").innerHTML = "";

		createDivNightTaxesPerPerson();

		fetchNightTaxesPerPerson(userId, status, 1);
	};

	a.innerHTML = text;

	return a;
}
