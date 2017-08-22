package com.neusoft.hs.engine.cost;

public class ChargeItemDTO {

	private String id;

	private String code;

	private String name;

	private float price;

	private String unit;

	private String chargingMode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getChargingMode() {
		return chargingMode;
	}

	public void setChargingMode(String chargingMode) {
		this.chargingMode = chargingMode;
	}
}
