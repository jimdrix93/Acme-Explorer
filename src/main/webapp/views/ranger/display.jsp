<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<display:table class="displaytag" name="ranger" id="row">
	<spring:message code="ranger.name" var="name" />
	<display:column property="name" title="name"/>
	
	<spring:message code="ranger.surname" var="surname" />
	<display:column property="surname" title="surname"/>
	
	<spring:message code="ranger.phone" var="phone" />
	<display:column property="phone" title="phone"/>
	
<%-- 	<display:column> --%>
<!-- 		<div> -->
<%-- 			<a href="ranger/curriculum/display.do?rangerId=${row.id}">  --%>
<%-- 				<spring:message code="ranger.curriculum.display" /> --%>
<!-- 			</a> -->
<!-- 		</div> -->
<%-- 	</display:column> --%>
</display:table>

<input type="button" name="back"
	value='<spring:message code="ranger.back"/>'
	onclick="javascript:window.history.back();" />
<br />
