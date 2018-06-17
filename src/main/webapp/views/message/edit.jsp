
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


<form:form requestUri="/message/create.do" modelAttribute="myMessage">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="recipient" />
	<form:hidden path="recipient.receivedMessages" />
	<form:hidden path="recipient.sendedMessages" />
	<form:hidden path="sender.receivedMessages" />
	<form:hidden path="sender.sendedMessages" />
	<form:hidden path="recipient.folders" />
	<form:hidden path="sender.folders" />
	<form:hidden path="sender" />
	<form:hidden path="moment" />
	<form:hidden path="copy" />
	
	<form:label path="subject">
		<spring:message code="message.subject" />:
	</form:label>
	<jstl:if test="${myMessage.id==0 }">
		<form:input path="subject" />
	</jstl:if>
	<jstl:if test="${myMessage.id!=0 }">
		<form:input path="subject" readonly="true"/>
	</jstl:if>
	<form:errors cssClass="error" path="subject" />
	<br />

	<form:label path="body">
		<spring:message code="message.body" />:
	</form:label>
	<jstl:if test="${myMessage.id==0 }">
		<form:textarea path="body" />
	</jstl:if>
	<jstl:if test="${myMessage.id !=0 }">
		<form:textarea path="body" readonly="true"/>
	</jstl:if>
	<form:errors cssClass="error" path="body" />
	<br />

	<form:label path="priority">
		<spring:message code="message.priority" />
	</form:label>
	<jstl:if test="${myMessage.id == 0}">
		<form:select id="priorityId" path="priority">
			<form:options items="${priority}" />
		</form:select>
	</jstl:if>
	<jstl:if test="${myMessage.id != 0}">
		<form:input path="priority" readonly="true"/>
	</jstl:if>
	<form:errors cssClass="error" path="priority" />
	<br />
	<br />

	<jstl:if test="${myMessage.id == 0}">
		<input type="submit" name="save" value='<spring:message code="message.save"/>' />&nbsp;
	</jstl:if>
	<jstl:if test="${myMessage.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="message.delete" />"
			onclick="return confirm('<spring:message code="message.confirm.delete" />')" />&nbsp;
	</jstl:if>

	<input type="button" name="cancel"
		value='<spring:message code="message.cancel"/>'
		onclick="javascript: relativeRedir('/message/list.do');" />
	<br />

</form:form>
