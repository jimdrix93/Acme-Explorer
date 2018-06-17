
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


<form:form requestUri="broadcast/administrator/create.do" modelAttribute="myMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipient" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	<form:hidden path="copy" />
	
	<form:label path="subject">
		<spring:message code="broadcast.subject" />:
	</form:label>
	<form:input path="subject" />
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="broadcast.body" />:
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />

	<form:label path="priority">
		<spring:message code="broadcast.priority" />
	</form:label>
	<form:select id="priorityId" path="priority">
		<form:options items="${priority}" />
	</form:select>
	<form:errors cssClass="error" path="priority" />
	<br />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="broadcast.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value='<spring:message code="broadcast.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />

</form:form>
