
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
	name="sponsorships" requestURI="sponsorship/sponsor/list.do" id="row">


	<!-- Attributes -->

	<spring:message code="sponsorship.banner" var="bannerHeader" />
	<display:column property="banner" title="${bannerHeader}"
		sortable="false" />

	<spring:message code="sponsorship.infoPage" var="infoPageHeader" />
	<display:column property="infoPage" title="${infoPageHeader}"
		sortable="true" />

	<!-- si ha metido credit card, no mostrar el enlace -->
	<spring:message code="sponsorship.validCreditCard"
		var="validCreditCardHeader" />
	<display:column title="${validCreditCardHeader}">
		<jstl:if test="${empty row.creditCard}">
			<a href="creditcard/sponsor/create.do?sponsorshipId=${row.id}">
				<spring:message	code="sponsorship.addCreditCard" />
			</a>
		</jstl:if>
		<jstl:if test="${not empty row.creditCard}">
			${row.creditCard.brandName}
		</jstl:if>
	</display:column>	

	<spring:message code="sponsorship.display" />
	<display:column>
		<a href="sponsorship/sponsor/display.do?sponsorshipId=${row.id}"> 
		<spring:message	code="sponsorship.display" />
		</a>
	</display:column>
	<display:column>
		<a href="sponsorship/sponsor/edit.do?sponsorshipId=${row.id}"> 
		<spring:message code="sponsorship.edit" />
		</a>
	</display:column>
	

	

</display:table>
		

<!-- Action links -->

<security:authorize access="hasRole('SPONSOR')">
	<div>
		<a href="sponsorship/sponsor/create.do"> 
			<spring:message code="sponsorship.create" />
		</a>
	</div>
</security:authorize>

