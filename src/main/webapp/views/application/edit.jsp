
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="${requestUri}" modelAttribute="application">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trip" />
	<form:hidden path="applicant" />

	<div class="${application.color}">
	
		<form:label path="moment">
			<spring:message code="application.moment" />:
		</form:label>
		<fmt:formatDate value="${application.moment}" pattern="${formdatetimeformat}" var="formattedMoment" />
		<security:authorize access="hasRole('EXPLORER')">
			<form:input path="moment"  />  <!-- value="${formattedMoment}" -->
			<form:errors cssClass="error" path="moment" />
		</security:authorize>
		<security:authorize access="hasRole('MANAGER')">
			<form:input path="moment" readonly="true"/>
		</security:authorize>
		<br />
	
	
		<form:label path="status">
			<spring:message code="application.status" />:
		</form:label>
		<form:select path="status" id="statusId">
	 		<form:options items="${possibleStatus}"  /> 
		</form:select>
		<form:errors cssClass="error" path="status" />
		<br />
	
		<form:label path="comments">
			<spring:message code="application.comments" />:
		</form:label>
		<security:authorize access="hasRole('EXPLORER')">
			<form:textarea path="comments" />
		</security:authorize>
		<security:authorize access="hasRole('MANAGER')">
			<form:textarea path="comments" readonly="true"/>
		</security:authorize>
		<form:errors cssClass="error" path="comments" />
	

		<security:authorize access="hasRole('MANAGER')">
			<h3>
				<spring:message code="application.rejectedReason" />
			</h3>

			<form:label path="rejectedReason">
				<spring:message code="application.rejectedReason" />:
			</form:label>
			<form:textarea path="rejectedReason" />
			<form:errors cssClass="error" path="rejectedReason" />
			<br />
		</security:authorize>

	</div>

	<input type="submit" name="save"
		value="<spring:message code="application.save" />" />&nbsp; 
		
		
	<security:authorize access="hasRole('EXPLORER')">
		<input type="button" name="cancel"
			value="<spring:message code="application.cancel" />"
			onclick="javascript: relativeRedir('application/explorer/list.do');" />	
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<input type="button" name="cancel"
			value="<spring:message code="application.cancel" />"
			onclick="javascript: relativeRedir('application/manager/list.do');" />	
	</security:authorize>
	<br />


</form:form>
