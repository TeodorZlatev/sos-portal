package bg.tu.sofia.utils;

public class StructuredResponse {
	public enum RESPONSE_STATUS {
		SUCCESS, FAIL
	}

	private int code;
	private RESPONSE_STATUS responseStatus;
	private String message;
	private String token;

	public StructuredResponse(int code, RESPONSE_STATUS responseStatus) {
		super();
		this.code = code;
		this.responseStatus = responseStatus;
	}

	public StructuredResponse(int code, RESPONSE_STATUS responseStatus, String token, String message) {
		super();
		this.code = code;
		this.responseStatus = responseStatus;
		this.token = token;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public RESPONSE_STATUS getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(RESPONSE_STATUS responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
