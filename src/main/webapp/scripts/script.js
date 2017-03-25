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