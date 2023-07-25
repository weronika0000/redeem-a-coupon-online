package com.fdmgroup.OneDayProjectWK.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;

import com.fdmgroup.OneDayProjectWK.OneDayProjectWkApplication;
import com.fdmgroup.OneDayProjectWK.model.Coupon;
import com.fdmgroup.OneDayProjectWK.model.RedeemResult;
import com.fdmgroup.OneDayProjectWK.model.User;
import com.fdmgroup.OneDayProjectWK.repository.UserRepository;
import com.fdmgroup.OneDayProjectWK.service.CouponService;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(classes = { CouponControllerTest.class })
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = OneDayProjectWkApplication.class)
public class CouponControllerTest {
	
	@MockBean
	CouponService mockCouponService;
	
	@MockBean
	UserRepository mockUserRepository;
	
	@Mock
	User mockUser;
	
	@Mock
	Coupon mockCoupon;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void test_redeemCouponMethod_ifRedeemResultisSuccess_returnConfirmation_andAddAttributes() throws Exception{
		
		 	int userId = 12;
	        int couponId = 22;
	        RedeemResult redeemResult = new RedeemResult();
	        redeemResult.setSuccess(true);
	        redeemResult.setTotalPrice(100.0);
	        when(mockCouponService.redeemCoupon(userId, couponId)).thenReturn(redeemResult);

	        mockMvc.perform(post("/redeemCoupon")
	                .param("userId", String.valueOf(userId))
	                .param("couponId", String.valueOf(couponId)))
	                .andExpect(status().isOk())
	                .andExpect(view().name("confirmation"))
	                .andExpect(model().attribute("totalPrice", 100.0));
	    }	
		
	
	
    @Test
    public void test_redeemCoupon_ReturnIndexPage_andAddAttribute_ErrorMessage_WhenRedeemResultFails_() throws Exception {
        int userId = 1;
        int couponId = 2;
        RedeemResult redeemResult = new RedeemResult();
        redeemResult.setSuccess(false);
        redeemResult.setErrorMessage("Invalid coupon ID");
        when(mockCouponService.redeemCoupon(userId, couponId)).thenReturn(redeemResult);

        mockMvc.perform(post("/redeemCoupon")
                .param("userId", String.valueOf(userId))
                .param("couponId", String.valueOf(couponId)))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("errorMessage", "Invalid coupon ID"));
    }
    
    
//    @Test
//    public void testDisplayListOfCoupons() throws Exception {
//        
//        Integer userId = 1;
//        List<Coupon> listOfcoupons = Arrays.asList(new Coupon());
//        when(mockCouponService.getListOfCoupons(userId)).thenReturn(listOfcoupons);
//
//        
//        mockMvc.perform(post("/displayListOfCoupons")
//                .param("userId", "1"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("index"))
//                .andExpect(model().attribute("userId", userId))
//                .andExpect(model().attribute("listOfCoupons", listOfcoupons));
//
//        verify(mockCouponService).getListOfCoupons(userId);
//    }

    @Test
    void testDisplayListOfCoupons1Success() throws Exception {
        // Set up test data
        User user = new User();
    	//int userId = 0;
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(new Coupon());
        user.setListofCoupons(coupons);
        user.setUserId(1);
        RedeemResult redeemResult = new RedeemResult(true, null, 0.0, coupons, user.getUserId());
        
        // Set up mock behavior for CouponService
        when(mockCouponService.getListOfCoupons1(user.getUserId())).thenReturn(redeemResult);
        
        mockMvc.perform(post("/displayListOfCoupons1")
                .param("userId", "1"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("userId", user.getUserId()))
                .andExpect(model().attribute("listOfCoupons", coupons));
       //verify(mockCouponService).getListOfCoupons(userId);
    }
    @Test
    void testDisplayListOfCoupons1Fail() throws Exception {
    	int invalidUserId = 100;
        String errorMessage = "Invalid User ID";
        RedeemResult redeemResult = new RedeemResult(false, errorMessage, 0.0, null, invalidUserId);
        
       
        when(mockCouponService.getListOfCoupons1(invalidUserId)).thenReturn(redeemResult);
        
        mockMvc.perform(post("/displayListOfCoupons1")
                .param("userId", "100"))
                .andExpect(view().name("index"))
                .andExpect(model().attribute("errorMessage", errorMessage));
    }
      
    
    

}


	
	

	
	

