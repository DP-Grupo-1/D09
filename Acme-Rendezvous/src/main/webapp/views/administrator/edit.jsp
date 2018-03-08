<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<security:authorize access="hasRole('ADMIN')">

	<form:form action="administrator/administrator/edit.do" modelAttribute="administrator">
	<form:hidden path="id"/>
	<form:hidden path="version"/>
	<form:hidden path="userAccount"/>
	
	
	<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
	
	
	<acme:textbox code="admin.name" path="name"/>
	<acme:textbox code="admin.surname" path="surname"/>
	<acme:textbox code="admin.postalAddress" path="postalAddress"/>
	<acme:textbox code="admin.phoneNumber" path="phoneNumber"/>
	<acme:textbox code="admin.email" path="email"/>
	
	<acme:submit name="save" code="admin.save"/>
	<acme:cancel url="welcome/index.do" code="admin.cancel"/>
	</form:form>
	
</security:authorize>