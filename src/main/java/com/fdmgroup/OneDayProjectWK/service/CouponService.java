package com.fdmgroup.OneDayProjectWK.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.OneDayProjectWK.model.Coupon;
import com.fdmgroup.OneDayProjectWK.model.RedeemResult;
import com.fdmgroup.OneDayProjectWK.model.User;
import com.fdmgroup.OneDayProjectWK.repository.CouponRepository;
import com.fdmgroup.OneDayProjectWK.repository.UserRepository;

@Service
public class CouponService {
	private UserRepository userRepository;
	private CouponRepository couponRepository;

	@Autowired
	public CouponService(UserRepository userRepository, CouponRepository couponRepository) {
		super();
		this.userRepository = userRepository;
		this.couponRepository = couponRepository;
	}

	@Transactional
	public Optional<Coupon> findCouponById(Integer couponId) {
		return couponRepository.findById(couponId);
	}

	@Transactional
	public void deleteCouponIfExceedTimesOfUse(Coupon coupon, User user) {
		if (coupon.getTimesOfUse() <= 0) {
			user.getListofCoupons().remove(coupon);
			couponRepository.delete(coupon);
		}
	}

	@Transactional
	public RedeemResult redeemCoupon(Integer userId, Integer couponId) {
		//using Optional to avoid a NullPointerException if the entities are not found in the database.
		Optional<User> optUser = userRepository.findById(userId);
		Optional<Coupon> optCoupon = couponRepository.findById(couponId);
		// I'm checking if the Coupon and User entities exist and are valid
		RedeemResult redeemResult = new RedeemResult();
		if (optCoupon.isEmpty()) {
			redeemResult.setSuccess(false);
			redeemResult.setErrorMessage("Invalid coupon ID");
			return redeemResult;
		}

		if (optUser.isEmpty()) {
			redeemResult.setSuccess(false);
			redeemResult.setErrorMessage("Invalid User ID");
			return redeemResult;
		}

		User user = optUser.get();
		Coupon coupon = optCoupon.get();
		// CHECKING if on the list of coupons there is not this coupon
		if (!user.getListofCoupons().contains(coupon)) {
			redeemResult.setSuccess(false);
			redeemResult.setErrorMessage("You don't have such a coupon");
			return redeemResult;
		}

		if (coupon.getValueOfCoupon() > user.getTotalPrice()) {
			redeemResult.setSuccess(false);
			redeemResult
					.setErrorMessage("The value of the coupon is bigger then total price. You can't redeem the coupon");
			return redeemResult;
		}
		// set the totalPrice= cuurent total price- minus coupon value
		user.setTotalPrice(user.getTotalPrice() - coupon.getValueOfCoupon());
		// change time of use if you've used coupon
		coupon.setTimesOfUse(coupon.getTimesOfUse() - 1);
		couponRepository.save(coupon);
		// delete Coupon form DataBase if it was fully used
		deleteCouponIfExceedTimesOfUse(coupon, user);

		userRepository.save(user);
		redeemResult.setSuccess(true);
		
		//I should do userService and  if in controller is success than
		//User user = userService.getUser(id) 
		redeemResult.setTotalPrice(user.getTotalPrice());
		redeemResult.setListOfCoupons(user.getListofCoupons());
		redeemResult.setUserId(user.getUserId());
		return redeemResult;
	}



	@Transactional
	public RedeemResult getListOfCoupons1(Integer userId) {
		Optional<User> optUser = userRepository.findById(userId);

		RedeemResult redeemResult = new RedeemResult();

		if (optUser.isEmpty()) {
			redeemResult.setSuccess(false);
			redeemResult.setErrorMessage("Invalid User ID");
			return redeemResult;
		} else {
			List<Coupon> listOfCoupons = optUser.get().getListofCoupons();
			redeemResult.setSuccess(true);
			redeemResult.setTotalPrice(optUser.get().getTotalPrice());
			redeemResult.setListOfCoupons(listOfCoupons);
			return redeemResult;
		}
	}
	@Transactional
	public List<Coupon> getListOfCoupons(Integer userId) {
		Optional<User> optUser = userRepository.findById(userId);
		User user = optUser.get();
		return user.getListofCoupons();

	}
}
