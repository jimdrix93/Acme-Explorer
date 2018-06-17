
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table name="socialIdentities" id="row" pagesize="5" requestURI="${requestUri}" class="displaytag">
	
	<display:column>
		<a href="socialIdentity/edit.do?socialIdentityId=${row.id}"> 
			<spring:message code="socialIdentity.edit" />
		</a>
	</display:column>

	<spring:message code="socialIdentity.nick" var="socialIdentityNick" />
	<display:column property="nick" sortable="true" title="${socialIdentityNick}" />
	
	<spring:message code="socialIdentity.socialNetwork" var="socialIdentitySocialNetwork" />
	<display:column property="socialNetwork" sortable="true" title="${socialIdentitySocialNetwork}" />
	
	<spring:message code="socialIdentity.link" var="socialIdentityLink" />
	<display:column>
 		<a href="${row.link }">${row.link }</a>
	</display:column>
	
	<spring:message code="socialIdentity.photo" var="socialIdentityPhoto" />
	<display:column>
 		<IMG src="${row.photo}" width="50" height="50"/>
	</display:column>

</display:table>

<div>
	<a href="socialIdentity/create.do"> 
		<spring:message code="socialIdentity.create" />
	</a>
</div>