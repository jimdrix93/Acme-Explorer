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


<display:table pagesize="5" class="displaytag" name="trips" requestURI="${requestURI}" id="row">
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${actorId == row.manager.id && row.startTrip > currentDate}">
				<a href="trip/manager/edit.do?tripId=${row.id}"> 
					<spring:message code="trip.edit" />
				</a>
			</jstl:if>
		</display:column>
	</security:authorize>
	
	<spring:message code="trip.ticker" var="tripTicker" />
	<display:column property="ticker" title="${tripTicker}" sortable="true" />

	<spring:message code="trip.title" var="tripTitle" />
	<display:column property="title" title="${tripTitle}" sortable="true" />

	<spring:message code="trip.price" var="priceHeader" />
	<display:column property="price" title="${priceHeader}"  format="${displaypriceformat}" sortable="true" />
	
	<spring:message code="trip.startDate" var="tripStartDate" />
	<display:column property="startTrip" title="${tripStartDate}" format="${displaydatetimeformat}" sortable="true" />
	
	<spring:message code="trip.endDate" var="tripEndDate" />
	<display:column property="endTrip" title="${tripEndDate}" format="${displaydatetimeformat}" sortable="true" />
	
	<display:column>
		<a href="trip/display.do?tripId=${row.id}"> 
			<spring:message code="trip.display" />
		</a>
	</display:column>
	
<%-- 	<display:column> --%>
<!-- 		<div> -->
<%-- 			<a href="curriculum/display.do?curriculumId=${row.ranger.curriculum.id}">  --%>
<%-- 				<spring:message code="trip.ranger.display" /> --%>
<!-- 			</a> -->
<!-- 		</div> -->
<%-- 	</display:column> --%>
	
<%-- 	<display:column> --%>
<%-- 		<a href="ranger/display.do?rangerId=${row.ranger.id}">  --%>
<%-- 			<spring:message code="trip.ranger.display" /> --%>
<!-- 		</a> -->
		
<%-- 	</display:column> --%>
<%-- 	<security:authorize access=""> --%>
<%-- 		<display:column> --%>
<%-- 			<a href="audit/list.do?tripId=${row.id}">  --%>
<%-- 				<spring:message code="trip.audits" /> --%>
<!-- 			</a> -->
<%-- 		</display:column> --%>
<%-- 	</security:authorize> --%>
	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<jstl:if test="${row.startTrip > currentDate}">
				<a href="application/explorer/create.do?tripId=${row.id}"> 
					<spring:message	code="trip.applyFor" /> 
				</a>
			</jstl:if>
		</display:column>
	
<%-- 		<display:column> --%>
<%-- 			<a href="survivalClass/explorer/list.do?tripId=${row.id}">  --%>
<%-- 				<spring:message code="trip.survivalClasses" />  --%>
<!-- 			</a> -->
<%-- 		</display:column> --%>
	</security:authorize>
	
	<security:authorize access="hasRole('AUDITOR')">
 		<display:column> 
 			<a href="note/create.do?tripId=${row.id}">  
 				<spring:message	code="trip.createNote" />  
 			</a> 
		</display:column> 
		
 		<display:column> 
 			<a href="audit/auditor/create.do?tripId=${row.id}">  
 				<spring:message	code="trip.createAudit" />  
 			</a> 
		</display:column> 
	</security:authorize> 
	
	<display:column> 
		<a href="audit/list.do?tripId=${row.id}">  
			<spring:message	code="trip.audits" />  
		</a> 
	</display:column> 
	
	<security:authorize access="hasRole('MANAGER')">
 		<display:column> 
 			<jstl:if test="${actorId == row.manager.id}"> 
 				<a href="note/listByTrip.do?tripId=${row.id}">  
					<spring:message	code="trip.notes" /> 
				</a> 
			</jstl:if> 
		</display:column> 
		
		<display:column>
			<jstl:if test="${actorId == row.manager.id}">
				<a href="application/manager/list.do?tripId=${row.id}" 
					onclick="<jstl:set var='messages' value='true'/>"> 
						<spring:message	code="trip.applications" />
				</a>
			</jstl:if>
		</display:column>
		
<%-- 		<display:column> --%>
<%-- 			<jstl:if test="${actorId == row.manager.id}"> --%>
<%-- 				<a href="survivalClass/manager/list.do?tripId=${row.id}" --%>
<%-- 					onclick="<jstl:set var='messages' value='true'/>">  --%>
<%-- 					<spring:message	code="trip.survivalClasses" /> --%>
<!-- 				</a> -->
<%-- 			</jstl:if> --%>
<%-- 		</display:column> --%>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="trip/manager/create.do"> 
			<spring:message code="trip.create" />
		</a>
	</div>
</security:authorize>


