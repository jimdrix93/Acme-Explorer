
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


<form:form requestUri="configuration/administrator/edit.do" modelAttribute="configuration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:label path="banner">
		<spring:message code="configuration.banner" />:
	</form:label>
	<form:input path="banner" size="100px"/>
	<form:errors cssClass="error" path="banner" />
	<br />

	<form:label path="countryCode">
		<spring:message code="configuration.countrycode" />:
	</form:label>
	<form:input path="countryCode" />
	<form:errors cssClass="error" path="countryCode" />
	<br />

	<form:label path="finderCached">
		<spring:message code="configuration.findercached" />
	</form:label>
	<form:input path="finderCached" />
	<form:errors cssClass="error" path="finderCached" />
	<br />

	<form:label path="finderReturn">
		<spring:message code="configuration.finderreturn" />
	</form:label>
	<form:input path="finderReturn" />
	<form:errors cssClass="error" path="finderReturn" />
	<br />

	<form:label path="vatTax">
		<spring:message code="configuration.vattax" />
	</form:label>
	<form:input path="vatTax" />
	<form:errors cssClass="error" path="vatTax" />
	<br />

	<form:label path="welcomeMessageEs">
		<spring:message code="configuration.welcomemessages" /> Es
	</form:label>
	<form:input path="welcomeMessageEs" size="100px"/>
	<form:errors cssClass="error" path="welcomeMessageEs" />
	<br />
	
	<form:label path="welcomeMessageEn">
		<spring:message code="configuration.welcomemessages" /> En
	</form:label>
	<form:input path="welcomeMessageEn" size="100px"/>
	<form:errors cssClass="error" path="welcomeMessageEn" />
	<br />	


	<form:label path="spamWord">
		<spring:message code="configuration.spamwords" />
	</form:label>
	<form:textarea path="spamWord" />
	<form:errors cssClass="error" path="spamWord" />
	<br />


	<input type="submit" name="save"
		value="<spring:message code="configuration.save" />" />&nbsp; 
		
	<input type="button" name="cancel"
		value='<spring:message code="configuration.cancel"/>'
		onclick="javascript: relativeRedir('7');"/>
	<br />

</form:form>
