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
 
 <display:table id="row" class="displaytag"	name="auditRecord" requestURI="audit/list.do">
	
	<spring:message code="audit.title" var="auditTitle" />
	<display:column property="title" title="${auditTitle}" sortable="true"/>
	
	<spring:message code="audit.description" var="descriptionAudit"/>
	<display:column property="description" title="${descriptionAudit}" sortable="true"/>

	<spring:message code="audit.moment" var="momentAudit"/>
	<display:column property="moment" title="${momentAudit}" sortable="true"/>
	
	<security:authorize access="hasRole('AUDITOR')">
	
		<spring:message code="audit.finalMode" var="finalModeAudit"/>
		<display:column property="Final Mode" title="${finalModeAudit}" sortable="true"/>
		
	</security:authorize>
	
	<display:column>
	<jstl:choose>
		<jstl:when test="${audit.finalMode == false}">
		<div>
			<a href="audit/display.do?auditId=${row.id}"><spring:message code="audit.display" />
			</a>
		</div>
		</jstl:when>
		
		<jstl:otherwise>
			<div><spring:message code="audit.finalMode.final"/></div>
		</jstl:otherwise>
		
	</jstl:choose>
	</display:column>
	
	
	<security:authorize access="hasRole('AUDITOR')">
	
	<spring:message code="audit.attachments" var="attAudit"/>
	<display:column property="attachments" title="${attAudit}" sortable="true"/>
	
	<spring:message code="audit.edit" var="editAudit"/>
	<display:column>
		<div>
			<a href="audit/auditor/edit.do?auditId=${row.id}"> ${editAudit}</a>
		</div>
	</display:column>
	</security:authorize>
	
	</display:table>
	
	<security:authorize access="hasRole('AUDITOR')">
		<div>
			<a href="audit/auditor/create.do"> <spring:message code="audit.create" /></a>
		</div>
	</security:authorize>