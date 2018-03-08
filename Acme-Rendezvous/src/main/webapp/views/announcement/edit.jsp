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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('USER')">
<form:form action="announcement/user/edit.do?rendezvousId=${rendezvousId}" modelAttribute="announcement">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="moment" />

	

	
	<form:label path="title">
		<spring:message code="announcement.title" />:
	</form:label>
	<form:input path="title"/>
	<form:errors cssClass="error" path="title" />
	<br />
	
	<form:label path="description">
		<spring:message code="announcement.description" />:
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description" />
	<br />
	
	
	
	<input type="submit" name="save"
		value="<spring:message code="announcement.save" />" />&nbsp; 

	
	<input type="button" name="cancel"
		value="<spring:message code="announcement.cancel" />"
		onclick="javascript: relativeRedir('rendezvous/list.do');" />
	<br />

</form:form>
</security:authorize>