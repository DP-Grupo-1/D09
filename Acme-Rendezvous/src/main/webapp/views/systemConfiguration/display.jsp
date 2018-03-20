<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<P><b><spring:message code="systemConfiguration.name"/>:</b> ${systemConfiguration.name}</P>

<P><b><spring:message code="systemConfiguration.bannerUrl"/>:</b> ${systemConfiguration.bannerUrl}</P>

<P><b><spring:message code="systemConfiguration.welcomeMessageEnglish"/>:</b> ${systemConfiguration.welcomeMessageEnglish}</P>

<P><b><spring:message code="systemConfiguration.welcomeMessageSpanish"/>:</b> ${systemConfiguration.welcomeMessageSpanish}</P>

<a href="systemConfiguration/administrator/edit.do">
	<spring:message code="systemConfiguration.edit" />
</a>
<br/>
<a href="welcome/index.do">
	<spring:message code="systemConfiguration.index" />
</a>