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


<display:table name="categories" id="row" pagesize="5"
	requestURI="${requestURI}" class="displaytag" keepStatus="true">


	


	<!-- Attributes -->

	<spring:message code="category.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="category.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	 


<!-- EditCategory -->

	<display:column>
			<a href="category/administrator/edit.do?categoryId=${row.id}">
				<spring:message code="category.edit"/>
			</a>
	</display:column>
	
	
			
<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<a href="category/administrator/delete.do?categoryId=${row.id }" 
		onclick="return confirm('<spring:message code="category.confirm.delete" />')">
				<spring:message code="category.delete"/>
				</a>
	</display:column>
</security:authorize>

</display:table>

<a href="category/administrator/create.do">
				<spring:message code="category.create"/>
			</a>


