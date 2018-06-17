
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="banner">
		<spring:message code="sponsorship.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br />

	<form:label path="infoPage">
		<spring:message code="sponsorship.infoPage" />:
	</form:label>
	<form:input path="infoPage" />
	<form:errors cssClass="error" path="infoPage" />
	<br />
	<br />

	<form:label path="trips">
		<spring:message code="sponsorship.trips" />:
	</form:label>
	<br />
    <form:select multiple="true" id="tripsId" path="trips">
        <form:options items="${trips}" itemValue="id" itemLabel="title" />
    </form:select>
	<form:errors cssClass="error" path="trips" />
	<br />
	<br />


 
	<input type="submit" name="save" value="<spring:message code="sponsorship.save" />" />&nbsp; 
	
	<jstl:if test="${sponsorship.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="sponsorship.delete" />"
			onclick="return confirm('<spring:message code="sponsorship.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="sponsorship.cancel" />"
		onclick="javascript: relativeRedir('/');" />
	<br />


</form:form>
