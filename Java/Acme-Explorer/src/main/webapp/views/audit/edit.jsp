<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="audit/auditor/edit.do" modelAttribute="audit">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="auditor"/>
	<form:hidden path="moment"/>
	<form:hidden path="trip"/>

	<form:label path="title">
		<spring:message code="audit.title" />:
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />

	<form:label path="description">
		<spring:message code="audit.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	<form:label path="attachments">
		<spring:message code="audit.attachments" />:
	</form:label>
	<form:textarea path="attachments"/>
	<form:errors cssClass="error" path="attachments" />
	<br />
	
	<form:label path="finalMode">
		<spring:message code="audit.finalMode" />:
	</form:label>
	<form:checkbox path="finalMode" />
	<form:errors cssClass="error" path="finalMode" />
	<br />


	<input type="submit" name="save" value="<spring:message code="audit.save" />" />&nbsp; 
	
	<jstl:if test="${audit.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="audit.delete" />"
			onclick="return confirm('<spring:message code="audit.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="audit.cancel" />"
		onclick="javascript: relativeRedir('audit/auditor/list.do');" />
	<br />


</form:form>