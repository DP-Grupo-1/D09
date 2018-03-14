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

<security:authorize access="hasRole('ADMINISTRATOR')">
<form:form action="category/administrator/edit.do" modelAttribute="category">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="childrens" />
	<form:hidden path="benefits" />
	
	

	
	
	
			<form:label path="name">
				<spring:message code="category.name" />:
			</form:label>
			<form:input path="name" />
			<form:errors cssClass="error" path="name" />
			
			<form:label path="description">
				<spring:message code="category.description" />:
			</form:label>
			<form:input path="description" />
			<form:errors cssClass="error" path="description" />
			
			
			
			<form:label path="parent">
				<spring:message code="category.parents" />:
			</form:label>
			<form:select id="category" path="parent" multiple="true">
				<form:option value="0" label="----" />
				<form:options items="${categories}" itemValue="id" itemLabel="name" />
			</form:select>
			<form:errors cssClass="error" path="parent" />
	
	

	
	<input type="submit" name="save"
		value="<spring:message code="category.save" />" />&nbsp; 
    <jstl:if test="${comment.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="category.delete" />"
			onclick="return confirm('<spring:message code="category.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="category.cancel" />"
		onclick="javascript: relativeRedir('category/administrator/list.do');" />
	<br />

</form:form>
</security:authorize>