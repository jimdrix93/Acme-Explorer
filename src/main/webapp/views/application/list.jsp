
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>




<!-- Listing grid -->

<h3><spring:message code="application.accepted" /></h3>
<display:table class="displaytag" name="acceptedApplications" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="application.trip.ticker" var="tickerHeader" />
	<display:column property="trip.ticker" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.title" var="titleHeader" />
	<display:column property="trip.title" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.price" var="priceHeader" />
	<display:column property="trip.price" title="${priceHeader}"  format="${displaypriceformat}" sortable="false" class="${row.color}"/>
	
	<spring:message code="application.trip.startTrip" var="startTripHeader" />
	<display:column property="trip.startTrip" title="${startTripHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>


	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader" />
		<display:column property="applicant.name" title="${explorerHeader}" sortable="false" />		
	</security:authorize>

	
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
		
	</security:authorize>

</display:table>

<hr />


<h3><spring:message code="application.cancelled" /></h3>

<display:table class="displaytag" name="cancelledApplications" id="row">

	
	<!-- Attributes -->
	
	<spring:message code="application.trip.ticker" var="tickerHeader" />
	<display:column property="trip.ticker" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.title" var="titleHeader" />
	<display:column property="trip.title" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.price" var="priceHeader" />
	<display:column property="trip.price" title="${priceHeader}"  format="${displaypriceformat}" sortable="false" class="${row.color}"/>
	
	<spring:message code="application.trip.startTrip" var="startTripHeader" />
	<display:column property="trip.startTrip" title="${startTripHeader}" format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader" />
		<display:column property="applicant.name" title="${explorerHeader}" sortable="false" />		
	</security:authorize>

	
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>
	

</display:table>

<hr />

<h3><spring:message code="application.pending" /></h3>

<display:table class="displaytag" name="pendingApplications" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="application.trip.ticker" var="tickerHeader" />
	<display:column property="trip.ticker" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.title" var="titleHeader" />
	<display:column property="trip.title" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.price" var="priceHeader" />
	<display:column property="trip.price" title="${priceHeader}"  format="${displaypriceformat}" sortable="false" class="${row.color}"/>
	
	<spring:message code="application.trip.startTrip" var="startTripHeader" />
	<display:column property="trip.startTrip" title="${startTripHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader" />
		<display:column property="applicant.name" title="${explorerHeader}" sortable="false" />		
	</security:authorize>

	
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>
	

</display:table>

<hr />

<h3><spring:message code="application.due" /></h3>

<display:table class="displaytag" name="dueApplications" id="row">
	
	
	<!-- Attributes -->
	
	<spring:message code="application.trip.ticker" var="tickerHeader" />
	<display:column property="trip.ticker" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.title" var="titleHeader" />
	<display:column property="trip.title" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.price" var="priceHeader" />
	<display:column property="trip.price" title="${priceHeader}"  format="${displaypriceformat}" sortable="false" class="${row.color}"/>
	
	<spring:message code="application.trip.startTrip" var="startTripHeader" />
	<display:column property="trip.startTrip" title="${startTripHeader}" format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader" />
		<display:column property="applicant.name" title="${explorerHeader}" sortable="false" />		
	</security:authorize>

	
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
		
		
		<display:column>
			<a href="creditcard/explorer/create.do?applicationId=${row.id}">
				<spring:message	code="application.addCreditCard" />
			</a>
		</display:column>	
	</security:authorize>
	

</display:table>

<hr />

<h3><spring:message code="application.rejected" /></h3>

<display:table class="displaytag" name="rejectedApplications" id="row">
	
	<!-- Attributes -->
	
	<spring:message code="application.trip.ticker" var="tickerHeader" />
	<display:column property="trip.ticker" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.title" var="titleHeader" />
	<display:column property="trip.title" title="${tickerHeader}" sortable="false" class="${row.color}"/>

	<spring:message code="application.trip.price" var="priceHeader" />
	<display:column property="trip.price" title="${priceHeader}"  format="${displaypriceformat}" sortable="false" class="${row.color}"/>
	
	<spring:message code="application.trip.startTrip" var="startTripHeader" />
	<display:column property="trip.startTrip" title="${startTripHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	<spring:message code="application.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"  format="${displaydatetimeformat}" sortable="false" class="${row.color}"/>

	
	<security:authorize access="hasRole('MANAGER')">
		<spring:message code="application.explorer" var="explorerHeader" />
		<display:column property="applicant.name" title="${explorerHeader}" sortable="false" />		
	</security:authorize>

	
	<!-- Action links -->

	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<a href="application/manager/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

	<security:authorize access="hasRole('EXPLORER')">
		<display:column>
			<a href="application/explorer/edit.do?applicationId=${row.id}">
				<spring:message	code="application.edit" />
			</a>
		</display:column>		
	</security:authorize>

</display:table>

