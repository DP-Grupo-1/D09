<%--
 * list.jsp
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
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<display:table name="announcements" id="row" pagesize="5"
	requestURI="${requestURI}" class="displaytag" keepStatus="true" defaultorder="descending">


	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
				<a href="announcement/administrator/delete.do?announcementId=${row.id}">
					<spring:message code="announcement.delete" /> 
				</a>		
		</display:column>
	</security:authorize>
	
	
	<!-- Attributes -->
	<spring:message code="announcement.moment" var="momentHeader" />
	<display:column property="moment" format="{0,date,yyyy/MM/dd HH:mm}" title="${momentHeader}"  />

	

	<spring:message code="announcement.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}" />
	 

	<spring:message code="announcement.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" />



<!-- Rendezvous -->
	<spring:message code="announcement.rendezvous" var="rendezvousHeader" />
	<display:column title="${rendezvousHeader}">
			<a href="rendezvous/display2.do?announcementId=${row.id}">
				<spring:message code="announcement.rendezvous"/>
			</a>
	</display:column>



</display:table>

<%-- <security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="category/administrator/create.do"><spring:message
				code="category.edit.create" /></a>
	</div>
</security:authorize>  --%>
