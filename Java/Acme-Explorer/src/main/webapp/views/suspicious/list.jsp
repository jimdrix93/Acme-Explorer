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


<h2><spring:message code="suspicious.managers" /></h2>
<display:table class="displaytag" name="managers" id="row">
	
	<spring:message code="suspicious.name" var="suspiciousName" />
	<display:column property="name" title="${suspiciousName}" sortable="true" />

	<spring:message code="suspicious.surname" var="suspiciousSurname" />
	<display:column property="surname" title="${suspiciousSurname}" sortable="true" />
	
	<spring:message code="suspicious.ban.unban" var="suspiciousBanUnban" />	
	<display:column title="${suspiciousBanUnban}">
		<jstl:if test="${row.accountActivated}">
			<a href="suspicious/administrator/banManager.do?managerId=${row.id}"> 
				<spring:message code="suspicious.ban" />
			</a>
		</jstl:if>
		<jstl:if test="${!row.accountActivated}">
			<a href="suspicious/administrator/unbanManager.do?managerId=${row.id}"> 
				<spring:message code="suspicious.unban" />
			</a>
		</jstl:if>
	</display:column>

</display:table>


<h2><spring:message code="suspicious.rangers" /></h2>
<display:table class="displaytag" name="rangers" id="row">
	
	<spring:message code="suspicious.name" var="suspiciousName" />
	<display:column property="name" title="${suspiciousName}" sortable="true" />

	<spring:message code="suspicious.surname" var="suspiciousSurname" />
	<display:column property="surname" title="${suspiciousSurname}" sortable="true" />
	
	<spring:message code="suspicious.ban.unban" var="suspiciousBanUnban" />	
	<display:column title="${suspiciousBanUnban}">
		<jstl:if test="${row.accountActivated}">
			<a href="suspicious/administrator/banRanger.do?rangerId=${row.id}"> 
				<spring:message code="suspicious.ban" />
			</a>
		</jstl:if>
		<jstl:if test="${!row.accountActivated}">
			<a href="suspicious/administrator/unbanRanger.do?rangerId=${row.id}"> 
				<spring:message code="suspicious.unban" />
			</a>
		</jstl:if>
	</display:column>

</display:table>




