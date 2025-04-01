package com.min.edu.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "ATM")
@Entity
public class ATM {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "MODEL")
	private String model;
	
	@Column(name = "STATEMENT")
	private String statement;
	
	@Column(name = "INTERPHONE")
	private String interPhone;
	
	@Column(name = "PRICE")
	private Integer price;
	
	@Column(name = "PRODUCTYEAR")
	private Integer productYear;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "boothid")
	private Booth booth;
	
	public ATM() {

	}

	public ATM(String brand, String model, String statement, String interPhone, Integer price, Integer productYear) {
		super();
		this.brand = brand;
		this.model = model;
		this.statement = statement;
		this.interPhone = interPhone;
		this.price = price;
		this.productYear = productYear;
	}
	

	public ATM(String brand, String model, String statement, String interPhone, Integer price, Integer productYear,
			Booth booth) {
		super();
		this.brand = brand;
		this.model = model;
		this.statement = statement;
		this.interPhone = interPhone;
		this.price = price;
		this.productYear = productYear;
		this.booth = booth;
	}

	public Booth getBooth() {
		return booth;
	}

	public void setBooth(Booth booth) {
		this.booth = booth;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getInterPhone() {
		return interPhone;
	}

	public void setInterPhone(String interPhone) {
		this.interPhone = interPhone;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getProductYear() {
		return productYear;
	}

	public void setProductYear(Integer productYear) {
		this.productYear = productYear;
	}

	@Override
	public String toString() {
		return "ATM [id=" + id + ", brand=" + brand + ", model=" + model + ", statement=" + statement + ", interPhone="
				+ interPhone + ", price=" + price + ", productYear=" + productYear + "]";
	}
	
	
}
