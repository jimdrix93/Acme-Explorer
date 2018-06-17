<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

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


<display:table pagesize="5" class="displayHasValue"
	name="hasValues" requestURI="${requestUri}" id="row">

	<spring:message code="hasValue.value" var="hasValueValue" />
	<display:column property="value" title="${hasValueValue}" sortable="true" />

<%--	<spring:message code="hasValue.edit" var="hasValueEdit" />
	<display:column>
		<div>
			<a href="hasValue/administrator/edit.do?hasValueId=${row.id}"> <spring:message
 				code="hasValue.edit" />
			</a>
		</div>
	</display:column> --%>
	</display:table> 

	<br />
	<input type="button" name="back"
	value='<spring:message code="hasValue.back"/>'
	onclick="javascript: relativeRedir('tag/list.do');" />