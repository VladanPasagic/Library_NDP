package org.unibl.etf.mdp.library.responses;

public class Response {
	protected boolean success;
	protected String message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Response(boolean success, String message) {
		super();
		this.message = message;
		this.success = success;
	}

	public Response() {

	}

}
