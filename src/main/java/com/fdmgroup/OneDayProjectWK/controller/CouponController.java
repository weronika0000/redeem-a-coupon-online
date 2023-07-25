package com.fdmgroup.OneDayProjectWK.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.OneDayProjectWK.model.Coupon;
import com.fdmgroup.OneDayProjectWK.model.RedeemResult;
import com.fdmgroup.OneDayProjectWK.model.User;
import com.fdmgroup.OneDayProjectWK.repository.CouponRepository;
import com.fdmgroup.OneDayProjectWK.repository.UserRepository;
import com.fdmgroup.OneDayProjectWK.service.CouponService;


@Controller
public class CouponController {

	@Autowired
	private CouponService couponService;
	
	

	// kontroler ma model map
	@PostMapping(value = "/redeemCoupon")
	public String redeemCoupon(ModelMap model, @RequestParam Integer userId, @RequestParam Integer couponId) {

		RedeemResult redeemResult = couponService.redeemCoupon(userId, couponId);
		// jezeli sie udalo
		
		if (redeemResult.isSuccess()) {
			model.addAttribute("totalPrice", redeemResult.getTotalPrice());
			model.addAttribute("listOfCoupons", redeemResult.getListOfCoupons());
			model.addAttribute("userId", redeemResult.getUserId());
			return "confirmation";

		} else {
			model.addAttribute("errorMessage", redeemResult.getErrorMessage());
			return "index";
		}
		
	}
	
	@PostMapping(value = "/displayListOfCoupons1")
	public String displayListOfCoupons1(ModelMap model, @RequestParam Integer userId) {
		RedeemResult redeemResult = couponService.getListOfCoupons1(userId);
		//List<Coupon> listOfCoupons = couponService.getListOfCoupons(userId);
		
		if (redeemResult.isSuccess()) {
		model.addAttribute("userId", userId);
		model.addAttribute("listOfCoupons", redeemResult.getListOfCoupons());
		return "index";
		}else {
			model.addAttribute("errorMessage", redeemResult.getErrorMessage());
			return "index";
		}

	}
	
//
//	@PostMapping(value = "/displayListOfCoupons")
//	public String displayListOfCoupons(ModelMap model, @RequestParam Integer userId) {
//		
//		List<Coupon> listOfCoupons = couponService.getListOfCoupons(userId);
//		model.addAttribute("userId", userId);
//		model.addAttribute("listOfCoupons", listOfCoupons);
//		return "index";
//
//	}



}






//	@PostMapping(value = "/displayListOfCoupons")
//	public String displayListOfCoupons1(ModelMap model, @RequestParam Integer userId) {
//	    Optional<User> optUser = userRepository.findById(userId);
//	    if (optUser.isEmpty()) {
//	        model.addAttribute("errorMessage", "Invalid User ID");
//	        return "error";
//	    }
//	    
//	    List<Coupon> listOfCoupons = couponService.getListOfCoupons(userId);
//	    model.addAttribute("userId", userId);
//	    model.addAttribute("listOfCoupons", listOfCoupons);
//
//	    return "index";
//	}
	
	

//optUser = Optional.of(user);create a new Optional with the updated User object
//optCoupon = Optional.of(coupon);