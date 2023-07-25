package com.fdmgroup.OneDayProjectWK.model;

import java.util.List;

public class RedeemResult {

	private boolean success;
	private String errorMessage;
	private double totalPrice;
	private List<Coupon> listOfCoupons;
	private Integer userId;


	public RedeemResult() {
		super();

	}

	public RedeemResult(boolean success, String errorMessage, double totalPrice, List<Coupon> listOfCoupons,
			Integer userId) {
		super();
		this.success = success;
		this.errorMessage = errorMessage;
		this.totalPrice = totalPrice;
		this.listOfCoupons = listOfCoupons;
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Coupon> getListOfCoupons() {
		return listOfCoupons;
	}

	public void setListOfCoupons(List<Coupon> listOfCoupons) {
		this.listOfCoupons = listOfCoupons;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
