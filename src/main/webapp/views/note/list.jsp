
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


<display:table name="notes" id="row" pagesize="5" keepStatus="true"
	requestURI="note/manager/list" class="displaytag">

	<spring:message code="note.remark" var="noteRemark" />
	<display:column property="remark" sortable="true" title="${noteRemark}" />

	<spring:message code="note.moment" var="noteMoment" />
	<display:column property="moment" sortable="true" title="${noteMoment}" 
		format="{0,date,${noteDateFormat}}"/>

	<spring:message code="note.reply" var="noteReply" />
	<display:column property="reply" sortable="true" title="${noteReply}" />

	<spring:message code="note.replyDate" var="noteReplyDate" />
	<display:column property="replyDate" sortable="true"
		title="${noteReplyDate}" />
	
	<security:authorize access="hasRole('MANAGER')">
	<jstl:if test="${managerId==row.trip.manager.id}">
		<display:column>
			<div>
			
				<a href="note/manager/edit.do?noteId=${row.id}"> <spring:message
						code="note.replyNote" /> 
				</a>
			
			</div>
		</display:column>
	</jstl:if>
	</security:authorize>

</display:table>