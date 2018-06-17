
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<form:form action="hasValue/administrator/edit.do" modelAttribute="hasValue">

<form:hidden path="id" />
<form:hidden path="version" />
<jstl:if test="${hasValue.id !=0 }">	
<form:hidden path="trip"/>
<form:hidden path="tag"/>
</jstl:if>

<form:label path="value">
	<spring:message code="hasValue.value" />:
</form:label>
<form:input path="value" />
<form:errors cssClass="error" path="value" />
<br />
<jstl:if test="${hasValue.id ==0 }">	
<form:label path="tag">
	<spring:message code="hasValue.tag" />:
</form:label>
<form:select id="tagId" path="tag">
	<form:options items="${tags}" itemValue="id" itemLabel="name" />
</form:select>
<form:errors cssClass="error" path="tag" />
<br />	
</jstl:if>

<jstl:if test="${hasValue.id ==0 }">	
<form:label path="trip">
	<spring:message code="hasValue.trip" />:
</form:label>
<form:select id="tripId" path="trip">
	<form:options items="${trips}" itemValue="id" itemLabel="title" />
</form:select>
<form:errors cssClass="error" path="trip" />
<br />	
</jstl:if>

<input type="submit" name="save" value="<spring:message code="hasValue.save" />" />&nbsp; 

<jstl:if test="${hasValue.id !=0}">
	<input type="submit" name="delete"
		value='<spring:message code="hasValue.delete"/>'
		onclick="return confirm('<spring:message code="hasValue.confirm.delete"/>')">&nbsp;
</jstl:if>

<input type="button" name="cancel"
		value='<spring:message code="hasValue.cancel"/>'
		onclick="javascript: relativeRedir('/');" />

</form:form>
