package org.unibl.etf.mdp.library.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class OrderEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 16005234819315279L;
	private UUID id;
	private List<OrderItemEntity> items;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<OrderItemEntity> getItems() {
		return items;
	}

	public void setItems(List<OrderItemEntity> items) {
		this.items = items;
	}

	public OrderEntity(List<OrderItemEntity> items) {
		super();
		this.items = items;
		id = UUID.randomUUID();
	}

	public OrderEntity() {

	}
}
