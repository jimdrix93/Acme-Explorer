<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="${enterpriseLogo}" alt="Acme Explorer Co., Inc." height="200" />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>				
					<li><a href="legaltext/administrator/list.do"><spring:message code="master.page.administrator.legalText" /></a></li>
					<li><a href="dashboard/administrator/display.do"><spring:message code="master.page.administrator.dashboard" /></a>
					<li><a href="broadcast/administrator/create.do"><spring:message code="master.page.administrator.broadcast" /></a>
					<li><a href="configuration/administrator/edit.do"><spring:message code="master.page.administrator.configuration" /></a>					
				</ul>
			</li>
			<li><a class="fNiv"><spring:message	code="master.page.create.accounts" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="manager/administrator/create.do"><spring:message code="master.page.administrator.create.manager.account" /></a></li>
					<li><a href="ranger/administrator/create.do"><spring:message code="master.page.administrator.create.ranger.account" /></a></li>					
				</ul>
			</li>
			<li><a class="fNiv" href="tag/list.do"><spring:message code="master.page.tags" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv"><spring:message	code="master.page.manager" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="trip/manager/list.do"><spring:message code="master.page.manager.trip.list" /></a></li>
					<li><a href="trip/manager/create.do"><spring:message code="master.page.manager.trip.create" /></a></li>					
					<li><a href="stage/manager/create.do"><spring:message code="master.page.manager.stage.create" /></a></li>
					<li><a href="application/manager/list.do"><spring:message code="master.page.manager.applications.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUDITOR')">
			<li><a class="fNiv"><spring:message	code="master.page.auditor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="note/auditor/list.do"><spring:message code="master.page.auditor.my.note.list" /></a></li>
					<li><a href="audit/auditor/list.do"><spring:message code="master.page.auditor.my.audit.list" /></a></li>
				</ul>
			</li>
		</security:authorize>

		<li><a class="fNiv" href="trip/list.do"><spring:message code="master.page.trips" /></a></li>
		<li><a class="fNiv" href="category/list.do"><spring:message code="master.page.categories" /></a></li>
		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="trip/search.do"><spring:message code="master.page.searchTrip" /></a></li>
			<li><a class="fNiv" href="ranger/create.do"><spring:message code="master.page.ranger.register" /></a></li>
			<li><a class="fNiv" href="explorer/create.do"><spring:message code="master.page.explorer.register" /></a></li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.manager.sponsorship.list" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('EXPLORER')">
			<li><a class="fNiv"><spring:message	code="master.page.explorer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="application/explorer/list.do"><spring:message code="master.page.explorer.applications.list" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="emergencyContact/explorer/list.do"><spring:message code="master.page.emergency.contact" /></a></li>
		</security:authorize>
					
		<security:authorize access="isAuthenticated()">
			<li> 
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/edit.do"><spring:message code="master.page.editUser" /></a></li>
					<li><a href="userAccount/edit.do"><spring:message code="master.page.editUserAccount" /></a></li>
					<li><a href="socialIdentity/list.do"><spring:message code="master.page.socialIdentities" /></a></li>
					<li><a href="actor/list.do"><spring:message code="master.page.list.actors" /></a></li>	
					<li><a href="folder/list.do"><spring:message code="master.page.messages" /></a></li>	
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

