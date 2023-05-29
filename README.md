# redeem-a-coupon-online
A sub system that allows a user to redeem a coupon online

## Introduction

This project is a one-day project done during my software development training. It focuses on building a subsystem for online coupon redemption. The goal is to allow users to input their user ID and coupon ID, and then request to redeem the coupon. The application performs necessary checks to determine whether the coupon can be redeemed or displays an appropriate message explaining why it cannot be redeemed.

Users have information such as ID, first name, last name, total price, and a list of coupons that have been sent to them (possibly by post or email). Each coupon has an ID, a value in a specified currency, and a number of remaining times it can be used.

The application verifies if the coupons can be redeemed based on the number of remaining times and whether their value exceeds the user's total price. If the conditions are met, the application updates the database and displays a page confirming the successful redemption. If any of the checks fail, the application presents a page with an appropriate message.
