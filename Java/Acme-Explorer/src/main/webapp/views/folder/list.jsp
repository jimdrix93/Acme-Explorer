<%--
 * list.jsp
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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table class="displaytag" requestURI="folder/list.do" name="folders" id="row">

	<spring:message code="folder.name" var="folderName" />
	<display:column property="name" title="${folderName}" sortable="true" />

	<spring:message code="folder.folderType" var="folderFolderType" />
	<display:column property="folderType" title="${folderFolderType}"
		sortable="true" />

	<display:column>
		<div>
			<a href="folder/message/list.do?folderId=${row.id}"> <spring:message
					code="folder.message.list" />
			</a>
		</div>
	</display:column>
	
	<display:column>
		<div>
			<a href="folder/message/moving.do?folderId=${row.id}"> 
				<spring:message	code="folder.message.move" />
			</a>
		</div>
	</display:column>
	
		<display:column>
			<jstl:if test="${ row.folderType == 'CUSTOM'}">
				<div>
					<a href="folder/createWithParent.do?parentId=${row.id}"> 
						<spring:message code="folder.create.childs" />
					</a>
				</div>
			</jstl:if>
		</display:column>
		
		<display:column>
			<jstl:if test="${ row.folderType == 'CUSTOM'}">
				<div>
					<a href="folder/edit.do?folderId=${row.id}"> 
				<spring:message code="folder.edit" />
					</a>
				</div>
			</jstl:if>
		</display:column>
</display:table>

<div>
	<a href="folder/create.do"> 
		<spring:message code="folder.create" />
	</a>
</div>

<div>
	<a href="actor/list.do"> 
		<spring:message code="folder.message.create" />
	</a>
</div>	

	
