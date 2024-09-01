package org.unibl.etf.mdp.library.entities;

import java.util.List;
import java.util.UUID;

public class ReceiptEntity {
	private UUID id;
	private List<ReceiptItemEntity> receiptItems;
	private double price;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<ReceiptItemEntity> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<ReceiptItemEntity> receiptItems) {
		this.receiptItems = receiptItems;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ReceiptEntity() {
		super();
	}

	public ReceiptEntity(List<ReceiptItemEntity> items, double price) {
		super();
		this.id = UUID.randomUUID();
		this.price = price;
		this.receiptItems = items;
	}

}
