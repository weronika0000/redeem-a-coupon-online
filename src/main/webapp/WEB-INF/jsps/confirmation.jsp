<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>  
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Index</title>
<c:url value="/css/style.css" var="jstlCss" />
<link href="${jstlCss}" rel="stylesheet">
</head>
<body>
<h1>Confirmation</h1>
<h2>Your coupon redeemed successfully.</h2> <br>

Now your total price is:<b> ${totalPrice}</b> USD <br><br><br>

Your remaining coupons:<br>
<c:forEach var="coupon" items="${listOfCoupons}">
<b>Coupon id:</b> ${coupon.couponId}<br>
<b>Value of the coupon: </b>${coupon.valueOfCoupon}&nbsp; ${coupon.currency}<br>
<b>Remaining times of use: </b>${coupon.timesOfUse} 
<form action="/redeemCoupon" method="post" >
<input type="hidden" name="userId" value="${userId}" />
<input type="hidden" name="couponId" value="${coupon.couponId}" />
<input type="submit"  value="Redeem the coupon" />
</form>
<br>
</c:forEach>

</body>
</html>