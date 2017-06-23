window.onload = function() {
	if (localStorage.getItem("token") === null) {
		window.location = "/sos-portal/index.html?unauthorized";
	} else {
		decode();
		createHeader();

		var role = localStorage.getItem("role");

		if (role === "ROLE_INHABITED" || role === "ROLE_CASHIER") {
			createDivPeopleWithNightTaxes(role);
		} else if (role === "ROLE_HOST") {
			createDivCreateNightTax();
		} else {
			createDivInsertUser();
		}
	}
}

function decode() {
	var token = localStorage.getItem("token");

	var payload = JSON.parse(window.atob(token.split('.')[1]));

	console
			.log("Role: " + payload.role + ", userId: " + +payload.userId
					+ ", roomId: " + +payload.roomId + ", blockId: "
					+ +payload.blockId);

	localStorage.setItem("role", payload.role);
	localStorage.setItem("userId", payload.userId);
	localStorage.setItem("roomId", payload.roomId);
	localStorage.setItem("blockId", payload.blockId);
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

	httpRequest
			.setRequestHeader("Authorization", localStorage.getItem("token"));

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

	if (destination) {
		destination.appendChild(element);
	}

	return element;
}

function showNoAvailable() {
	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h2", "Няма налични нощувки!");
}

function createHeader() {

	var menu = document.getElementById("menu");

	var role = localStorage.getItem("role");

	createHeaderHelper(menu, logout, "Изход", "right item");

	if (role === "ROLE_INHABITED") {
		createHeaderHelper(menu, function() {
			createDivPeopleWithNightTaxes(role)
		}, "Моите нощувки", "left item");
		return;
	}

	if (role === "ROLE_HOST") {
		createHeaderHelper(menu, createDivCreateNightTax,
				"Вписване на нощувка", "left item");

		createHeaderHelper(menu, function() {
			createDivPeopleWithNightTaxes(role)
		}, "Хора с нощувки", "left item");

		createHeaderHelper(menu, createDivInsertInhabitant,
				"Вписване на живущ", "left item");
		return;
	}

	if (role === "ROLE_CASHIER") {
		createHeaderHelper(menu, function() {
			createDivPeopleWithNightTaxes(role)
		}, "Хора с нощувки", "left item");
		return;
	}

	if (role === "ROLE_ADMINISTRATOR") {
		createHeaderHelper(menu, createDivInsertUser, "Вписване на потребител",
				"left item");

		createHeaderHelper(menu, function() {
			createDivPeopleWithNightTaxes(role)
		}, "Хора с нощувки", "left item");

		createHeaderHelper(menu, createDivCreateNightTax,
				"Вписване на нощувка", "left item");

		return;
	}

}

function createHeaderHelper(menu, onclick, innerHTML, attribute) {
	var li = document.createElement("li");

	li.setAttribute("class", attribute)

	var a = document.createElement("a");

	a.onclick = onclick;

	a.innerHTML = innerHTML;

	li.appendChild(a);

	menu.appendChild(li);
}

