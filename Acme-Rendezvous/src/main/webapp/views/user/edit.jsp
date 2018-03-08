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
<form:form action="user/user/edit.do" modelAttribute="user">

<form:hidden path="id"/>
<form:hidden path="version"/>
<form:hidden path="userAccount"/>
<form:hidden path="attendances"/>
<form:hidden path="comments"/>
<form:hidden path="replies"/>



<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<acme:textbox code="user.name" path="name"/>
<acme:textbox code="user.surname" path="surname"/>
<acme:textbox code="user.postalAddress" path="postalAddress"/>
<acme:textbox code="user.phoneNumber" path="phoneNumber"/>
<acme:textbox code="user.email" path="email"/>

<acme:submit name="save" code="user.save"/>
<acme:cancel url="welcome/index.do" code="user.cancel"/>
</form:form>
