<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="${requestURI}" modelAttribute="question">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="answers" />
	<form:hidden path="creator" />
	<form:hidden path="rendezvous" />
	
	<acme:textbox code="question.questionToAnswer" path="questionToAnswer"/>
	<br/>
	
	<input type="submit" name="save"
	value="<spring:message code="question.save" />" />&nbsp; 
	
	<input type="button" name="cancel"
		value="<spring:message code="question.cancel" />"
		onclick="location.href = ('welcome/index.do');" />
</form:form>