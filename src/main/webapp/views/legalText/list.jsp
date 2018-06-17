
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<h4>
	<spring:message code="legalText.draft"/>
</h4>


<display:table class="displaytag" name="legalTextsDraft" id="row">
	<display:column>
		<div>
			<a href="legaltext/administrator/edit.do?legalTextId=${row.id}"> 
				<spring:message	code="legalText.edit" />
			</a>
		</div>
	</display:column>
	<spring:message code="legalText.title" var="legalTextTitle" />
	<display:column property="title" title="${legalTextTitle}"  />	
	
	<spring:message code="legalText.body" var="legalTextBody" />
	<display:column property="body" title="${legalTextBody}"  />	
	
	<spring:message code="legalText.numberApLaws" var="legalTextNumberApLaws" />
	<display:column property="numberApLaws" title="${legalTextNumberApLaws}"  />	
	
	<spring:message code="legalText.moment" var="legalTextMoment" />
	<display:column property="moment" title="${legalTextMoment}" format="${displaydateformat}"  />	
	
	<spring:message code="legalText.finalMode" var="legalTextFinalMode" />
	<display:column title="${legalTextFinalMode}">
		<jstl:if test="${row.finalMode == true }">
			<spring:message code="legalText.yes" />
		</jstl:if>
		<jstl:if test="${row.finalMode == false }">
			<spring:message code="legalText.no"/>
		</jstl:if>
	</display:column>

</display:table>
<br/>

<div>
	<a href="legaltext/administrator/create.do"> 
	<spring:message	code="legalText.create" />
	</a>
</div>
<br/>

<h4>
	<spring:message code="legalText.final"/>
</h4>

<display:table class="displaytag" name="legalTextsFinalMode" id="row">
	<spring:message code="legalText.title" var="legalTextTitle" />
	<display:column property="title" title="${legalTextTitle}"  />	
	
	<spring:message code="legalText.body" var="legalTextBody" />
	<display:column property="body" title="${legalTextBody}"  />	
	
	<spring:message code="legalText.numberApLaws" var="legalTextNumberApLaws" />
	<display:column property="numberApLaws" title="${legalTextNumberApLaws}"  />	
	
	<spring:message code="legalText.moment" var="legalTextMoment" />
	<!-- Falta el format -->
	<display:column property="moment" title="${legalTextMoment}" format="${displaydatetimeformat}"  />	
	
	<spring:message code="legalText.finalMode" var="legalTextFinalMode" />
	<display:column title="${legalTextFinalMode}">
		<jstl:if test="${row.finalMode == true }">
			<spring:message code="legalText.yes" />
		</jstl:if>
		<jstl:if test="${row.finalMode == false }">
			<spring:message code="legalText.no"/>
		</jstl:if>
	</display:column>
	
</display:table>