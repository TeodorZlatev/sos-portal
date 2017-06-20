package bg.tu.sofia.services;

import bg.tu.sofia.utils.StructuredResponse;

public interface AuthenticationService {
	public StructuredResponse authenticate(String personalNumber, String password);
}
