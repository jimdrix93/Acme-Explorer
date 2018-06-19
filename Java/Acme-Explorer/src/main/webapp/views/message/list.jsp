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

<p><spring:message code="messages" />

<display:table pagesize="5" class="displaytag" name="messages" requestURI="/folder/message/list.do" id="row">
		<spring:message code="message.priority" var="messagePriority" />
		<display:column property="priority" title="${messagePriority}" sortable="true" />
		
		<spring:message code="message.moment" var="messageMoment" />
		<display:column property="moment" title="${messageMoment}" sortable="true" />
	
		<spring:message code="message.subject" var="messageSubject" />
		<display:column property="subject" title="${messageSubject}" sortable="true" />
		
		<spring:message code="message.body" var="messageBody" />
		<display:column property="body" title="${messageBody}" sortable="true" />
		
		<spring:message code="message.recipient" var="messageRecipient" />
		<display:column property="recipient.name" title="${messageRecipient}" sortable="true" />
		
		<spring:message code="message.sender" var="messageSender" />
		<display:column property="sender.name" title="${messageSender}" sortable="true" />
		
		<display:column>
			<div>
				<a href="message/edit.do?messageId=${row.id}"> <spring:message
						code="message.delete" />
				</a>
			</div>
		</display:column>
		

</display:table>