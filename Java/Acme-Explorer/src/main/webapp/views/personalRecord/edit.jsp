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

<form:form action="personalRecord/ranger/edit.do" modelAttribute="personalRecord" >
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="fullName">
		<spring:message code="personalRecord.fullName" />
	</form:label>
	<form:input path="fullName" />
	<form:errors cssClass="error" path="fullName" />
	<br />
	
	<form:label path="photo">
		<spring:message code="personalRecord.photo" />
	</form:label>
	<form:input path="photo" />
	<form:errors cssClass="error" path="photo" />
	<br />
	
	<form:label path="email">
		<spring:message code="personalRecord.email" />
	</form:label>
	<form:input path="email" />
	<form:errors cssClass="error" path="email" />
	<br />
	
	<form:label path="phone">
		<spring:message code="personalRecord.phone" />
	</form:label>
	<form:input path="phone" placeholder="+xx (xx) xxxxxxx"/>
	<form:errors cssClass="error" path="phone" />
	<br />
	
	<form:label path="linkedInUrl">
		<spring:message code="personalRecord.linkedInUrl" />
	</form:label>
	<form:input path="linkedInUrl" />
	<form:errors cssClass="error" path="linkedInUrl" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="personalRecord.save" />" />&nbsp; 
		
	<jstl:if test="${personalRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="personalRecord.delete" />"
			onclick="return confirm('<spring:message code="personalRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value='<spring:message code="personalRecord.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
