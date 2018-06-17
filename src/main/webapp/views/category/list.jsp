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


<display:table pagesize="5" class="displayCategory" name="categories" requestURI="${requestUri}" id="row">

	<spring:message code="category.name" var="categoryName" />
	<display:column property="name" title="${categoryName}" sortable="true" />

	<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.edit" var="categoryEdit" />
	<display:column>
		<div>
			<a href="category/administrator/edit.do?categoryId=${row.id}"> <spring:message
 				code="category.edit" />
			</a>
		</div>
	</display:column>
	</security:authorize>

	<spring:message code="category.child" var="categoryChild" />
	<display:column>
		<div>
			<a href="category/listCategory.do?categoryId=${row.id}"> <spring:message
 				code="category.display" />
			</a>
		</div>
	</display:column>

	<spring:message code="category.trips" var="categoryTrips" />
	<display:column>
		<div>
			<a href="trip/listTripCategory.do?categoryId=${row.id}"> <spring:message
 				code="category.trips" />
			</a>
		</div>
	</display:column>

	</display:table>


	<security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="category/administrator/create.do"> <spring:message
 				code="category.create" />
		</a>
	</div>
	</security:authorize>
	<br />
	<input type="button" name="back"
	value='<spring:message code="category.back"/>'
	onclick="javascript: relativeRedir('/');" />