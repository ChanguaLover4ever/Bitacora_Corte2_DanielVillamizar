package com.restaurant.model.domain;

import java.math.BigDecimal;
import java.util.List;

public class Order {

	private String id;
	private String tableId;
	private String notes;
	private List<String> dishIds;
	private String status;
	private BigDecimal total;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getDishIds() {
		return dishIds;
	}

	public void setDishIds(List<String> dishIds) {
		this.dishIds = dishIds;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
}
