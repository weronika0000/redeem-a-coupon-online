package com.fdmgroup.OneDayProjectWK.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.OneDayProjectWK.model.Coupon;
import com.fdmgroup.OneDayProjectWK.model.User;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Integer> {

	
	
	
}
