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


<jstl:if test="${stages.size() == 0 }">
	<h2>
		<spring:message code="trip.no.stages" />
	</h2>
	<input type="button" name="createStage" 
		value='<spring:message code="master.page.manager.stage.create"/>'
		onclick="javascript: relativeRedir('stage/manager/create.do');" />
	<br />	
</jstl:if>
<jstl:if test="${stages.size() != 0 }">
	<form:form action="trip/manager/edit.do" modelAttribute="trip">
	
		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="notes" />
		<form:hidden path="audits" />
		<form:hidden path="manager" />
		<form:hidden path="applications" />
		<form:hidden path="stories" />
		<form:hidden path="hasValues" />
		<form:hidden path="survivalClasses" />
		
		<form:label path="ticker">
			<spring:message code="trip.ticker" />
		</form:label>
		<form:input path="ticker" readonly="true"/>
		<form:errors cssClass="error" path="ticker" />
		<br />
	
		<form:label path="title">
			<spring:message code="trip.title" />
		</form:label>
		<form:input path="title" />
		<form:errors cssClass="error" path="title" />
		<br />
	
		<form:label path="description">
			<spring:message code="trip.description" />
		</form:label>
		<form:textarea path="description" />
		<form:errors cssClass="error" path="description" />
		<br />
	
		<form:label path="requirements">
			<spring:message code="trip.requirements" />
		</form:label>
		<form:input path="requirements" />
		<form:errors cssClass="error" path="requirements" />
		<br />
	
		<form:label path="publicationDate">
			<spring:message code="trip.publicationDate" />
		</form:label>
		<form:input path="publicationDate" placeholder="yyyy/MM/dd HH:mm" />
		<form:errors cssClass="error" path="publicationDate" />
		<br />
	
		<form:label path="startTrip">
			<spring:message code="trip.startDate" />
		</form:label>
		<form:input path="startTrip" placeholder="yyyy/MM/dd HH:mm" />
		<form:errors cssClass="error" path="startTrip" />
		<br />
	
		<form:label path="endTrip">
			<spring:message code="trip.endDate" />
		</form:label>
		<form:input path="endTrip" placeholder="yyyy/MM/dd HH:mm" />
		<form:errors cssClass="error" path="endTrip" />
		<br />
	
		<form:label path="price" disabled="true">
			<spring:message code="trip.price" />
		</form:label>
		<fmt:formatNumber value="${trip.price}" pattern="${formpriceformat}" var="formattedPrice" />
		<form:input path="price" disabled="true"
			placeholder="${formpriceformat}" value="${formattedPrice}" />
		<spring:message code="currency_symbol" />
		<form:errors cssClass="error" path="price" />
		<br />
		<br />
		
		<form:label path="ranger">
			<spring:message code="trip.ranger.register" />
		</form:label>
		<form:select id="rangerId" path="ranger">
			<form:options items="${ranger}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors cssClass="error" path="ranger" />
		<br />
		<br />
		
	
		<form:label path="stages">
			<spring:message code="trip.stages" />
		</form:label>
		<form:select multiple="true" id="stagesId" path="stages">
			<form:options items="${stages}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="stages" />
		<br />
		<br />
	
		<form:label path="category">
			<spring:message code="trip.category" />
		</form:label>
		<form:select id="categoryId" path="category">
			<form:options items="${category}" itemValue="id" itemLabel="name" />
		</form:select>
		<form:errors cssClass="error" path="category" />
		<br />
		<br />
		
		<form:label path="legalText">
			<spring:message code="trip.legalTexts" />
		</form:label>
		<form:select id="legalTextId" path="legalText">
			<form:options items="${legalText}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="legalText" />
		<br />
		<br />
	
	<jstl:if test="${trip.id !=0 }">
			<jstl:if test="${canBeCancelated == true }">
				<form:label path="cancelated">
					<spring:message code="trip.cancelated" />
				</form:label>
				<form:checkbox path="cancelated" />
				<form:errors cssClass="error" path="cancelated" />
				<br />
			
				<form:label path="cancelationReason">
					<spring:message code="trip.cancelated.reason" />
				</form:label>
				<form:textarea path="cancelationReason" />
				<form:errors cssClass="error" path="cancelationReason" />
				<br />
			</jstl:if>
		</jstl:if>
		<jstl:if test="${stages.size() !=0 }">
			<input type="submit" name="save" value='<spring:message code="trip.save"/>' />&nbsp;
		</jstl:if>
		<jstl:if test="${trip.id !=0 }">
			<input type="submit" name="delete"
				value='<spring:message code="trip.delete"/>'
				onclick="return confirm('<spring:message code="trip.confirm.delete"/>')">&nbsp;
		</jstl:if>
		
		<input type="button" name="cancel"
			value='<spring:message code="trip.cancel"/>'
			onclick="javascript: relativeRedir('trip/manager/list.do');" />
		<br />	

	</form:form>
	
</jstl:if>