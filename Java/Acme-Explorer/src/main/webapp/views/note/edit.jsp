
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="note/edit.do" modelAttribute="note">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="trip"/>
	<form:hidden path="moment"/>
	<jstl:if test="${note.id !=0 }">
	<form:hidden path="remark"/>	
	</jstl:if>
	
	<security:authorize access="hasRole('AUDITOR')">
	<form:label path="remark">
		<spring:message code="note.remark" />:
	</form:label>
	<form:textarea path="remark" />
	<form:errors cssClass="error" path="remark" />
	<br />
	</security:authorize>

	<security:authorize access="hasRole('MANAGER')">
	<form:label path="reply">
		<spring:message code="note.reply" />:
	</form:label>
	<form:textarea path="reply" />
	<form:errors cssClass="error" path="reply" />
	<br />
	</security:authorize>

 
	<input type="submit" name="save" value="<spring:message code="note.save" />" />&nbsp; 
	
	
	<input type="button" name="cancel"
		value="<spring:message code="note.cancel" />"
		onclick="javascript:relativeRedir('trip/list.do')" />
	<br />

	

</form:form>
