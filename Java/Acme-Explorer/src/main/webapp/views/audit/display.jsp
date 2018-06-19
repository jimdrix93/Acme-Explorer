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


<h2>
	<spring:message code="audit.title" var="auditTitle" />
	<jstl:out value="${auditTitle}: ${audit.title}" />
</h2>

<p>
	<spring:message code="audit.description" var="auditDescription" />
	<jstl:out value="${auditDescription}: ${audit.description}" />
</p>

<p>
	<spring:message code="audit.attachments" />: <br />
	
	<jstl:forEach items="${audit.attachments}" var="attachment">
		<a href="${attachment}">${attachment}</a><br />
	</jstl:forEach>
	
</p>
	
	<input type="button" name="cancel"
		value="<spring:message code="audit.back" />"
		onclick="javascript: relativeRedir('audit/list.do?tripId=${audit.trip.id}');" />
	