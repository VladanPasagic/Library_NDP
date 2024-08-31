package org.unibl.etf.mdp.library.entities;

import java.io.Serializable;
import java.util.UUID;

public class DistributorEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7599731718333308575L;
	private UUID id;
	private String name;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DistributorEntity(String name) {
		super();
		this.id = UUID.randomUUID();
		this.name = name;
	}

	public DistributorEntity(UUID id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public DistributorEntity() {
		super();
	}

	@Override
	public String toString() {
		return name;
	}

}
