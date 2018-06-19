
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="legaltext/administrator/edit.do" modelAttribute="legalText">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="title">
		<spring:message code="legalText.title" />
	</form:label>
	<form:input path="title" />
	<form:errors cssClass="error" path="title" />
	<br />
	<br />
	
	<form:label path="body">
		<spring:message code="legalText.body" />
	</form:label>
	<form:textarea path="body" />
	<form:errors cssClass="error" path="body" />
	<br />
	<br />
	
	<form:label path="numberApLaws">
		<spring:message code="legalText.numberApLaws" />
	</form:label>
	<form:input path="numberApLaws" placeHolder="number"/>
	<form:errors cssClass="error" path="numberApLaws" />
	<br />
	<br />
	
	<form:label path="moment">
		<spring:message code="legalText.moment" />
	</form:label>
	<form:input path="moment" value="${moment}" readonly="true"/>
	<form:errors cssClass="error" path="moment" />
	<br />
	<br />
	
	<form:label path="finalMode">
		<spring:message code="legalText.finalMode" />
	</form:label>
	<form:checkbox path="finalMode" />
	<form:errors cssClass="error" path="finalMode" />
	<br />
	<br />
	
	<input type="submit" name="save" value="<spring:message code="legalText.save" />" />&nbsp; 
	<jstl:if test="${legalText.id !=0 }">
		<input type="submit" name="delete"
			value='<spring:message code="legalText.delete"/>'
			onclick="return confirm('<spring:message code="legalText.confirm.delete"/>')">&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value="<spring:message code="legalText.cancel" />"
		onclick="javascript: relativeRedir('legaltext/administrator/list.do')" />
	<br />

	

</form:form>
