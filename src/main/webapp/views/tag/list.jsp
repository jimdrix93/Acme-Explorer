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


<display:table pagesize="5" class="displayTag"
	name="tags" requestURI="${requestUri}" id="row">

	<spring:message code="tag.name" var="tagName" />
	<display:column property="name" title="${tagName}" sortable="true" />

	<spring:message code="tag.edit" var="tagEdit" />
	<display:column>
		<div>
			<a href="tag/administrator/edit.do?tagId=${row.id}"> <spring:message
 				code="tag.edit" />
			</a>
		</div>
	</display:column>
	
	<spring:message code="tag.hasValue" var="tagHasValue" />
	<display:column>
		<div>
			<a href="hasValue/list.do?tagId=${row.id}"> <spring:message
 				code="tag.hasValue" />
			</a>
		</div>
	</display:column>
	
	</display:table>

	<div>
		<a href="hasValue/administrator/create.do"> <spring:message
 				code="tag.hasValue.create" /> 
		</a>
	</div>

	<div>
		<a href="tag/administrator/create.do"> <spring:message
 				code="tag.create" /> 
		</a>
	</div>
	
	<br />
	<input type="button" name="back"
	value='<spring:message code="tag.back"/>'
	onclick="javascript: relativeRedir('/');" />