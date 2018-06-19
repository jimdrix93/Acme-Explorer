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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
 
 <display:table id="row" class="displaytag"	name="audits" requestURI="${requestUri}">
	
	<spring:message code="audit.title" var="auditTitle" />
	<display:column property="title" title="${auditTitle}" sortable="true"/>
	
	<spring:message code="audit.description" var="descriptionAudit"/>
	<display:column property="description" title="${descriptionAudit}" sortable="true"/>

	<spring:message code="audit.moment" var="momentAudit"/>
	<display:column property="moment" title="${momentAudit}" format="${displaydatetimeformat}" sortable="true"/>
	
	<spring:message code="audit.display" var="displayAudit"/>
	<display:column title="${displayAudit}">
	<div>
		<a href="audit/display.do?auditId=${row.id}"><spring:message code="audit.display" />
		</a>
	</div>
	</display:column>
	
	<security:authorize access="hasRole('AUDITOR')">	
	<spring:message code="audit.edit" var="editAudit"/>
	<display:column title="${editAudit}">
		<jstl:if test="${row.finalMode}">
			<spring:message code="audit.finalMode" />
		</jstl:if>
		<jstl:if test="${!row.finalMode}">
			<div>
				<a href="audit/auditor/edit.do?auditId=${row.id}"> ${editAudit}</a>
			</div>
		</jstl:if>
	</display:column>
	</security:authorize>
	
</display:table>


		