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
<form:form action="manager/manager/edit.do" modelAttribute="manager">

<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="userAccount"/>
<form:hidden path="benefits"/>


<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:textbox code="manager.name" path="name"/>
<acme:textbox code="manager.surname" path="surname"/>

<spring:message code="manager.optional" var="optional" /> 
<acme:textbox code="manager.postalAddress" path="postalAddress" placeholder="${optional}"/>
<acme:textbox code="manager.phoneNumber" path="phoneNumber" placeholder="${optional}"/>
<acme:textbox code="manager.vat" path="VAT"/>
<acme:textbox code="manager.email" path="email"/>

<acme:submit name="save" code="manager.save"/>
<acme:cancel url="welcome/index.do" code="manager.cancel"/>
</form:form>
