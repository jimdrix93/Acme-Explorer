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
	
	<form:label path="company">
		<spring:message code="professionalRecord.company" />
	</form:label>
	<form:input path="company" />
	<form:errors cssClass="error" path="company" />
	<br />
	
	<form:label path="startDate">
		<spring:message code="professionalRecord.startDate" />
	</form:label>
	<form:input path="startDate" />
	<form:errors cssClass="error" path="startDate" />
	<br />
	
	<form:label path="endDate">
		<spring:message code="professionalRecord.endDate" />
	</form:label>
	<form:input path="endDate" />
	<form:errors cssClass="error" path="endDate" />
	<br />
	
	<form:label path="role">
		<spring:message code="professionalRecord.role" />
	</form:label>
	<form:input path="role" />
	<form:errors cssClass="error" path="role" />
	<br />
	
	<form:label path="attachment">
		<spring:message code="professionalRecord.attachment" />
	</form:label>
	<form:input path="attachment" />
	<form:errors cssClass="error" path="attachment" />
	<br />
	
	<form:label path="comments">
		<spring:message code="professionalRecord.comments" />
	</form:label>
	<form:input path="comments" />
	<form:errors cssClass="error" path="comments" />
	<br />
	
	<input type="submit" name="save"
		value="<spring:message code="professionalRecord.save" />" />&nbsp; 
		
	<jstl:if test="${professionalRecord.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="professionalRecord.delete" />"
			onclick="return confirm('<spring:message code="professionalRecord.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value='<spring:message code="professionalRecord.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
