<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<script>
	function phoneRegex(form) {
		var regex = /^((\+\d{1,3})\s?(\(\d{1,3}\))?)?\s?(\d{4,})$/g;
		var phone = document.getElementById("phone").value;
		if (regex.test(phone)==false){
		    if (!confirm('The phone number is not good.Are you sure to continue?')){
		    	alert("Can not commit this operation.");
		    	return false;
		    }
		}
	}
</script>

<form:form requestURI="${requestURI}" modelAttribute="ranger" onsubmit="return phoneRegex(this)" method="post">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="folders"/>
	<form:hidden path="receivedMessages"/>
	<form:hidden path="sendedMessages"/>
	<form:hidden path="accountActivated"/>
	<form:hidden path="userAccount.authorities[0].authority"/>
	<form:hidden path="trips" />
	<form:hidden path="curriculum" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="suspicious"/>

	<form:label path="name">
		<spring:message code="ranger.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="ranger.surname" />
	</form:label>
	<form:input path="surname"/>
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="ranger.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="ranger.phone" />
	</form:label>
	<form:input path="phone" placeholder="+xx (xx) xxxxxxx"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="ranger.address" />
	</form:label>
	<form:input path="address"/>
	<form:errors cssClass="error" path="address" />
	<br />
	
	<form:label path="userAccount.username">
		<spring:message code="ranger.userAccount.username" />
	</form:label>
	<form:input path="userAccount.username" />
	<form:errors cssClass="error" path="userAccount.username" />
	<br />

	<form:label path="userAccount.password">
		<spring:message code="ranger.userAccount.password" />
	</form:label>
	<form:password path="userAccount.password" />
	<form:errors cssClass="error" path="userAccount.password" />
	<br />
	<br />
	<input type="submit" name="save"
		value='<spring:message code="ranger.save"/>' />&nbsp;
	
	<input type="button" name="cancel"
		value='<spring:message code="ranger.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
