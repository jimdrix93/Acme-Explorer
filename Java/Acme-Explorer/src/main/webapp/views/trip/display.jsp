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


<h2>
	<spring:message code="trip.title" var="tripTitle" />
	<jstl:out value="${tripTitle}: ${trip.title }" />
</h2>
<p>
	<spring:message code="trip.ticker" var="tripTicker" />
	<jstl:out value="${tripTicker}: ${trip.ticker}" />
</p>

<p>
	<spring:message code="trip.description" var="tripDescription" />
	<jstl:out value="${tripDescription}: ${trip.description }" />
</p>
<p>
	<spring:message code="trip.price" var="tripPrice" />
	<fmt:formatNumber value="${trip.price}" pattern="${formpriceformat}" var="formattedPrice" />
	<jstl:out value="${tripPrice }: ${formattedPrice }" />
	<spring:message code="currency_symbol"/>
</p>
<p>
	<spring:message code="trip.requirements" var="tripRequirements" />
	<jstl:out value="${tripRequirements }: ${trip.requirements }" />
</p>
<p>
	<spring:message code="trip.publicationDate" var="tripPublicationDate" />
	<fmt:formatDate value="${trip.publicationDate }" pattern="${formdatetimeformat}" var="publicationDate"/>
	<jstl:out value="${tripPublicationDate }: ${publicationDate }" />
</p>
<p>
	<spring:message code="trip.startDate" var="tripStartTrip" />
	<fmt:formatDate value="${trip.startTrip }" pattern="${formdatetimeformat}" var="startTrip"/>
	<jstl:out value="${tripStartTrip }: ${startTrip }" />
</p>
<p>
	<spring:message code="trip.endDate" var="tripEndTrip" />
	<fmt:formatDate value="${trip.endTrip }" pattern="${formdatetimeformat}" var="endTrip"/>
	<jstl:out value=" ${tripEndTrip }: ${endTrip }" />
</p> 
<p>
	<spring:message code="trip.category" var="tripCategory" />
	<jstl:out value="${tripCategory}: ${trip.category.name }" />
</p>
<p>
	<spring:message code="trip.ranger" var="tripRanger" />
	<jstl:out value="${tripRanger}: ${trip.ranger.name }" />
</p>
<!-- <h4> -->
<%-- 	<spring:message code="trip.notes" /> --%>
<!-- </h4> -->

<%-- <display:table name="notes" class="displaytag"> --%>

<%-- 	<spring:message code="note.remark" var="noteRemark" /> --%>
<%-- 	<display:column property="remark" title="${noteRemark}" /> --%>

<%-- 	<spring:message code="note.moment" var="noteMoment" /> --%>
<%-- 	<display:column property="moment" title="${noteMoment}" format="${displaydatetimeformat}"/> --%>

<%-- 	<spring:message code="note.reply" var="noteReply" /> --%>
<%-- 	<display:column property="reply" title="${noteReply}" /> --%>

<%-- 	<spring:message code="note.replyDate" var="noteReplyDate" /> --%>
<%-- 	<display:column property="replyDate" title="${noteReplyDate}" /> --%>
<%-- </display:table> --%>

<!-- <h4> -->
<%-- 	<spring:message code="trip.audits" /> --%>
<!-- </h4> -->

<%-- <display:table name="audits" class="displaytag"> --%>

<%-- 	<spring:message code="audit.moment" var="auditMoment" /> --%>
<%-- 	<display:column property="moment" title="${auditMoment}" format="${displaydatetimeformat}"/> --%>

<%-- 	<spring:message code="audit.title" var="auditTitle" /> --%>
<%-- 	<display:column property="title" title="${auditTitle}" /> --%>

<%-- 	<spring:message code="audit.description" var="auditDescription" /> --%>
<%-- 	<display:column property="description" title="${auditDescription}" /> --%>

<%-- 	<spring:message code="audit.attachments" var="auditAttachments" /> --%>
<%-- 	<display:column property="attachments" title="${auditAttachments}" /> --%>
<%-- </display:table> --%>


<h4>
	<spring:message code="trip.stages" />
</h4>

<display:table name="stages" class="displaytag">
	<spring:message code="stage.title" var="stageTitle" />
	<display:column property="title" title="${stageTitle}" />

	<spring:message code="stage.description" var="stageDescription" />
	<display:column property="description" title="${stageDescription}" />

	<spring:message code="stage.price" var="stagePrice" />
	<display:column property="price" title="${stagePrice}" format="${displaypriceformat}" />
</display:table>

<!-- <h4> -->
<%-- 	<spring:message code="trip.survivalClasses" /> --%>
<!-- </h4> -->

<%-- <display:table name="survivalClasses" class="displaytag"> --%>
<%-- 	<spring:message code="survivalClass.title" var="survivalClassTitle" /> --%>
<%-- 	<display:column property="title" title="${survivalClassTitle}" /> --%>

<%-- 	<spring:message code="survivalClass.description" var="survivalClassDescription" /> --%>
<%-- 	<display:column property="description" title="${survivalClassDescription}" /> --%>

<%-- 	<spring:message code="survivalClass.moment" var="survivalClassMoment" /> --%>
<%-- 	<display:column property="moment" title="${survivalClassMoment}" format="${displaydatetimeformat}"/> --%>
	
<%-- 	<spring:message code="survivalClass.location.name" var="survivalClassLocationName" /> --%>
<%-- 	<display:column property="location.name" title="${survivalClassLocationName}" /> --%>
<%-- </display:table> --%>


<!-- <h4> -->
<%-- 	<spring:message code="trip.stories" /> --%>
<!-- </h4> -->

<%-- <display:table name="stories" class="displaytag"> --%>
<%-- 	<spring:message code="story.title" var="storyTitle" /> --%>
<%-- 	<display:column property="title" title="${storyTitle}" /> --%>

<%-- 	<spring:message code="story.text" var="storyText" /> --%>
<%-- 	<display:column property="text" title="${storyText}" /> --%>

<%-- 	<spring:message code="story.attachments" var="storyAttachments" /> --%>
<%-- 	<display:column property="attachments" title="${storyAttachments}" /> --%>
<%-- </display:table> --%>

<h4>
	<spring:message code="trip.hasValues" />
</h4>

<display:table name="hasValues"  class="displaytag">

	<spring:message code="hasValue.tag.name" var="hasValueTag" />
	<display:column property="tag.name" title="${hasValueTag}" />
	
	<spring:message code="hasValue.value" var="hasValueValue" />
	<display:column property="value" title="${hasValueValue}" />
		
</display:table>
<br>
<br>

<input type="button" name="back"
	value='<spring:message code="trip.back"/>'
	onclick="javascript: relativeRedir('trip/list.do');" />
	<br />


