<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="systemConfiguration/administrator/edit.do" modelAttribute="systemConfiguration">

	<form:hidden path="id"/>
	<form:hidden path="version"/>
	
	<form:label path="name">
		<spring:message code="systemConfiguration.name"/>
	</form:label>
	<form:input path="name"/>
	<form:errors cssClass="error" path="name"/>
	<br/>
	
	<form:label path="bannerUrl">
		<spring:message code="systemConfiguration.bannerUrl"/>
	</form:label>
	<form:input path="bannerUrl"/>
	<form:errors cssClass="error" path="bannerUrl"/>
	<br/>
	
	<form:label path="welcomeMessageEnglish">
		<spring:message code="systemConfiguration.welcomeMessageEnglish"/>
	</form:label>
	<form:input path="welcomeMessageEnglish"/>
	<form:errors cssClass="error" path="welcomeMessageEnglish"/>
	<br/>
	
	<form:label path="welcomeMessageSpanish">
		<spring:message code="systemConfiguration.welcomeMessageSpanish"/>
	</form:label>
	<form:input path="welcomeMessageSpanish"/>
	<form:errors cssClass="error" path="welcomeMessageSpanish"/>
	<br/>
	
	<input type="submit" name="save" value="<spring:message code="systemConfiguration.save"/>"/>
				
	<a href="welcome/index.do">
			 		<spring:message code="systemConfiguration.cancel" />
				</a>
</form:form>