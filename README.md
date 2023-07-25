redeem-a-coupon-online
## Introduction

This project is a one-day project done during my software development training. It focuses on building a subsystem for online coupon redemption. The goal is to allow users to input their user ID and coupon ID, and then request to redeem the coupon. The application performs necessary checks to determine whether the coupon can be redeemed or displays an appropriate message explaining why it cannot be redeemed.

Users have information such as ID, first name, last name, total price, and a list of coupons that have been sent to them (possibly by post or email). Each coupon has an ID, a value in a specified currency, and a number of remaining times it can be used.

The application verifies if the coupons can be redeemed based on the number of remaining times and whether their value exceeds the user's total price. If the conditions are met, the application updates the database and displays a page confirming the successful redemption. If any of the checks fail, the application presents a page with an appropriate message.

## Frameworks

Spring, Springboot, MVC, Hibernate

## Required Deliverables

I was required to submit the following:
-code solution that accomplishes the following tasks:
  - Lists all the coupons for each user ID stored in the database.
  - Allows users to redeem a coupon from their list of coupons by inputting their user ID and coupon ID.
  - Displays a page that confirms the transaction details for the redeemed coupon.

# Installation
1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Ensure you have Java and Maven installed.
4. Build the project using Maven: `mvn clean install`.
5. Run the application using Maven: `mvn spring-boot:run`.
6. Access the application through your web browser at http://localhost:8080.

# Usage


Go to http://localhost:8080
There are existing users with id: 101, 103, 104, 105

1. Display Coupons List:
   - Fill in the user's ID in an input field.
   - Click a button to view the list of coupons for that user.

2. Coupon Redemption:
   - Enter the user's ID and the coupon's ID in separate input fields.
   - Click a button to complete the redemption process.
