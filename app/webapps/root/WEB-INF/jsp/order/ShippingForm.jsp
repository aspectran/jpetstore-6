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
<%@ include file="../common/IncludeTop.jsp"%>

<div id="CenterForm">

	<form method="post" action="/order/newOrder">

		<h3>Shipping Address</h3>
		<table>
			<colgroup>
				<col style="width: 25%"/>
				<col/>
			</colgroup>
			<tr>
				<td>First name:</td>
				<td><input type="text" name="shipToFirstName" value="${user.order.shipToFirstName}"/></td>
			</tr>
			<tr>
				<td>Last name:</td>
				<td><input type="text" name="shipToLastName" value="${user.order.shipToLastName}"/></td>
			</tr>
			<tr>
				<td>Address 1:</td>
				<td><input type="text" name="shipAddress1" value="${user.order.shipAddress1}"/></td>
			</tr>
			<tr>
				<td>Address 2:</td>
				<td><input type="text" name="shipAddress2" value="${user.order.shipAddress2}"/></td>
			</tr>
			<tr>
				<td>City:</td>
				<td><input type="text" name="shipCity" value="${user.order.shipCity}"/></td>
			</tr>
			<tr>
				<td>State:</td>
				<td><input type="text" name="shipState" value="${user.order.shipState}"/></td>
			</tr>
			<tr>
				<td>Zip:</td>
				<td><input type="text" name="shipZip" value="${user.order.shipZip}"/></td>
			</tr>
			<tr>
				<td>Country:</td>
				<td><input type="text" name="shipCountry" value="${user.order.shipCountry}"/></td>
			</tr>
		</table>

		<div class="button-bar">
			<button type="submit" class="button">Continue</button>
		</div>

	</form>

</div>

<%@ include file="../common/IncludeBottom.jsp"%>