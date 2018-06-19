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
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>

<form:form action="curriculum/ranger/edit.do" modelAttribute="curriculum" >
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="personalRecord" />
	<form:hidden path="educationRecords" />
	<form:hidden path="miscellaneousRecords" />
	<form:hidden path="professionalRecords" />
	<form:hidden path="endorserRecords" />
	
	<jstl:if test="${curriculum.id != 0}">
	
		<h4>
			<spring:message code="personalRecord" />
		</h4>
		
		<jstl:if test="${curriculum.personalRecord == null}">
		
			<div>
				<a href="personalRecord/ranger/create.do?curriculumId=${row.id}"> 
					<spring:message code="personalRecord.create" />
				</a>
			</div>
		</jstl:if>
		<jstl:if test="${curriculum.personalRecord != null}">
			<div>
				<a href="personalRecord/ranger/edit.do"> 
					<spring:message code="personalRecord.edit" />
				</a>
			</div>
		</jstl:if>
		
		<h4>
			<spring:message code="educationRecord" />
		</h4>
		
		<display:table pagesize="5" class="displaytag" name="educationRecords" id="row">
	
			<spring:message code="educationRecord.diplomaTitle" var="educationRecordDiplomaTitle" />
			<display:column property="diplomaTitle" title="${educationRecordDiplomaTitle}"  />

			<spring:message code="educationRecord.startDate" var="educationRecordStartDate" />
			<display:column property="startDate" title="${educationRecordStartDate}" />
			
			<spring:message code="educationRecord.endDate" var="educationRecordEndDate" />
			<display:column property="endDate" title="${educationRecordEndDate}" />
			
			<spring:message code="educationRecord.institution" var="educationRecordInstitution" />
			<display:column property="institution" title="${educationRecordInstitution}"  />
			
			<spring:message code="educationRecord.attachment" var="educationRecordAttachment" />
			<display:column property="attachment" title="${educationRecordAttachment}"  />
			
			<spring:message code="educationRecord.comments" var="educationRecordComments" />
			<display:column property="comments" title="${educationRecordComments}"  />
			
			<display:column>
				<div>
					<a href="educationRecord/ranger/edit.do?educationRecordId=${row.id}"> 
						<spring:message code="educationRecord.edit" />
					</a>
				</div>
			</display:column>
			
		</display:table>
		
		<div>
			<a href="educationRecord/ranger/create.do?curriculumId=${row.id}"> 
				<spring:message code="educationRecord.create" />
			</a>
		</div>
		
		<h4>
			<spring:message code="miscellaneousRecords" />
		</h4>
		
		<display:table pagesize="5" class="displaytag" name="miscellaneousRecords"  id="row">
	
			<spring:message code="miscellaneousRecord.title" var="miscellaneousRecordTitle" />
			<display:column property="title" title="${educationRecordTitle}" />
			
			<spring:message code="miscellaneousRecord.attachment" var="miscellaneousAttachment" />
			<display:column property="attachment" title="${miscellaneousAttachment}" />
			
			<spring:message code="miscellaneousRecord.comments" var="miscellaneousComments" />
			<display:column property="comments" title="${miscellaneousComments}" />
			
			<display:column>
				<div>
					<a href="miscellaneousRecord/ranger/edit.do?miscellaneousRecordId=${row.id}"> 
						<spring:message code="miscellaneousRecord.edit" />
					</a>
				</div>
			</display:column>
			
		</display:table>
		
		<div>
			<a href="miscellaneousRecord/ranger/create.do?curriculumId=${row.id}"> 
				<spring:message code="miscellaneousRecord.create" />
			</a>
		</div>
		
	</jstl:if>
	
	<input type="submit" name="save"
		value="<spring:message code="curriculum.save" />" />&nbsp; 
		
	<jstl:if test="${curriculum.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="curriculum.delete" />"
			onclick="return confirm('<spring:message code="curriculum.confirm.delete" />')" />&nbsp;
	</jstl:if>
	
	<input type="button" name="cancel"
		value='<spring:message code="curriculum.cancel"/>'
		onclick="javascript: relativeRedir('/');" />
	<br />
</form:form>
