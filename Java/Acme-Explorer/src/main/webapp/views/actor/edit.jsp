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
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

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

<form:form action="actor/edit.do" modelAttribute="${actorType}" onsubmit="return phoneRegex(this)" method="post">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="accountActivated" />
	<form:hidden path="folders" />
	<form:hidden path="receivedMessages" />
	<form:hidden path="sendedMessages" />
	<form:hidden path="socialIdentities" />
	<form:hidden path="userAccount" />

	<form:label path="name">
		<spring:message code="actor.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="surname">
		<spring:message code="actor.surname" />
	</form:label>
	<form:input path="surname" />
	<form:errors cssClass="error" path="surname" />
	<br />

	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>
	<form:input path="phone" placeholder="+xx (xx) xxxxxxx"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	
	<security:authorize access="hasRole('EXPLORER')">
		<form:hidden path="finders"/>
		<form:hidden path="applications"/>
		<form:hidden path="stories"/>
		<form:hidden path="emergencyContacts"/>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<form:hidden path="suspicious"/>
		<form:hidden path="trips"/>
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<form:hidden path="audits"/>
		<form:hidden path="notes"/>
	</security:authorize>
	<security:authorize access="hasRole('RANGER')">
		<form:hidden path="suspicious"/>
		<form:hidden path="trips"/>
		<form:hidden path="curriculum"/>
	</security:authorize>
	<security:authorize access="hasRole('SPONSOR')">
		<form:hidden path="sponsorships"/>
	</security:authorize>
	<br>
	
	<security:authorize access="hasRole('ADMIN')">
		<input type="submit" name="saveAdmin"
			value='<spring:message code="actor.save"/>'/>&nbsp;
	</security:authorize>
	<security:authorize access="hasRole('MANAGER')">
		<input type="submit" name="saveManager"
			value='<spring:message code="actor.save"/>' />&nbsp;
	</security:authorize>
	<security:authorize access="hasRole('EXPLORER')">
		<input type="submit" name="saveExplorer"
			value='<spring:message code="actor.save"/>' />&nbsp;
	</security:authorize>
	<security:authorize access="hasRole('RANGER')">
		<input type="submit" name="saveRanger"
			value='<spring:message code="actor.save"/>' />&nbsp;
	</security:authorize>
	<security:authorize access="hasRole('AUDITOR')">
		<input type="submit" name="saveAuditor"
			value='<spring:message code="actor.save"/>' />&nbsp;
	</security:authorize>
	
	<input type="button" name="cancel"
		value='<spring:message code="actor.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
