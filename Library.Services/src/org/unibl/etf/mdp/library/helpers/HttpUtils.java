package org.unibl.etf.mdp.library.helpers;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class HttpUtils {
	public static <T> T get(String url, Class<T> responseType) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON).get();
		if (response.getStatus() == 200) {
			T responseEntity = response.readEntity(responseType);
			close(response, client);
			return responseEntity;
		} else {
			close(response, client);
			return null;
		}
	}

	public static <T, G> G post(String url, T body, Class<G> responseType) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(body, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 201 || response.getStatus() == 200 || response.getStatus() == 409) {
			G responseEntity = response.readEntity(responseType);
			close(response, client);
			return responseEntity;
		} else {
			close(response, client);
			return null;
		}
	}

	public static <T> void post(String url, T body) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(body, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			close(response, client);
		} else {
			close(response, client);
		}
	}

	private static void close(Response rs, Client cl) {
		rs.close();
		cl.close();
	}

	public static boolean put(String url) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.put(Entity.entity("1", MediaType.APPLICATION_JSON));
		if (response.getStatus() == 200) {
			close(response, client);
			return true;
		} else {
			close(response, client);
			return false;
		}
	}

	public static boolean delete(String url) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON).delete();
		if (response.getStatus() == 200) {
			close(response, client);
			return true;
		} else {
			close(response, client);
			return false;
		}
	}

	public static <T> boolean multiPartForm(String url, T content) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(content, MediaType.MULTIPART_FORM_DATA));
		if (response.getStatus() == 200) {
			close(response, client);
			return true;
		} else {
			close(response, client);
			return false;
		}
	}

}
