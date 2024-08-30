package org.unibl.etf.mdp.library.requests;

import java.util.ArrayList;
import java.util.List;

public class MultipleBookRequest {
	private List<String> ids;

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public MultipleBookRequest()
	{
		this.ids = new ArrayList<String>();
	}

	public MultipleBookRequest(List<String> ids) {
		this.ids = ids;
	}
}
