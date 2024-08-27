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

	public static <T> boolean post(String url, T body, Class<T> responseType) {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);
		Response response = target.request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(body, MediaType.APPLICATION_JSON));
		if (response.getStatus() == 201 || response.getStatus() == 200) {
			T responseEntity = response.readEntity(responseType);
			close(response, client);
			return true;
		} else {
			close(response, client);
			return false;
		}
	}

	private static void close(Response rs, Client cl) {
		rs.close();
		cl.close();
	}

}