package com.fdmgroup.OneDayProjectWK.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users_table")
public class User {

	@Id
	@GeneratedValue
	private Integer userId;
	
	private String firstName;
	
	private String lastName;
	
	private double totalPrice;
	
	//a single user can have multiple coupons associated with them
	@OneToMany
	List<Coupon> listofCoupons;

	public User() {
		super();
		
	}

	public User(String firstName, String lastName, double totalPrice, List<Coupon> listofCoupons) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.totalPrice = totalPrice;
		this.listofCoupons = listofCoupons;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<Coupon> getListofCoupons() {
		return listofCoupons;
	}

	public void setListofCoupons(List<Coupon> listofCoupons) {
		this.listofCoupons = listofCoupons;
	}

	public Integer getUserId() {
		return userId;
	}

	
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", totalPrice="
				+ totalPrice + ", listofCoupons=" + listofCoupons + "]";
	}
	
	
	
	
	
}
