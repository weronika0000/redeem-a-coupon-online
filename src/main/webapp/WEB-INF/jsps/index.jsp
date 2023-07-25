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

<p class="x">Hello</p>

${errorMessage} 

<h2>Display your coupons:</h2>
<form action="/displayListOfCoupons1" method="post" >
User Id:<input type="number" name="userId" placeholder="user id" required /><br>
<input type="submit" value="Display the coupons" />
</form>
<br><br>

<c:forEach var="coupon" items="${listOfCoupons}">
<b>Coupon id:</b> ${coupon.couponId}<br>
<b>Value of the coupon: </b>${coupon.valueOfCoupon}&nbsp; ${coupon.currency}<br>
<b>Remaining times of use: </b>${coupon.timesOfUse} <br>
<form action="/redeemCoupon" method="post" >
<input type="hidden" name="userId" value="${userId}" />
<input type="hidden" name="couponId" value="${coupon.couponId}" />
<input type="submit"  value="Redeem the coupon" /><br>
</form><br>
</c:forEach>
<!-- form to input data -->

<h2>Redeem you coupon: </h2>
<form action="/redeemCoupon" method="post" >
User Id:<input type="number" name="userId" placeholder="user id" required /><br>
Coupon Id:<input type="number" name="couponId" placeholder="coupon id" required /><br>
<input type="submit" value="Redeem the coupon" />
</form>

</body>
</html>