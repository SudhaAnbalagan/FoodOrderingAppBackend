package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.service.businness.CustomerService;
import com.upgrad.FoodOrderingApp.service.businness.OrderService;
import com.upgrad.FoodOrderingApp.service.entity.CouponEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import com.upgrad.FoodOrderingApp.service.exception.AuthorizationFailedException;
import com.upgrad.FoodOrderingApp.service.exception.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class OrderController {


    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderService orderService;

    /* The method handles get Coupon By CouponName request.
     It takes authorization from the header and coupon name as the path variable.
    Produces response in CouponDetailsResponse and returns UUID,Coupon Name and Percentage of coupon present in the DB
      if error occurs, returns error code and error Message.
   */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getCouponByCouponName(@RequestHeader(value = "authorization") final String authorization, @PathVariable(value = "coupon_name") final String couponName) throws AuthorizationFailedException, CouponNotFoundException {
        String[] authParts = authorization.split("Bearer ");
        final String accessToken = authParts[1];

        CustomerEntity customerEntity = customerService.getCustomer(accessToken);

        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);

        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
                .couponName(couponEntity.getCouponName())
                .id(UUID.fromString(couponEntity.getUuid()))
                .percent(couponEntity.getPercent());

        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);


    }


}
