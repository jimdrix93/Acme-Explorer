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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="trip/search.do" modelAttribute="finder">

	<form:hidden path="trips"/>
	
	<form:label path="search">
		<spring:message code="finder.search" />:
	</form:label>
	<form:input path="search" />
	<form:errors cssClass="error" path="search" />
	<br />

	<security:authorize access="hasRole('EXPLORER')">
			
		<form:label path="priceMax">
			<spring:message code="finder.priceMax" />:
		</form:label>
		<fmt:formatNumber value="${finder.price}" pattern="${formpriceformat}" var="formattedPrice"/>
		<form:input path="priceMax" placeholder="${formattedPrice}"/>
		<form:errors cssClass="error" path="priceMax" />
		<br />
	
		<form:label path="priceMin">
			<spring:message code="finder.priceMin" />:
		</form:label>
		<fmt:formatNumber value="${finder.price}" pattern="${formpriceformat}" var="formattedPrice"/>
		<form:input path="priceMin" placeholder="${formattedPrice}"/>
		<form:errors cssClass="error" path="priceMin" />
		<br />
	
		<form:label path="startDate">
	 		<spring:message code="finder.startDate"/>:
	 	</form:label>
		<fmt:formatDate value="${finder.startTrip}" pattern="${formdateformat}" var="formattedMoment"/>
		<form:input path="startDate" value="${formattedMoment}" />
		<form:errors cssClass="error" path="startDate" /> 
		<br />
	
		<form:label path="endDate">
	 		<spring:message code="finder.endDate"/>:
	 	</form:label>
		<fmt:formatDate value="${finder.endTrip}" pattern="${formdateformat}" var="formattedMoment"/>
		<form:input path="endDate" value="${formattedMoment}" />
		<form:errors cssClass="error" path="endDate" /> 
		<br />

	</security:authorize>

	<input type="submit" name="searchButton" value="<spring:message code="finder.search" />" />
	<br />

</form:form>