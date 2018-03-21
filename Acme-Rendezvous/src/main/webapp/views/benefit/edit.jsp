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

<%@taglib prefix="acme"	tagdir="/WEB-INF/tags"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

		
		<form:form action="${requestURI}" modelAttribute="benefit">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="flag" />
		<form:hidden path="rendezvouses" />
		
		
   	 	<acme:textbox code="benefit.name" path="name"/>
   	 	<acme:textbox code="benefit.description" path="description"/>
   	 	<acme:textbox code="benefit.picture" path="picture"/>
		
		<button type="submit" name="save" class="btn btn-primary" id="save">
			<spring:message code="benefit.save" />
		</button>
		<jstl:if test="${benefit.id != 0 and benefit.rendezvouses.isEmpty()}">
		<input type="submit" name="delete"
			value="<spring:message code="benefit.delete" />"
			onclick="return confirm('<spring:message code="benefit.confirm.delete" />')" />&nbsp;
	</jstl:if>
		</form:form>		

	<br/>
	
	<input type="button" name="back"
		value="<spring:message code="benefit.back" />"
			onclick="location.href = ('welcome/index.do');" />