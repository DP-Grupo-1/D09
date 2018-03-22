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

<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('USER')">
<form:form action="${requestURI}" modelAttribute="rendezvous">
<jstl:if test="${finalMode == false}">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="flag" />
	<form:hidden path="creator" />
	<form:hidden path="attendants" />
	<form:hidden path="rendezvouses" />
	<form:hidden path="comments" />
	<form:hidden path="announcements" />
	<form:hidden path="categories"/>
	
	<script>
	function tester(moment) {
		var d = new Date();
		
		var minutos = d.getMinutes();
		var hora = d.getHours();
		
		var dia = d.getDate();
		var mes = d.getMonth() +1;
		var ano = d.getFullYear();
		
			
		var campos = moment.split(' ');
		
		// 18/05/23
		//dates[0] = 18, dates[1] = 05, dates[2] = 23
		var dates = campos[0].split("/");
		
		// 13:30
		//horas[0] = 13, horas[1] = 30
		var horas = campos[1].split(":");
		
		if(dates[0] < ano){
			alert("<spring:message code="rendezvous.error.moment" />");
			return false;
		} else if((dates[0] == ano) && dates[1] < mes){
			alert("<spring:message code="rendezvous.error.moment" />");
			return false;
		} else if((dates[0] == ano) && (dates[1] == mes) && dates[2] < dia){
			alert("<spring:message code="rendezvous.error.moment" />");
			return false;
		} else if((dates[0] == ano) && (dates[1] == mes) && (dates[2] == dia) && horas[0] < hora){
			alert("<spring:message code="rendezvous.error.moment" />");
			return false;
	} else if((dates[0] == ano) && (dates[1] == mes) && (dates[2] == dia) && (horas[0] == hora) && (horas[1] < minutos)){
			alert("<spring:message code="rendezvous.error.moment" />");
			return false;
		} else {
			
		}
    
 }
</script>
	
    <acme:textbox code="rendezvous.name" path="name"/>
    
    <acme:textbox code="rendezvous.description" path="description"/>
    
    <spring:message code="rendezvous.moment.format" var="momentFormat"/>
    
    <acme:textbox code="rendezvous.moment" path="moment" id="momentId" placeholder="${momentFormat}"/>
    
    <spring:message code="rendezvous.optional" var="optional"/>
    
    <acme:textbox code="rendezvous.picture" path="picture" placeholder="${optional}"/>
    
    <acme:textbox code="rendezvous.locationLatitude" path="locationLatitude" placeholder="${optional}"/>
    
    <acme:textbox code="rendezvous.locationLongitude" path="locationLongitude" placeholder="${optional}"/>
	
	<spring:message code="rendezvous.finalMode" />:
	<input type="checkbox" name="finalMode"
		<jstl:if test="${rendezvous.finalMode==true}">
			checked
		</jstl:if> 
		value="true" />
	<br>
	
	<jstl:if test="${adult==true}">
	
	<spring:message code="rendezvous.adultOnly" />:
	<input type="checkbox" name="adultOnly" 
	<jstl:if test="${rendezvous.adultOnly==true}">
	checked
	</jstl:if> 
		value="true" />
	<br>
	
	</jstl:if> 
	
	<jstl:if test="${rendezvous.finalMode==false || rendezvous.flag!=Flag.DELETED}">
		<input type="submit" name="save"
			value="<spring:message code="rendezvous.save"/>"/>&nbsp;
	<input type="submit" name="delete"
			value="<spring:message code="rendezvous.delete"/>" />&nbsp;
    </jstl:if> 

	<input type="button" name="cancel"
		value="<spring:message code="rendezvous.cancel" />"
		onclick="javascript: relativeRedir('rendezvous/user/list.do');" />
	<br />
</jstl:if>
</form:form>
</security:authorize>
