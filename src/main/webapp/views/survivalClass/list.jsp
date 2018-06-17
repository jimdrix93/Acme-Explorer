
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

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="survivalClasses" requestURI="${requestURI}" id="row">

	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="survivalClass/manager/edit.do?survivalClassId=${row.id}"> <spring:message
					code="survivalClass.edit" />
			</a>
		</display:column>
	</security:authorize>

	<!-- Attributes -->

	<spring:message code="survivalClass.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" sortable="true" />

	<spring:message code="survivalClass.description"
		var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />

	<spring:message code="survivalClass.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" format="{0,date,${survivalClassDateFormat}}" />

	<spring:message code="survivalClass.location" var="locationHeader" />
	<display:column property="location.coordinates" title="${locationHeader}"
		sortable="false" />

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
				<a href="survivalClass/explorer/register.do?survivalClassId=${row.id}">
					<spring:message	code="survivalClass.register" />
				</a>
		</display:column>
		<display:column>
				<a href="survivalClass/explorer/unregister.do?survivalClassId=${row.id}">
					<spring:message	code="survivalClass.unregister" />
				</a>
		</display:column>
	</security:authorize>

</display:table>

<!-- Action links -->

<security:authorize access="hasRole('MANAGER')">
	<div>
		<a href="survivalClass/manager/create.do?tripId=${row.trip.id}"> <spring:message
				code="survivalClass.create" />
		</a>
	</div>
</security:authorize>