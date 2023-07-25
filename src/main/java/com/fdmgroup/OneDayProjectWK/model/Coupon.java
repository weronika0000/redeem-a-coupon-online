package com.fdmgroup.OneDayProjectWK.model;

import java.util.Currency;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "coupons_table")
public class Coupon {

	@Id
	@GeneratedValue
	private Integer couponId;
	
	private double valueOfCoupon;
	
	private int timesOfUse;
	
	private Currency currency;
	
	public Coupon() {
		super();
		
	}

	public double getValueOfCoupon() {
		return valueOfCoupon;
	}

	public void setValueOfCoupon(double valueOfCoupon) {
		this.valueOfCoupon = valueOfCoupon;
	}

	public int getTimesOfUse() {
		return timesOfUse;
	}

	public void setTimesOfUse(int timesOfUse) {
		this.timesOfUse = timesOfUse;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Integer getCouponId() {
		return couponId;
	}

	public void setCouponId(Integer couponId) {
		this.couponId = couponId;
	}

	
	
	
	
}
