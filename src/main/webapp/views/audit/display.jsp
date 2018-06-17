<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<div style="position: relative; width: 649px; height: 448px; margin-left: auto; margin-right: auto;">

<display:table name="audits" id="row" class="displaytag">

		<spring:message code="audit.title" var="titleAudit" />
		<display:column property="title" sortable="true"
			title="${titleAudit}" />

		<spring:message code="audit.description" var="descriptionAudit" />
		<display:column property="description" sortable="true"
			title="${descriptionAudit}" />

		<spring:message code="audit.attachments" var="attAudit" />
		<display:column property="attachments" sortable="true" title="${attAudit}" />

		<spring:message code="audit.finalMode" var="finalModeAudit" />
		<display:column property="finalMode" sortable="true"
			title="${finalModeAudit}" />

</display:table>
	
</div>