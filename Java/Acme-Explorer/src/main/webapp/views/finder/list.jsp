<%--
 * list.jsp
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


<display:table class="displaytag" pagesize="${pageSize}" requestURI="finder/explorer/list.do" name="finder" id="row">

	<spring:message code="finder.search" var="finderSearch" />
	<display:column property="search" title="${finderSearch}" sortable="true" />
	
	<spring:message code="finder.maxPrice" var="finderPriceMax" />
	<display:column property="maxPrice" title="${finderPriceMax}" format="${displaypriceformat}" sortable="true" />
	
	<spring:message code="finder.minPrice" var="finderPriceMin" />
	<display:column property="minPrice" title="${finderPriceMin}" format="${displaypriceformat}" sortable="true" />
	
	<spring:message code="finder.startDate" var="finderDateMax" />
	<display:column property="minDate" title="${finderDateMax}" format="${displaydatetimeformat}" sortable="true" />
	
	<spring:message code="finder.endDate" var="finderDateMin" />
	<display:column property="maxDate" title="${finderDateMin}" format="${displaydatetimeformat}" sortable="true" />
	

	<display:column>
		<div>
			<a href="finder/explorer/edit.do?finderId=${row.id}"> <spring:message
					code="finder.edit" />
			</a>
		</div>
	</display:column>	
</display:table>


	
