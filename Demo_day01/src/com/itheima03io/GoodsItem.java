package com.itheima03io;
/*
 * 定义一个超市商品类
 * */
public class GoodsItem {

	private String id;
	private String name;
	private double price;
	private int buyCount;
	private String unit;
	
	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public GoodsItem(String id, String name, double price, int buyCount, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.buyCount = buyCount;
		this.unit = unit;
	}

	public GoodsItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GoodsItem(String id, String name, double price, String unit) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	
}
