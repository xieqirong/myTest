package com.itheima01;

public class GoodsItem {
	//��Ʒid
	private String id;
	//����
	private String name;
	//�۸�
	private double price;
	//�Ƽ۵�λ
	private String unit;
	//��¼��������
	private int buyCount;
	
	
	
	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public GoodsItem(String id, String name, double price, String unit, int buyCount) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.unit = unit;
		this.buyCount = buyCount;
	}

	public GoodsItem() {
	}

	public GoodsItem(String id, String name, double price, String unit) {
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
