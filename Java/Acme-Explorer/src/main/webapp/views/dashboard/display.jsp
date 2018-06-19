
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<h3><spring:message code="dashboard.applications.per.trip" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statisticsApplications[2]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statisticsApplications[0]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statisticsApplications[1]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statisticsApplications[3]}</li>
	</ul>

<h3><spring:message code="dashboard.trips.per.manager" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statisticsManager[2]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statisticsManager[0]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statisticsManager[1]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statisticsManager[3]}</li>
	</ul>

<h3><spring:message code="dashboard.price.trips" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: 
		<fmt:formatNumber value="${statisticsPrice[2]}" pattern="${formpriceformat}" var="formattedPrice"/>
		${formattedPrice}
		<spring:message code="currency_symbol" /> 
	</li>
	<li><spring:message code="dashboard.minimum" />: 
		<fmt:formatNumber value="${statisticsPrice[0]}" pattern="${formpriceformat}" var="formattedPrice"/>
		${formattedPrice}
		<spring:message code="currency_symbol" /> 
	</li>
	<li><spring:message code="dashboard.maximum" />: 
		<fmt:formatNumber value="${statisticsPrice[1]}" pattern="${formpriceformat}" var="formattedPrice"/>
		${formattedPrice}
		<spring:message code="currency_symbol" /> 
	</li>
	<li><spring:message code="dashboard.deviation" />: 
		<fmt:formatNumber value="${statisticsPrice[3]}" pattern="${formpriceformat}" var="formattedPrice"/>
		${formattedPrice}
		<spring:message code="currency_symbol" /> 
	</li>
	</ul>


<h3><spring:message code="dashboard.trips.by.ranger" /></h3>

	<ul>
	<li><spring:message code="dashboard.average" />: ${statisticsRanger[2]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statisticsRanger[0]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statisticsRanger[1]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statisticsRanger[3]}</li>
	</ul>


<h3><spring:message code="dashboard.ratio.applications" /></h3>

	<ul>
	<li><spring:message code="dashboard.pending" />: ${ratioPending} %</li>
	<li><spring:message code="dashboard.due" />: ${ratioDue} %</li>
	<li><spring:message code="dashboard.accepted" />: ${ratioAccepted} %</li>
	<li><spring:message code="dashboard.cancelled" />: ${ratioCancelled} %</li>
	</ul>

<h3><spring:message code="dashboard.ratio.trips.cancelled" /></h3>

	<ul>
	<li><spring:message code="dashboard.ratio" />: ${ratioTripsCancelled} %</li>
	</ul>



 
<h3><spring:message code="dashboard.listing.trips.10" /></h3>


	<table class="displaytag" >
	<thead>
		<tr>
			<td>
				<b><spring:message code="dashboard.trip.ticker"></spring:message></b>
			</td>
			<td>
				<b><spring:message code="dashboard.trip.number.applications"></spring:message></b>
			</td>
		</tr>
	</thead>
	<tbody>
	<jstl:forEach items="${ratio10}" var="fila">
		<tr  class="odd">
			<td>
				${fila[0]}
			</td>
			<td>
				${fila[1]}
			</td>
		</tr>
	</jstl:forEach>
	</tbody>
	</table>
	
	
	
<h3><spring:message code="dashboard.times.legaltext" /></h3>


	<table class="displaytag" >
	<thead>
		<tr>
			<td>
				<b><spring:message code="dashboard.legaltext.title"></spring:message></b>
			</td>
			<td>
				<b><spring:message code="dashboard.legaltext.number.of.references"></spring:message></b>
			</td>
		</tr>
	</thead>
	<tbody>
	<jstl:forEach items="${statisticsLegalText}" var="fila">
		<tr class="odd">
			<td>
				${fila[0]}
			</td>
			<td>
				${fila[2]}
			</td>
		</tr>
	</jstl:forEach>
	</tbody>
	</table>
	
	
	
<h3><spring:message code="dashboard.notes.per.trip" /></h3>
	<ul>
	<li><spring:message code="dashboard.average" />: ${statisticsNotes[2]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statisticsNotes[0]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statisticsNotes[1]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statisticsNotes[3]}</li>
	</ul>
	
	
	
<h3><spring:message code="dashboard.audits.per.trip" /></h3>
	<ul>
	<li><spring:message code="dashboard.average" />: ${statisticsAudits[2]}</li>
	<li><spring:message code="dashboard.minimum" />: ${statisticsAudits[0]}</li>
	<li><spring:message code="dashboard.maximum" />: ${statisticsAudits[1]}</li>
	<li><spring:message code="dashboard.deviation" />: ${statisticsAudits[3]}</li>
	</ul>
	
	
<h3><spring:message code="dashboard.ratio.trips.withaudit" /></h3>
	<ul>
	<li><spring:message code="dashboard.ratio" />: ${ratioTripsWithAudit} %</li>
	</ul>


<h3><spring:message code="dashboard.ratio.rangers.curricula" /></h3>
	<ul>
	<li><spring:message code="dashboard.ratio" />: ${ratioRangersWithCurricula} %</li>
	</ul>

<h3><spring:message code="dashboard.ratio.rangers.curricula.endorsed" /></h3>
	<ul>
	<li><spring:message code="dashboard.ratio" />: ${ratioRangersWithCurriculaEndorsed} %</li>
	</ul>	
	

<h3><spring:message code="dashboard.ratio.suspicious" /></h3>
	<ul>
	<li><spring:message code="dashboard.ratio.suspicious.managers" />: ${ratioSuspiciousManagers} %</li>
	<li><spring:message code="dashboard.ratio.suspicious.rangers" />: ${ratioSuspiciousRangers} %</li>
	</ul>	

