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


<form:form action="administrator/edit.do" modelAttribute="administrator">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="accountActivated" />
	<form:hidden path="folders" />
	<form:hidden path="receivedMessages" />
	<form:hidden path="sendedMessages" />
	<form:hidden path="userAccount" />
	<form:hidden path="socialIdentities" />

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

<%-- 	<form:label path="userAccount.username"> --%>
<%-- 		<spring:message code="actor.userAccount.username" /> --%>
<%-- 	</form:label> --%>
<%-- 	<form:input path="userAccount.username" /> --%>
<%-- 	<form:errors cssClass="error" path="userAccount.username" /> --%>
<!-- 	<br /> -->

<%-- 	<form:label path="userAccount.password"> --%>
<%-- 		<spring:message code="actor.userAccount.password" /> --%>
<%-- 	</form:label> --%>
<%-- 	<form:password path="userAccount.password" /> --%>
<%-- 	<form:errors cssClass="error" path="userAccount.password" /> --%>
<!-- 	<br /> -->

	<form:label path="email">
		<spring:message code="actor.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="actor.phone" />
	</form:label>
	<form:input path="phone" placeholder="+xx (xx) xxxxxxx" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<form:label path="address">
		<spring:message code="actor.address" />
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" />
	<br />
	<br />
	
	<input type="submit" name="save"
		value='<spring:message code="actor.save"/>' />&nbsp;
	
	<jstl:if test="${actor.id !=0 }">
		<input type="submit" name="delete"
			value='<spring:message code="actor.delete"/>'
			onclick="return confirm('<spring:message code="actor.confirm.delete"/>')">&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value='<spring:message code="actor.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
