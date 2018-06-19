
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


<display:table name="emergencyContacts" id="row" pagesize="5" keepStatus="true"
	requestURI="${requestUri}" class="displaytag">

	<spring:message code="emergencyContact.name" var="emergencyContactName" />
	<display:column property="name" sortable="true" title="${emergencyContactName}" />
	
	<spring:message code="emergencyContact.email" var="emergencyContactEmail" />
	<display:column property="email" sortable="true" title="${emergencyContactEmail}" />
	
	<spring:message code="emergencyContact.phone" var="emergencyContactPhone" />
	<display:column property="phone" sortable="true" title="${emergencyContactPhone}" />
	
	<display:column>
		<div>
			<a href="emergencyContact/explorer/edit.do?emergencyContactId=${row.id}"> 
				<spring:message code="emergencyContact.edit" />
			</a>
		</div>
	</display:column>

</display:table>

	<div>
		<a href="emergencyContact/explorer/create.do"> 
			<spring:message code="emergencyContact.create" />
		</a>
	</div>