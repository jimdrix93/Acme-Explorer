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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="folder/message/moving.do" modelAttribute="folder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="childs" />
	<form:hidden path="name" />
	<form:hidden path="folderType" />
	
	<form:label path="messages">
		<spring:message code="messages" />
	</form:label>
	<form:select multiple="false" id="messageId" path="messages" >
		<form:options items="${messages}" itemValue="id" itemLabel="subject"/>
	</form:select>
	<form:errors cssClass="error" path="messages" />
	<br />

	<input type="submit" name="move"
		value="<spring:message code="folder.message.move" />" />&nbsp; 
		
</form:form>
	
	

	
