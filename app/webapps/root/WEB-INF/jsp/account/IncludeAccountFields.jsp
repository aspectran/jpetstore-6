<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<h3>Account Information</h3>

<table>
	<tr>
		<td>First name:</td>
		<td><input type="text" name="firstName" value="${user.account.firstName}"/></td>
	</tr>
	<tr>
		<td>Last name:</td>
		<td><input type="text" name="lastName" value="${user.account.lastName}"/></td>
	</tr>
	<tr>
		<td>Email:</td>
		<td><input type="text" name="email" value="${user.account.email}"/></td>
	</tr>
	<tr>
		<td>Phone:</td>
		<td><input type="text" name="phone" value="${user.account.phone}"/></td>
	</tr>
	<tr>
		<td>Address 1:</td>
		<td><input type="text" name="address1" value="${user.account.address1}"/></td>
	</tr>
	<tr>
		<td>Address 2:</td>
		<td><input type="text" name="address2" value="${user.account.address2}"/></td>
	</tr>
	<tr>
		<td>City:</td>
		<td><input type="text" name="city" value="${user.account.city}"/></td>
	</tr>
	<tr>
		<td>State:</td>
		<td><input type="text" name="state" value="${user.account.state}"/></td>
	</tr>
	<tr>
		<td>Zip:</td>
		<td><input type="text" name="zip" value="${user.account.zip}"/></td>
	</tr>
	<tr>
		<td>Country:</td>
		<td><input type="text" name="country" value="${user.account.country}"/></td>
	</tr>
</table>

<h3>Profile Information</h3>

<table>
	<tr>
		<td>Language Preference:</td>
		<td>
			<select name="languagePreference">
				<c:forEach items="${staticCodes.languages}" var="item">
					<option value="${item.key}"<c:if test="${user.account.languagePreference eq item.key}"> selected</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>Favourite Category:</td>
		<td>
			<select name="favouriteCategoryId">
				<c:forEach items="${staticCodes.categories}" var="item">
					<option value="${item.key}"<c:if test="${user.account.favouriteCategoryId eq item.key}"> selected</c:if>>${item.value}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>Enable MyList</td>
		<td>
			<input type="checkbox" name="listOption" value="true"<c:if test="${user.account.listOption}"> checked</c:if>/>
		</td>
	</tr>
	<tr>
		<td>Enable MyBanner</td>
		<td>
			<input type="checkbox" name="bannerOption" value="true"<c:if test="${user.account.bannerOption}"> checked</c:if>/>
		</td>
	</tr>
</table>
