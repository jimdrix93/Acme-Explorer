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

<form:form action="${requestURI}" modelAttribute="finder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trips" />
	<form:hidden path="moment"/>

	<form:label path="search">
		<spring:message code="finder.search" />:
	</form:label>
	<form:input path="search" />
	<form:errors cssClass="error" path="search" />
	<br />

	<security:authorize access="hasRole('EXPLORER')">

		<form:label path="minPrice">
			<spring:message code="finder.minPrice" />:
		</form:label>
		<fmt:formatNumber value="${minPrice}" pattern="${formpriceformat}" var="formattedPrice" />
		<form:input path="minPrice" value="${formattedPrice}" />
		<spring:message code="currency_symbol" />
		<form:errors cssClass="error" path="minPrice" />
		<br />
		
		<form:label path="maxPrice">
			<spring:message code="finder.maxPrice" />:
		</form:label>
		<fmt:formatNumber value="${maxPrice}" pattern="${formpriceformat}" var="formattedPrice" />
		<form:input path="maxPrice" value="${formattedPrice}" />
		<spring:message code="currency_symbol" />
		<form:errors cssClass="error" path="maxPrice" />
		<br />

		<form:label path="minDate">
			<spring:message code="finder.startDate" />:
	 	</form:label>
		<form:input path="minDate" placeholder="yyyy/MM/dd" />
		<form:errors cssClass="error" path="minDate" />
		<br />

		<form:label path="maxDate">
			<spring:message code="finder.endDate" />:
	 	</form:label>
		<form:input path="maxDate" placeholder="yyyy/MM/dd" />
		<form:errors cssClass="error" path="maxDate" />
		<br />

	</security:authorize>

	<input type="submit" name="searchButton"
		value="<spring:message code="finder.search" />" />
	<br />

</form:form>