function logout() {
	localStorage.removeItem("token");
	localStorage.removeItem("role");
	localStorage.removeItem("userId");
	localStorage.removeItem("roomId");
	localStorage.removeItem("blockId");

	window.location = "/sos-portal/index.html";
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

// INSERT USER
function createDivInsertUser() {
	document.title = "Записване на потребител";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Записване на потребител");

	var table = createElement(mainDiv, "table", null, null, [ {
		name : "id",
		value : "tableInsertUser"
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
		value : "user"
	} ]);

	var trPersonalNumber = createElement(table, "tr");
	var tdPersonalNumberOutput = createElement(trPersonalNumber, "td");
	createElement(tdPersonalNumberOutput, "output", "ЕГН:");
	var tdPersonalNumberInput = createElement(trPersonalNumber, "td");
	createElement(tdPersonalNumberInput, "input", null, null, [ {
		name : "id",
		value : "personalNumber"
	} ]);

	var trEmail = createElement(table, "tr");
	var tdEmailOutput = createElement(trEmail, "td");
	createElement(tdEmailOutput, "output", "Е-mail:");
	var tdEmailInput = createElement(trEmail, "td");
	createElement(tdEmailInput, "input", null, null, [ {
		name : "id",
		value : "email"
	} ]);

	var trRoles = createElement(table, "tr");
	var tdRoleOutput = createElement(trRoles, "td");
	createElement(tdRoleOutput, "output", "Роля: ");
	var tdRoleSelect = createElement(trRoles, "td");
	createElement(tdRoleSelect, "select", null, null, [ {
		name : "id",
		value : "roles"
	} ], expand);

	loadRoles();

	createElement(mainDiv, "button", "Запази", function() {
		console.log(document.getElementById("roles").value)
	});
}

function loadRoles() {
	var select = document.getElementById("roles");

	select.appendChild(loadRolesHelper(" ", 0));
	select.appendChild(loadRolesHelper("Живущ", 1));
	select.appendChild(loadRolesHelper("Домакин", 2));
	select.appendChild(loadRolesHelper("Касиер", 3));
	select.appendChild(loadRolesHelper("Администратор", 4));
}

function loadRolesHelper(roleName, roleId) {
	var option = document.createElement("option");
	option.text = roleName;
	option.value = roleId;

	return option;
}

function expand() {
	var role = document.getElementById("roles").value;

	if (role === "0") {

	} else if (role === "1") {
		insertInhabitantHelper();
	} else if (role === "2") {
		insertHostHelper();
	} else if (role === "3") {
		insertCashierHelper();
	} else {
		insertAdministratorHelper();
	}
	// var blockId = localStorage.getItem("blockId");
	//
	// createAjaxRequest(function(responseText) {
	// var hostsJSON = JSON.parse(responseText);
	//
	// var hostsSelect = document.getElementById("hosts");
	//
	// // check if exists, otherwise complete the div
	// if (hostsSelect) {
	// updateDivCreateNightTax(hostsJSON);
	// } else {
	// completeDivCreateNightTax(hostsJSON);
	// }
	//
	// }, "GET", "/sos-portal/hosts?roomId=" + roomId + "&blockId=" + blockId);
}

function insertInhabitantHelper() {
	loadBlocks();
}

function loadBlocks() {
	var tableInsertUser = document.getElementById("tableInsertUser");

	if (document.getElementById("blocks")) {
		if (document.getElementById("rooms")) {
			var trRoom = document.getElementById("trRoom");
			
			tableInsertUser.removeChild(trRoom);
		}
		return;
	}

	var trBlock = createElement(tableInsertUser, "tr");
	var tdBlockOutput = createElement(trBlock, "td");
	createElement(tdBlockOutput, "output", "Блок: ");
	var tdBlockSelect = createElement(trBlock, "td");
	createElement(tdBlockSelect, "select", null, null, [ {
		name : "id",
		value : "blocks"
	} ], function() {

		if (document.getElementById("rooms")) {
			document.getElementById("rooms").innerHTML = "";
		} else {
			var trRoom = createElement(tableInsertUser, "tr", null, null, [ {
				name : "id",
				value : "trRoom"
			} ]);
			var tdRoomOutput = createElement(trRoom, "td");
			createElement(tdRoomOutput, "output", "Стая: ");
			var tdRoomSelect = createElement(trRoom, "td");
			createElement(tdRoomSelect, "select", null, null, [ {
				name : "id",
				value : "rooms"
			} ]);
		}

		loadRoomsByBlock(this.options[this.selectedIndex].value);
	});

	createAjaxRequest(function(responseText) {
		var blocks = JSON.parse(responseText);
		var select = document.getElementById("blocks");
		var option = document.createElement("option");
		option.text = "№";
		option.value = "№";
		select.appendChild(option);
		for (i = 0; i < blocks.length; i++) {
			var option = document.createElement("option");
			option.text = blocks[i].number;
			option.value = blocks[i].id;
			select.appendChild(option);
		}
	}, "GET", "/sos-portal/blocks");
}

function insertHostHelper() {
	loadBlocks();

	console.log("host")
}

function insertCashierHelper() {
	console.log("cashier");
}

function insertAdministratorHelper() {
	console.log("administrator");
}

// INSERT INHABITANT
function createDivInsertInhabitant() {
	document.title = "Записване на живущ";

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", "Записване на живущ");

	var table = createElement(mainDiv, "table", null, null, [ {
		name : "id",
		value : "tableInsertInhabitant"
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

	var trEmail = createElement(table, "tr");
	var tdEmailOutput = createElement(trEmail, "td");
	createElement(tdEmailOutput, "output", "Е-mail:");
	var tdEmailInput = createElement(trEmail, "td");
	createElement(tdEmailInput, "input", null, null, [ {
		name : "id",
		value : "email"
	} ]);

	var trRoom = createElement(table, "tr");
	var tdRoomOutput = createElement(trRoom, "td");
	createElement(tdRoomOutput, "output", "Стая: ");
	var tdRoomSelect = createElement(trRoom, "td");
	createElement(tdRoomSelect, "select", null, null, [ {
		name : "id",
		value : "rooms"
	} ]);

	loadRoomsByBlock();

	createElement(mainDiv, "button", "Запази", insertInhabitant);
}

function insertInhabitant() {

	var inhabited = document.getElementById("inhabited").value;
	var personalNumber = document.getElementById("personalNumber").value;
	var email = document.getElementById("email").value;
	var rooms = document.getElementById("rooms");
	var roomId = rooms.options[rooms.selectedIndex].value;

	var data = JSON.stringify({
		"username" : inhabited,
		"personalNumber" : personalNumber,
		"email" : email,
		"roomId" : roomId,
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
			createDivInsertInhabitant();
		}
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
		name : "id",
		value : "tableCreateNightTax"
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

function loadRoomsByBlock(blockId) {

	var currentBlockId;

	// administrator chooses the block
	if (blockId) {
		currentBlockId = blockId;
	} else {
		// host
		currentBlockId = localStorage.getItem("blockId");
	}

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
	}, "GET", "/sos-portal/rooms?blockId=" + currentBlockId);
}

function expandHosts() {
	var roomId = document.getElementById("rooms").value;
	var blockId = localStorage.getItem("blockId");

	createAjaxRequest(function(responseText) {
		var hostsJSON = JSON.parse(responseText);

		var hostsSelect = document.getElementById("hosts");

		// check if exists, otherwise complete the div
		if (hostsSelect) {
			updateDivCreateNightTax(hostsJSON);
		} else {
			completeDivCreateNightTax(hostsJSON);
		}

	}, "GET", "/sos-portal/hosts?roomId=" + roomId + "&blockId=" + blockId);
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
function createDivPeopleWithNightTaxes(role) {

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	if (role === "ROLE_INHABITED") {
		document.title = "Моите нощувки";

		createElement(mainDiv, "h1", "Моите нощувки", null, [ {
			name : "class",
			value : "centerText"
		} ]);
	} else {
		document.title = "Хора с нощувки";

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
	}

	var table = createElement(mainDiv, "table", null, null, [ {
		name : "id",
		value : "peopleWithNightTaxesTable"
	}, {
		name : "class",
		value : "modified"
	} ]);

	var thead = createElement(table, "thead");

	var tr = createElement(thead, "tr");

	createElement(tr, "th", "Имена");

	createElement(tr, "th", "ЕГН");

	createElement(tr, "th", "Стая");

	if (role === "ROLE_CASHIER" || role === "ROLE_ADMINISTRATOR") {
		createElement(tr, "th", "Блок");
	}

	createElement(tr, "th", "Неплатени");

	createElement(tr, "th", "Платени");

	createElement(table, "tbody");

	createElement(mainDiv, "div", null, null, [ {
		name : "id",
		value : "paginator"
	}, {
		name : "class",
		value : "pagination"
	} ]);

	if (role === "ROLE_INHABITED") {
		fetchPeopleWithNightTaxes(null, localStorage.getItem("userId"), 1);
	} else if (role === "ROLE_HOST") {
		fetchPeopleWithNightTaxes(localStorage.getItem("blockId"), null, 1);
	} else {
		fetchPeopleWithNightTaxes(null, null, 1);
	}
}

function fetchPeopleWithNightTaxes(blockId, userId, pageNumber) {

	if (userId) {
		createAjaxRequest(
				function(responseText) {

					if (responseText === undefined) {

						showNoAvailable();

					} else {
						var tbody = document
								.getElementById("peopleWithNightTaxesTable").tBodies[0];
						tbody.innerHTML = "";

						var tr = fillInPeopleTableRow(JSON.parse(responseText));

						tbody.appendChild(tr);

						peopleWithNightTaxesTable.appendChild(tbody);
					}

				}, "GET", "/sos-portal/userNightTaxes?userId=" + userId);
	} else if (blockId) {
		createAjaxRequest(function(responseText) {
			var people = JSON.parse(responseText);

			if (people.length === 0) {

				showNoAvailable();

			} else {
				fillInPeopleTable(people);

				fetchPeopleWithNightTaxesAboutPagination(blockId, pageNumber);
			}

		}, "GET", "/sos-portal/usersNightTaxesAboutBlock?blockId=" + blockId
				+ "&pageNumber=" + pageNumber);
	} else {
		createAjaxRequest(function(responseText) {
			var people = JSON.parse(responseText);

			if (people.length === 0) {

				showNoAvailable();

			} else {
				fillInPeopleTable(people);

				fetchPeopleWithNightTaxesAboutPagination(null, pageNumber);
			}

		}, "GET", "/sos-portal/allUsersNightTaxes?pageNumber=" + pageNumber);
	}

}

function fetchPeopleWithNightTaxesAboutPagination(blockId, pageNumber) {

	var url;

	if (blockId) {
		url = "/sos-portal/usersNightTaxesAboutBlockCount?blockId=" + blockId
				+ "&pageNumber=" + pageNumber;
	} else {
		url = "/sos-portal/allUsersNightTaxesCount?pageNumber=" + pageNumber;
	}

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
	}, "GET", url);

}

function createAHrefForPeople(blockId, pageNumber, innerHTML, active) {
	var a = document.createElement("a");

	a.onclick = function() {
		fetchPeopleWithNightTaxes(blockId, null, pageNumber);
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
		var tr = fillInPeopleTableRow(people[index]);

		tbody.appendChild(tr);
	}

	peopleWithNightTaxesTable.appendChild(tbody);
}

function fillInPeopleTableRow(person) {
	var tr = document.createElement("tr");

	var tdUsername = document.createElement("td");
	var usernameText = document.createTextNode(person.username);
	tdUsername.appendChild(usernameText);
	tr.appendChild(tdUsername);

	var tdPersonalNumber = document.createElement("td");
	var personalNumberText = document.createTextNode(person.personalNumber);
	tdPersonalNumber.appendChild(personalNumberText);
	tr.appendChild(tdPersonalNumber);

	var tdRoom = document.createElement("td");
	var roomText = document.createTextNode(person.roomNumber);
	tdRoom.appendChild(roomText);
	tr.appendChild(tdRoom);

	if (localStorage.getItem("role") === "ROLE_CASHIER"
			|| localStorage.getItem("role") === "ROLE_ADMINISTRATOR") {
		var tdBlock = document.createElement("td");
		tdBlock.appendChild(document.createTextNode(person.blockNumber));
		tr.appendChild(tdBlock);
	}

	var tdUnpaid = document.createElement("td");
	tdUnpaid.appendChild(createButtonNightTaxesStatus(person.id, "неплатени",
			"UNPAID"));
	tr.appendChild(tdUnpaid);

	var tdPaid = document.createElement("td");
	tdPaid.appendChild(createButtonNightTaxesStatus(person.id, "платени",
			"PAID"));
	tr.appendChild(tdPaid);

	return tr;
}

function searchPeople() {
	var marker = document.getElementById("searchTxt").value;

	var blockId = localStorage.getItem("blockId");

	createAjaxRequest(function(responseText) {
		var nightTaxes = JSON.parse(responseText);

		fillInPeopleTable(nightTaxes);

		fetchPeopleWithNightTaxesAboutPagination(blockId, 1);
	}, "GET", "/sos-portal/search?marker=" + marker + "&blockId=" + blockId
			+ "&pageNumber=" + 1);
}

function createButtonNightTaxesStatus(userId, innerHTML, status) {

	var button = createElement(null, "button", innerHTML, function() {
		document.getElementById("mainDiv").innerHTML = "";

		createDivNightTaxesPerPerson(status);

		fetchNightTaxesPerPerson(userId, status, 1);
	});

	return button;
}

// NIGHT TAXES PER PERSON
function createDivNightTaxesPerPerson(status) {

	var txtTitleAndH1;

	if (status === "UNPAID") {
		txtTitleAndH1 = "Неплатени нощувки";
	} else {
		txtTitleAndH1 = "Платени нощувки";
	}

	document.title = txtTitleAndH1;

	var mainDiv = document.getElementById("mainDiv");
	mainDiv.innerHTML = "";

	createElement(mainDiv, "h1", txtTitleAndH1);

	var table = createElement(mainDiv, "table", null, null, [ {
		name : "id",
		value : "nightTaxesTable"
	}, {
		name : "class",
		value : "modified"
	} ]);

	var thead = createElement(table, "thead");

	// TODO if role - block

	var tr = createElement(thead, "tr");

	var thRoom = createElement(tr, "th", "Стая");

	var thStatus = createElement(tr, "th", "Статус");

	var thInhabitant = createElement(tr, "th", "Живущ");

	var thGuest = createElement(tr, "th", "Гост");

	var thDate = createElement(tr, "th", "Дата");

	var thCreatedBy = createElement(tr, "th", "Вписана от");

	var thDateCreated = createElement(tr, "th", "Дата на създаване");

	// TODO if role and datePaid - datePaid

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

	createAjaxRequest(
			function(responseText) {
				var nightTaxes = JSON.parse(responseText);

				if (nightTaxes.length === 0) {

					showNoAvailable();

				} else {

					fillInNightTaxesPerPersonTable(nightTaxes);

					fetchNightTaxesPerPersonAboutPagination(userId, status,
							pageNumber);
				}

			}, "GET", "/sos-portal/nightTaxesPerUser?userId=" + userId
					+ "&status=" + status + "&pageNumber=" + pageNumber);

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
			div.appendChild(createAHrefForNightTaxes(userId, status,
					paginatorData.active, paginatorData.active, true));
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

		if (localStorage.getItem("role") === "ROLE_CASHIER"
				|| localStorage.getItem("role") === "ROLE_ADMINISTRATOR") {
			var tdBlock = document.createElement("td");
			var blockText = document
					.createTextNode(nightTaxes[index].blockNumber);
			tdBlock.appendChild(blockText);
			tr.appendChild(tdBlock);
		}

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

		// TODO: datePaid if role and datePaid != null
		if (localStorage.getItem("role") === "ROLE_CASHIER"
				|| localStorage.getItem("role") === "ROLE_ADMINISTRATOR") {
			var tdBlock = document.createElement("td");
			var blockText = document
					.createTextNode(nightTaxes[index].blockNumber);
			tdBlock.appendChild(blockText);
			tr.appendChild(tdBlock);
		}

		tbody.appendChild(tr);
	}

	nightTaxesTable.appendChild(tbody);
}
