window.onload = function() {
	createHeader();
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

function createElement(destination, element, innerHTML, onclick, attributes,
		oninput) {
	var element = document.createElement(element);

	if (innerHTML) {
		element.innerHTML = innerHTML;
	}

	if (onclick) {
		element.onclick = onclick;
	}

	if (oninput) {
		element.oninput = oninput;
	}

	if (attributes) {
		for (i = 0; i < attributes.length; i++) {
			element.setAttribute(attributes[i].name, attributes[i].value);
		}
	}

	destination.appendChild(element);

	return element;
}

function createHeader() {
	createAjaxRequest(function(responseText) {
		var data = JSON.parse(responseText);

		var menu = document.getElementById("menu");

		for (i = 0; i < data.length; i++) {

			var li = createHeaderHelper(data[i]);

			menu.appendChild(li);
		}

	}, "GET", "/sos-portal/header");
}

function createHeaderHelper(data) {
	var li = document.createElement("li");

	li.setAttribute("class", "item")

	var a = document.createElement("a");

	a.onclick = function() {
		window[data.onclick]();
	};

	a.innerHTML = data.innerHTML;

	li.appendChild(a);

	return li;
}

function showMessage(responseData) {
	var response = JSON.parse(responseData);

	var messenger = document.getElementById("messenger");
	
	if (response.responseStatus === "SUCCESS") {
		messenger.setAttribute("class", "basicSpan infoSpan");
	} else {
		messenger.setAttribute("class", "basicSpan alertSpan");
	}
	
	var outputMessage = document.getElementById("message");
	
	outputMessage.innerHTML = response.message;
	
	messenger.style.display = "block";
	
	return response.responseStatus;
}

// INSERT INHABITANT
function createDivInsertInhabitant() {
	document.title = "Записване на живущ";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Записване на живущ");
	
	var table = createElement(mainDiv, "table", null, null, [ {
		name: "id",
		value: "tableInsertInhabitant"
	}, {
		name : "class",
		value : "center"
	} ]);
	
	var trNames = createElement(table, "tr");
	var tdNamesOutput = createElement(trNames, "td");
	createElement(tdNamesOutput, "output", "Имена:");
	var tdNamesInput = createElement(trNames, "td");
	createElement(tdNamesInput, "input", null, null, [ {
		name : "id",
		value : "inhabited"
	} ]);
	
	var trPersonalNumber = createElement(table, "tr");
	var tdPersonalNumberOutput = createElement(trPersonalNumber, "td");
	createElement(tdPersonalNumberOutput, "output", "ЕГН:");
	var tdPersonalNumberInput = createElement(trPersonalNumber, "td");
	createElement(tdPersonalNumberInput, "input", null, null, [ {
		name : "id",
		value : "personalNumber"
	} ]);
	
	var trRoom = createElement(table, "tr");
	var tdRoomOutput = createElement(trRoom, "td");
	createElement(tdRoomOutput, "output",  "Стая:");
	var tdRoomInput = createElement(trRoom, "td");
	createElement(tdRoomInput, "input", null, null, [ {
		name : "id",
		value : "room"
	} ]);

	createElement(mainDiv, "button", "Запази", insertInhabitant);
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

	createAjaxRequest(function(responseText) {
		showMessage(responseText);
	}, "POST", "/sos-portal/inhabitant", data, requestHeader);

}

// CREATE NIGHT TAX
function createDivCreateNightTax() {

	document.title = "Вписване на нощувка";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Вписване на нощувка", null, [ {
		name : "class",
		value : "centerText"
	} ]);
	
	var table = createElement(mainDiv, "table", null, null, [ {
		name: "id",
		value: "tableCreateNightTax"
	}, {
		name : "class",
		value : "center"
	} ]);
	
	var trGuest = createElement(table, "tr");
	var tdGuestOutput = createElement(trGuest, "td");
	createElement(tdGuestOutput, "output", "Гост: ");
	var tdGuestInput = createElement(trGuest, "td");
	createElement(tdGuestInput, "input", null, null, [ {
		name : "id",
		value : "guest"
	} ]);
	
	var trRoom = createElement(table, "tr");
	var tdRoomOutput = createElement(trRoom, "td");
	createElement(tdRoomOutput, "output", "Стая: ");
	var tdRoomSelect = createElement(trRoom, "td");
	createElement(tdRoomSelect, "select", null, null, [ {
		name : "id",
		value : "rooms"
	} ], expandHosts);
	
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
		var hostsJSON = JSON.parse(responseText);

		var hostsSelect = document.getElementById("hosts");

		// check if exists, otherwise complete the div
		if (hostsSelect) {
			updateDivCreateNightTax(hostsJSON);
		} else {
			completeDivCreateNightTax(hostsJSON);
		}

	}, "GET", "/sos-portal/hosts?roomId=" + roomId + "&blockId=" + 1);
}

