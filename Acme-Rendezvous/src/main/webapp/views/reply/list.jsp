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


<display:table name="replies" id="row" pagesize="5"
	requestURI="${requestURI}" class="displaytag" keepStatus="true">




	<!-- Attributes -->

	<spring:message code="reply.date" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" />

	<spring:message code="reply.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	 

	


<!-- Display reply -->
	<spring:message code="reply.display" var="nameHeader" />
	<display:column>
			<a href="reply/display.do?replyId=${row.id}">
				<spring:message code="reply.display"/>
			</a>
	</display:column>

	
	


</display:table>

<%-- <security:authorize access="hasRole('ADMIN')">
	<div>
		<a href="category/administrator/create.do"><spring:message
				code="category.edit.create" /></a>
	</div>
</security:authorize>  --%>
