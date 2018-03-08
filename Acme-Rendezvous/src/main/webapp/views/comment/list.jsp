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


<display:table name="comments" id="row" pagesize="5"
	requestURI="${requestURI}" class="displaytag" keepStatus="true">


	


	<!-- Attributes -->

	<spring:message code="comment.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}" sortable="true" />

	<spring:message code="comment.text" var="textHeader" />
	<display:column property="text" title="${textHeader}" sortable="true" />
	 

	<spring:message code="comment.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}" sortable="true" />



<!-- Display comment -->
<display:column >
			<a href="comment/display.do?commentId=${row.id}"><spring:message code="comment.display" /></a>
        </display:column>


<!-- Replies -->
	<spring:message code="comment.reply" var="nameHeader" />
	<display:column title="${nameHeader}">
			<a href="reply/list.do?commentId=${row.id}">
				<spring:message code="comment.replies"/>
			</a>
	</display:column>
	
	<display:column>
	<div>
		<a href="reply/user/create.do?commentId=${row.id}"><spring:message
				code="reply.create" /></a>
	</div>
</display:column>
	 

	
	<a href="comment/display.do?commentId=${row.id}">
				${row.text}
			</a>
<security:authorize access="hasRole('ADMIN')">
	<display:column>
		<a href="comment/administrator/delete.do?commentId=${row.id }" 
		onclick="return confirm('<spring:message code="comment.confirm.delete" />')">
				<spring:message code="comment.delete"/>
				</a>
	</display:column>
</security:authorize>

</display:table>

