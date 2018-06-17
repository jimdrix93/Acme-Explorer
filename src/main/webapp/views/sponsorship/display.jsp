
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div>
	<img src="${sponsorship.banner}"/>
	<br/>
	<a href="${sponsorship.infoPage}">
		<spring:message code="sponsorship.infoPage"/>
	</a>
	<br/>
	
	<spring:message code="sponsorship.validCreditCard" var="validCreditCardHeader" />
	<br/>
	
	<display:table class="displaytag" name="sponsorship" id="row">

		<spring:message code="sponsorship.validCreditCard.holderName" var="validCreditCardHeader" />
		<display:column property="validCreditCard.holderName" title="${validCreditCardHeader}"/>
		
		<spring:message code="sponsorship.validCreditCard.brandName" var="validCreditCardHeader" />
		<display:column property="validCreditCard.brandName" title="${validCreditCardHeader}" />
		
		<spring:message code="sponsorship.validCreditCard.number" var="validCreditCardHeader" />
		<display:column property="validCreditCard.number" title="${validCreditCardHeader}"/>
	</display:table>
	
	<display:table class="displaytag" name="sponsorship.trips" id="row">
		<spring:message code="trip.ticker" var="tripTicker" />
		<display:column property="ticker" title="${tripTicker}" sortable="true" />
	
		<spring:message code="trip.title" var="tripTitle" />
		<display:column property="title" title="${tripTitle}" sortable="true" />
	
		<spring:message code="trip.price" var="priceHeader" />
		<display:column property="price" title="${priceHeader}"  format="${displaypriceformat}" sortable="true" />
		
	
		<spring:message code="trip.startDate" var="tripStartDate" />
		<display:column property="startTrip" title="${tripStartDate}"
			format="{0,date,${tripDateFormat}}" sortable="true" />

		<spring:message code="trip.endDate" var="tripEndDate" />
		<display:column property="endTrip" title="${tripEndDate}"
			format="{0,date,${tripDateFormat}}" sortable="true" />
	</display:table>
</div>