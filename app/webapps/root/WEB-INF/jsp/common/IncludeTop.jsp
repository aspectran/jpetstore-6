<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--

       Copyright 2010-2016 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

--%>
<div id="Header">

<div id="Logo">
	<div id="LogoContent">
		<a href="/catalog/"><img src="/images/logo-topbar.gif" /></a>
	</div>
</div>

<div id="Menu">
	<div id="MenuContent">
		<a href="/cart/viewCart"><img align="middle" name="img_cart" src="/images/cart.gif" /></a>
		<c:if test="${not user.authenticated}">
			<img align="middle" src="/images/separator.gif" />
			<a href="/account/signonForm">Sign In</a>
			<img align="middle" src="/images/separator.gif" />
			<a href="/account/newAccountForm">Sign Up</a>
		</c:if>
		<c:if test="${user.authenticated}">
			<img align="middle" src="/images/separator.gif" />
			<a href="/order/listOrders">My Orders</a>
			<img align="middle" src="/images/separator.gif" />
			<a href="/account/editAccountForm">My Account</a>
			<img align="middle" src="/images/separator.gif" />
			<a href="/account/signoff">Sign Out</a>
		</c:if>
		<img align="middle" src="/images/separator.gif" />
		<a href="../help.html">?</a>
	</div>
</div>

<div id="Search" data-hide-for="large">
	<div id="SearchContent">
		<form action="/catalog/searchProducts">
			<input type="text" name="keyword"/>
			<button type="submit" class="button">Search</button>
		</form>
	</div>
</div>

<div id="QuickLinks">
	<a href="/catalog/categories/FISH"><img src="/images/sm_fish.gif" /></a>
	<img align="middle" src="/images/separator.gif" />
	<a href="/catalog/categories/DOGS"><img src="/images/sm_dogs.gif" /></a>
	<img align="middle" src="/images/separator.gif" />
	<a href="/catalog/categories/REPTILES"><img src="/images/sm_reptiles.gif" /></a>
	<img align="middle" src="/images/separator.gif" />
	<a href="/catalog/categories/CATS"><img src="/images/sm_cats.gif" /></a>
	<img align="middle" src="/images/separator.gif" />
	<a href="/catalog/categories/BIRDS"><img src="/images/sm_birds.gif" /></a>
</div>
</div>

<div id="Content">