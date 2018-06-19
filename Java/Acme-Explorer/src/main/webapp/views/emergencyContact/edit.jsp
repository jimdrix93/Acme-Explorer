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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<form:form action="emergencyContact/explorer/edit.do" modelAttribute="emergencyContact">
	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="name">
		<spring:message code="emergencyContact.name" />
	</form:label>
	<form:input path="name" />
	<form:errors cssClass="error" path="name" />
	<br />

	<form:label path="email">
		<spring:message code="emergencyContact.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />

	<form:label path="phone">
		<spring:message code="emergencyContact.phone" />
	</form:label>
	<form:input path="phone" placeholder="+xx (xx) xxxxxxx" />
	<form:errors cssClass="error" path="phone" />
	<br />

	<input type="submit" name="save"
		value='<spring:message code="emergencyContact.save"/>' />&nbsp;
	
	<jstl:if test="${emergencyContact.id !=0 }">
		<input type="submit" name="delete"
			value='<spring:message code="emergencyContact.delete"/>'
			onclick="return confirm('<spring:message code="emergencyContact.confirm.delete"/>')">&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value='<spring:message code="emergencyContact.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
