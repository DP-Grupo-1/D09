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
		
		<form:form action="${requestURI}" modelAttribute="requestBenefit">
		
		<form:hidden path="benefit" />
		
		
		<form:label path="rendezvous">
		<spring:message code="request.rendezvous"/>
		</form:label>
		<form:select path="rendezvous">
		<form:options items="${rendezvouses}" itemValue="id" itemLabel="name"/>
		</form:select>
		<form:errors cssClass="error" path="rendezvous"/>
	
   	 	<acme:textbox code="requestBenefit.holder.name" path="holderName"/>
    
		<div>
			<form:label path="brandName">
				<spring:message code="requestBenefit.brand.name" />
			</form:label>	
			<form:select path="brandName">		
				<form:option value="VISA" label="Visa" />
				<form:option value="MASTERCARD" label="MasterCard" />
				<form:option value="DISCOVER" label="Discover" />
				<form:option value="DINNERS" label="Dinners" />
				<form:option value="AMEX" label="Amex" />
			</form:select>
			<form:errors path="brandName" cssClass="error" />
		</div>
		
		
		<acme:textbox code="requestBenefit.number" path="number"/>
	
		<acme:textbox code="requestBenefit.expiration.month" path="expirationMonth"/>
	
		<acme:textbox code="requestBenefit.expiration.year" path="expirationYear"/>
	
		<acme:textbox code="requestBenefit.CVV" path="cvv"/>
		
		<acme:textbox code="requestBenefit.comment" path="comment"/>
		
		<button type="submit" name="save" class="btn btn-primary" id="save">
			<spring:message code="requestBenefit.save" />
		</button>
		
		</form:form>		

	<br/>
	
	<input type="button" name="back"
		value="<spring:message code="requestBenefit.back" />"
			onclick="location.href = ('welcome/index.do');" />