function updateDivCreateNightTax(hostsJSON) {
	var select = document.getElementById("hosts");
	select.innerHTML = "";

	for (i = 0; i < hostsJSON.length; i++) {
		var option = document.createElement("option");
		option.text = hostsJSON[i].username;
		option.value = hostsJSON[i].id;
		select.appendChild(option);
	}
}

function completeDivCreateNightTax(hostsJSON) {
	var mainDiv = document.getElementById("mainDiv");
	var table = document.getElementById("tableCreateNightTax");

	var trHosts = createElement(table, "tr");
	var tdHostsOutput = createElement(trHosts, "td");
	createElement(tdHostsOutput, "output", "Домакин: ");
	var tdHostsSelect = createElement(trHosts, "td");
	var select = createElement(tdHostsSelect, "select", null, null, [ {
		name : "id",
		value : "hosts"
	} ]);
	select.innerHTML = "";
	for (i = 0; i < hostsJSON.length; i++) {
		var option = document.createElement("option");
		option.text = hostsJSON[i].username;
		option.value = hostsJSON[i].id;
		select.appendChild(option);
	}
	
	var trDate = createElement(table, "tr");
	var tdDateOutput = createElement(trDate, "td");
	createElement(tdDateOutput, "output", "Дата: ");
	var tdDateInput = createElement(trDate, "td");
	createElement(tdDateInput, "input", null, null, [ {
		name : "id",
		value : "date"
	}, {
		name : "type",
		value : "text"
	} ]);
	$("#date").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	
	createElement(mainDiv, "button", "Запази", createNightTax);
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

	createAjaxRequest(function(responseText) {
		var result = showMessage(responseText);
	
		if (result === "SUCCESS") {
			createDivCreateNightTax();
		}
	}, "POST", "/sos-portal/nightTax", data, requestHeader);

}

// PEOPLE WITH NIGHT TAXES
function createDivPeopleWithNightTaxes() {

	document.title = "Хора с нощувки";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Хора с нощувки", null, [ {
		name : "class",
		value : "centerText"
	} ]);

	createElement(mainDiv, "input", null, null, [ {
		name : "id",
		value : "searchTxt"
	}, {
		name : "type",
		value : "text"
	}, {
		name : "placeholder",
		value : "Търси"
	}, {
		name : "class",
		value : "centerContent"
	} ], searchPeople);

	var table = createElement(mainDiv, "table", null, null, [ {
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

	createElement(mainDiv, "div", null, null, [ {
		name : "id",
		value : "paginator"
	}, {
		name : "class",
		value : "pagination"
	} ]);

	fetchPeopleWithNightTaxes(1, 1);
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

function searchPeople() {
	var marker = document.getElementById("searchTxt").value;
	
	createAjaxRequest(function(responseText) {
		var nightTaxes = JSON.parse(responseText);

		fillInPeopleTable(nightTaxes);

		fetchPeopleWithNightTaxesAboutPagination(1, 1);
	}, "GET", "/sos-portal/search?marker=" + marker + "&blockId=" + 1 + "&pageNumber=" + 1);
}

function createAHrefNightTaxesStatus(userId, innerHTML, status) {
	var a = document.createElement("a");

	a.onclick = function() {
		document.getElementById("mainDiv").innerHTML = "";

		createDivNightTaxesPerPerson();

		fetchNightTaxesPerPerson(userId, status, 1);
	};

	a.innerHTML = innerHTML;

	return a;
}

// NIGHT TAXES PER PERSON
function createDivNightTaxesPerPerson() {
	document.title = "Нощувки";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Нощувки");

	var table = createElement(mainDiv, "table", null, null, [ {
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

	var thDateCreated = createElement(tr, "th", "Дата на създаване");

	createElement(table, "tbody");

	createElement(mainDiv, "div", null, null, [ {
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
					status, paginatorData.active, paginatorData.active, true));
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
		var statusText;
		if (nightTaxes[index].status === "UNPAID") {
			statusText = document.createTextNode("неплатена");
		} else {
			statusText = document.createTextNode("платена");
		}
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
