package org.unibl.etf.mdp.library.entities;

import java.util.List;

public class ReceiptEntity {
	private int id;
	private List<ReceiptItemEntity> receiptItems;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<ReceiptItemEntity> getReceiptItems() {
		return receiptItems;
	}

	public void setReceiptItems(List<ReceiptItemEntity> receiptItems) {
		this.receiptItems = receiptItems;
	}

}
