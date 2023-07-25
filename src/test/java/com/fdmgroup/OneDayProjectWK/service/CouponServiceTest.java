package com.fdmgroup.OneDayProjectWK.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fdmgroup.OneDayProjectWK.OneDayProjectWkApplication;
import com.fdmgroup.OneDayProjectWK.model.Coupon;
import com.fdmgroup.OneDayProjectWK.model.RedeemResult;
import com.fdmgroup.OneDayProjectWK.model.User;
import com.fdmgroup.OneDayProjectWK.repository.CouponRepository;
import com.fdmgroup.OneDayProjectWK.repository.UserRepository;

@SpringBootTest(classes = { CouponService.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = OneDayProjectWkApplication.class)

public class CouponServiceTest {
//it is not a mock object
	@InjectMocks
	CouponService mockCouponService;

	@MockBean
	CouponRepository mockCouponRepository;

	@MockBean
	UserRepository mockUserRepository;

	@Mock
	User mockUser;
	@Mock
	Coupon mockCoupon;

	@Test
	public void deleteCouponIfExceedTimesOfUse_whenTimesOfUseIsZero() {

		User user = new User();
		Coupon coupon = new Coupon();
		coupon.setTimesOfUse(0);
		List<Coupon> listOfCoupons = new ArrayList<>();
		listOfCoupons.add(coupon);
		user.setListofCoupons(listOfCoupons);

		mockCouponService.deleteCouponIfExceedTimesOfUse(coupon, user);
		// check that the coupon was removed from the user's list of coupons
		assertTrue(user.getListofCoupons().isEmpty());

		// Verify that the coupon was deleted from the coupon repository
		verify(mockCouponRepository, times(1)).delete(coupon);
	}

	@Test
	public void deleteCouponIfExceedTimesOfUse_whenTimesOfUseIsMoreThanZero_deleteMethodOfRepositoryIsNotcalled() {

		User user = new User();
		Coupon coupon = new Coupon();
		coupon.setTimesOfUse(1);
		List<Coupon> listOfCoupons = new ArrayList<>();
		listOfCoupons.add(coupon);
		user.setListofCoupons(listOfCoupons);

		mockCouponService.deleteCouponIfExceedTimesOfUse(coupon, user);

		verify(mockCouponRepository, times(0)).delete(coupon);
	}

	@Test
	@Transactional
	public void testFindCouponById_methodOfService_calls_FindById_methododCouponRepository() {

		Coupon coupon = new Coupon();
		Optional<Coupon> optionalCoupon = Optional.of(coupon);
		when(mockCouponRepository.findById(1)).thenReturn(optionalCoupon);
		// Call the findCouponById method in CouponService
		Optional<Coupon> resultCoupon = mockCouponService.findCouponById(1);
		// Verify that the CouponRepository's findById method was called with the
		// check if correct correct argument
		when(mockCouponRepository.findById(1)).thenReturn(optionalCoupon);
		// Checking that the returned Optional<Coupon> contains the correct Coupon
		// object
		assertEquals(coupon, resultCoupon.get());
	}

	@Test
	@Transactional
	public void testgetListOfCoupons_returnsListOfCoupons() {
		User user = new User();
		Coupon coupon1 = new Coupon();
		Coupon coupon2 = new Coupon();
		List<Coupon> listOfCoupons = new ArrayList<>();

		listOfCoupons.add(coupon1);
		listOfCoupons.add(coupon2);
		user.setListofCoupons(listOfCoupons);

		when(mockUserRepository.findById(1)).thenReturn(Optional.of(user));

		List<Coupon> resultList = mockCouponService.getListOfCoupons(1);
		// Verify that the UserRepository's findById method was called with the correct
		// argument
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(user));
		// Checking the returned List<Coupon> contains the correct Coupon objects
		assertEquals(listOfCoupons, resultList);
	}

	@Test
	@Transactional
	public void testRedeemResult_redeemCoupon_method__whenCouponIsEmpty_returnsErrorMessage_Invalid_coupon_ID() {
		User user = new User();
		Coupon coupon = new Coupon();

		when(mockCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.empty());

		RedeemResult result = mockCouponService.redeemCoupon(user.getUserId(), coupon.getCouponId());
		assertFalse(result.isSuccess());
		assertEquals("Invalid coupon ID", result.getErrorMessage());
		verify(mockCouponRepository).findById(coupon.getCouponId());
	}

//the same as above
	@Test
	@Transactional
	public void testRedeemResult_redeemCoupon_when_optCouponisEmpty_returnsErrorMessage_Invalid_User_ID() {

		Integer userId = 1;
		Integer couponId = 1;
		when(mockCouponRepository.findById(couponId)).thenReturn(Optional.empty());
		RedeemResult result = mockCouponService.redeemCoupon(userId, couponId);
		// verify
		assertFalse(result.isSuccess());
		assertEquals("Invalid coupon ID", result.getErrorMessage());
		verify(mockCouponRepository).findById(couponId);
	}

	@Test
	@Transactional
	public void testRedeemResult_RedeemCouponMethod_WithInvalidUserId() {

		// arrange
		Integer userId = 123;
		Integer couponId = 456;
		Optional<User> optUser = Optional.empty();
		Optional<Coupon> optCoupon = Optional.of(new Coupon());
		// Mock the behavior of the UserRepository and CouponRepository
		when(mockUserRepository.findById(userId)).thenReturn(optUser);
		when(mockCouponRepository.findById(couponId)).thenReturn(optCoupon);
		// ACT
		// Call the method and check the result
		RedeemResult redeemResult = mockCouponService.redeemCoupon(userId, couponId);
		assertFalse(redeemResult.isSuccess());
		assertEquals("Invalid User ID", redeemResult.getErrorMessage());
		// ASSERT
		// Verify that the UserRepository/CouponRepository was called with the correct parameter
		verify(mockUserRepository).findById(userId);
		verify(mockCouponRepository).findById(couponId);
	}

	@Test
	@Transactional
	public void test_redeemCouponMethod_IfUserDoesntHaveACoupon_returnErrorMessage() {
		Integer userId = 1;
		Integer couponId = 2;
		User user = new User();
		Coupon coupon = new Coupon();
		RedeemResult expectedRedeemResult = new RedeemResult();

		expectedRedeemResult.setSuccess(false);
		expectedRedeemResult.setErrorMessage("You don't have such a coupon");

		when(mockUserRepository.findById(userId)).thenReturn(Optional.of(mockUser));
		when(mockCouponRepository.findById(couponId)).thenReturn(Optional.of(mockCoupon));
		// act
		RedeemResult actualRedeemResult = mockCouponService.redeemCoupon(userId, couponId);
		// Assert
		assertFalse(actualRedeemResult.isSuccess());
		assertEquals(actualRedeemResult.getErrorMessage(), expectedRedeemResult.getErrorMessage());

	}

//	@Test
//	@Transactional
//	public void testRedeemCouponUserDoesNotHaveCoupon() {
//	    // Create a user and coupon for testing
//	    User user = new User();
//	    user.setUserId(1);
//	    user.setTotalPrice(100);
//	    Coupon coupon = new Coupon();
//	    coupon.setCouponId(1);
//	    coupon.setValueOfCoupon(50);
//	    coupon.setTimesOfUse(1);
//
//	    // Configure mock repositories to return the user but not the coupon
//	    when(mockUserRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
//	    when(mockCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.empty());
//
//	    // Call the method being tested
//	    RedeemResult result = mockCouponService.redeemCoupon(user.getUserId(), coupon.getC());
//
//	    // Verify that the coupon was not redeemed and an error message was returned
//	    assertFalse(result.isSuccess());
//	    assertEquals("You don't have such a coupon", result.getErrorMessage());
//	    assertEquals(100, user.getTotalPrice());
//	    assertEquals(1, coupon.getTimesOfUse());
//
//	    // Verify that the user and coupon were not saved to the database
//	    verify(mockUserRepository, never()).save(user);
//	    verify(mockCouponRepository, never()).save(coupon);
//	}

	@Test
	@Transactional
	public void test_redeemCouponMethod_IfValueOfCouponIsBiggerThanTotalPrice_returnErrorMessage() {
		User user = new User();
		user.setTotalPrice(10);
		// coupon with a value greater than the user's total price
		Coupon coupon = new Coupon();
		coupon.setValueOfCoupon(20);
		List<Coupon> listOfCoupons = new ArrayList<>();
		user.setListofCoupons(listOfCoupons);
		listOfCoupons.add(coupon);
		// mock repositories to return the mock user and coupon
		when(mockUserRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
		when(mockCouponRepository.findById(coupon.getCouponId())).thenReturn(Optional.of(coupon));

		// Call the method being tested
		RedeemResult result = mockCouponService.redeemCoupon(user.getUserId(), coupon.getCouponId());
		// Verify that the coupon was not redeemed and an error message was returned
		assertFalse(result.isSuccess());
		assertEquals("The value of the coupon is bigger then total price. You can't redeem the coupon",
				result.getErrorMessage());
	}

	@Test
	public void test_redeemCoupon_IfcouponSuccessfullyRedeemed_returnConfirmation() {
		User user = new User();
		user.setTotalPrice(50);
		Coupon coupon = new Coupon();
		coupon.setValueOfCoupon(20);
		coupon.setTimesOfUse(1);
		List<Coupon> listOfCoupons = new ArrayList<>();
		user.setListofCoupons(listOfCoupons);
		listOfCoupons.add(coupon);

		// configure the mock repositories to return the user and coupon when queried
		when(mockUserRepository.findById(1)).thenReturn(Optional.of(user));
		when(mockCouponRepository.findById(1)).thenReturn(Optional.of(coupon));
		// call the redeemCoupon method to apply the coupon to the user's total price
		RedeemResult redeemResult = mockCouponService.redeemCoupon(1, 1);
		// verify that the coupon was sucssfuly aplied and the userss total price was
		// updated
		assertTrue(redeemResult.isSuccess());
		assertEquals(30.0, redeemResult.getTotalPrice(), 0.0);
		assertEquals(0, coupon.getTimesOfUse());
		// verify that the coupon and user were saved to the database
		verify(mockCouponRepository, times(1)).save(coupon);
		verify(mockUserRepository, times(1)).save(user);
	}

}
